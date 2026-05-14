# Android Architecture Reference

A reusable spec for new Android projects. Drop this file into a repo root (rename to `ARCHITECTURE.md` if you like) and have your AI coding agent or new teammates read it before writing any code.

This document describes **what** the architecture is and **why** each rule exists. It is opinionated by design. It assumes Jetpack Compose, Hilt, Room, Retrofit, Coroutines, and Kotlin 2.x. If your stack diverges, fork and adapt — but don't half-adopt; the rules below stop working if you mix them with a different mental model.

---

## 1. The five non-negotiable rules

If you remember nothing else, remember these. The rest of this document is them, expanded.

1. **Dependencies point inward.** UI depends on domain. Data depends on domain. Domain depends on nothing. This holds whether the code lives in a feature module or in `:core`.
2. **`:core:domain` (and any feature's domain sub-package) is pure Kotlin.** Zero Android imports. Zero framework imports. If it can't compile to a JVM jar with no Android SDK on the classpath, it's broken.
3. **Features never depend on other features.** Feature A never imports from feature B — not its repository, not its models, not its UI. If two features need the same type, that type belongs in `:core`. The day you write `implementation(project(":feature:auth"))` inside `:feature:profile`, stop and lift the shared concept up.
4. **Repositories return domain types, not DTOs and not entities.** The data layer's job is to translate; the rest of the app should never see a Retrofit DTO or a Room entity. This is true whether the repository lives in a feature module or in `:core:data`.
5. **ViewModels expose `StateFlow<UiState>`, not multiple flows.** One state, one stream, immutable. Side effects (snackbars, navigation) use a separate `SharedFlow<Effect>` channel.

Violations of any of these compound over months. They're cheap to follow from day one and expensive to retrofit later.

---

## 2. Module structure

There are two valid ways to slice an Android codebase: **layer-first** (everything of one kind in one place — all repos in `:core:data`, all APIs in `:core:network`) and **feature-first** (each feature owns its full vertical stack — API, repository, models, screens). Both have proponents, and both fail in their pure form: strict layer-first creates a `:core:data` so large that every feature change ripples through everything, and strict feature-first breaks down the moment two features need the same data type.

This document teaches the **hybrid** that production codebases converge on. The core idea is one decision rule:

> **If exactly one feature uses it, the feature owns it. The moment a second feature needs it, lift it to `:core`.**

This applies to repositories, models, APIs, and DAOs — everything in the data and domain layers. Infrastructure (Retrofit setup, Room database class, design tokens) always lives in `:core` regardless.

### 2.1 The layout

```
:app                                  // Application class, top-level NavHost, DI graph composition

:feature:
   :auth/
      data/         AuthApi, AuthRepositoryImpl, AuthDao, LoginDto → Login mappers
      domain/       AuthRepository (interface), Login, LoginUseCase, SignUpUseCase
      ui/           LoginScreen, LoginViewModel, SignUpScreen, SignUpViewModel
   :profile/
      data/         ProfileApi, ProfileRepositoryImpl
      domain/       ProfileRepository, UpdateProfileUseCase
      ui/           ProfileScreen, ProfileViewModel
   :home/
      ...                             // each feature is self-contained

:core:
   :domain                            // SHARED domain only. Models and repo interfaces used by 2+ features.
                                      //   e.g. User, UserRepository, AppError, AuthToken
   :data                              // SHARED implementations only. Impls of :core:domain interfaces.
                                      //   e.g. UserRepositoryImpl (because User is shared)
   :network                           // Retrofit, OkHttp, JSON config, interceptors, error mapping.
                                      //   NOT API interfaces — those live with the feature that owns them
                                      //   (or in :core:data if shared).
   :database                          // Room database class, migrations, DB-level config.
                                      //   NOT entities/DAOs — those live with the feature that owns them
                                      //   (or in :core:data if shared).
   :datastore                         // DataStore Preferences/Proto setup, typed preference wrappers
   :ui                                // Theme, design tokens, shared Composables, common modifiers
   :common                            // tiny utilities: ApiResult, dispatchers, logger, time provider
   :testing                           // shared test fixtures, fakes, JUnit rules

:build-logic                          // Gradle convention plugins (see §11)
```

The key thing to notice: `:core:network` contains *infrastructure* (the Retrofit/OkHttp setup), not *interfaces*. `AuthApi` lives in `:feature:auth/data/` because only auth uses it. The Retrofit instance it gets injected into lives in `:core:network`.

### 2.2 The decision rule, worked through

When you create a new type, ask: *who uses this?*

**Used by exactly one feature → lives in that feature's module.**

- `LoginDto`, `AuthApi`, `AuthRepository` interface, `AuthRepositoryImpl`, `LoginScreen`, `LoginViewModel` → all in `:feature:auth`.
- The feature is self-contained. You can delete it by deleting the module.

**Used by two or more features → lift to `:core`.**

- `User` is read by `:feature:profile`, `:feature:home` (greeting), `:feature:settings`. → `User` moves to `:core:domain`, `UserRepository` interface moves to `:core:domain`, `UserRepositoryImpl` moves to `:core:data`.
- The feature that *owns* the concept conceptually (probably auth, since it creates users) no longer owns it structurally. That's fine — that's the trade.

**Infrastructure → always in `:core`, regardless of who uses it.**

- The Retrofit instance, the Room database class, the design system, dispatcher modules — these are infrastructure. Even if only one feature exists today, they belong in `:core` so the second feature can use them tomorrow without a refactor.

### 2.3 The migration moment

In practice, you can't always predict which types will be shared. The pragmatic flow:

1. **Day one of a feature: build it self-contained.** New auth feature? Everything goes in `:feature:auth`. Don't preemptively lift things to `:core` "just in case."
2. **The first time a second feature needs the type, lift it.** Profile screen needs `User`? Stop. Pull `User`, `UserRepository`, and `UserRepositoryImpl` out of `:feature:auth` and into `:core:domain` + `:core:data`. Now both features depend on `:core`, not on each other.
3. **Never let one feature depend on another.** That's the only hard rule in this whole section. The moment you're tempted to do `implementation(project(":feature:auth"))`, that's the signal to lift, not to add the dependency.

The cost of lifting is small (move 2–3 files, update imports, run the build). The cost of feature-to-feature dependencies compounds forever — they create cycles, they couple release cadence, they make features impossible to delete.

### 2.4 Why feature-owned data is worth the discipline

Three reasons this hybrid beats strict layer-first for most apps:

- **Cognitive locality.** When you're working on auth, the API, repository, models, ViewModels, and screens are all in one place. You don't jump across four `:core` modules to follow one user action.
- **Feature deletion is trivial.** Want to remove a feature? Delete the module. With strict layer-first, you'd hunt for that feature's classes scattered across `:core:domain`, `:core:data`, `:core:network`, `:core:database`.
- **Build graph clarity.** A change to `AuthRepositoryImpl` only rebuilds `:feature:auth`. With strict layer-first, it rebuilds all of `:core:data`, which forces every feature that depends on `:core:data` to relink — which is every feature.

### 2.5 Starting small

You don't need the full layout on day one. **Minimum viable structure for a new project:**

```
:app
:feature:auth                 (or whatever your first feature is)
:core:ui
:core:common
```

Add modules as the seam becomes obvious:

- Add `:core:network` the moment you write your first Retrofit instance (you'll want it shared across features eventually).
- Add `:core:database` the moment you set up Room.
- Add `:core:domain` and `:core:data` the moment a second feature needs a shared type — *not* before.
- Add `:build-logic` once you have ~5 modules and find yourself copy-pasting Gradle config.

Premature splitting is as bad as never splitting. The rule is "split when the seam becomes obvious," not "split everything up front."

---

## 3. The four layers

The layer model is about *roles*, not about *modules*. Layers cut across both feature modules and `:core` modules. The same four layers exist in `:feature:auth` (for auth-owned types) and in `:core` (for shared types).

```
┌─────────────────────────────────────────────────────────────────┐
│ Presentation        (always in feature modules — UI is never    │
│                      shared across features)                    │
│   - Composables, ViewModels, UiState, navigation routes         │
├─────────────────────────────────────────────────────────────────┤
│ Domain              (feature/.../domain/ for feature-only types │
│                      :core:domain for shared types)             │
│   - Pure Kotlin models, use cases, repository interfaces        │
├─────────────────────────────────────────────────────────────────┤
│ Data                (feature/.../data/ for feature-only repos   │
│                      :core:data for shared repos)               │
│   - Repository implementations, mappers                         │
├─────────────────────────────────────────────────────────────────┤
│ Sources             (:core:network, :core:database — always     │
│                      shared infrastructure. API/DAO interfaces  │
│                      live with whoever owns them.)              │
│   - Retrofit instance, Room DB class, DataStore setup           │
└─────────────────────────────────────────────────────────────────┘
```

Two rules hold no matter which module a piece of code lives in:

- **The dependency arrow goes one way: presentation → domain → data → sources.** A ViewModel can call a use case or a repository. A repository can call an API or a DAO. The reverse never happens. A repository never imports a ViewModel; a domain model never imports a Retrofit DTO.
- **Domain code is pure Kotlin.** Whether it's `:core:domain` or `feature/auth/domain/`, the `domain/` directory contains zero Android imports, zero Retrofit, zero Room. It's testable as plain JVM code.

---

## 4. Domain layer

The domain layer is where business concepts and pure logic live. It exists in two places:

- **Inside each feature module** (e.g. `feature/auth/domain/`) for types only that feature uses — `Login`, `AuthRepository`, `LoginUseCase`.
- **In `:core:domain`** for types two or more features use — `User`, `UserRepository`, `AppError`, `AuthToken`.

The rules below apply equally to both locations. Mentally, treat "the domain layer" as a single conceptual layer that happens to be spread across modules.

### 4.1 What lives in the domain layer

- **Models.** Plain Kotlin data classes that represent business concepts. `User`, `Order`, `Session`, `Login`. Never `UserDto`, never `UserEntity`.
- **Repository interfaces.** Just interfaces — implementations live in the corresponding `data/` package or in `:core:data`.
- **Use cases.** One file per "thing the app does." `RefreshOrders`, `CompleteOnboarding`, `LogIn`. Each use case has exactly one public method (often `operator fun invoke`).
- **Domain errors.** Typed errors that the UI cares about. `NetworkUnavailable`, `Unauthorized`, `ValidationFailed(field: String)`. If they're shared across features, put them in `:core:domain`; if feature-specific, keep them with the feature.
- **Pure logic.** Scoring algorithms, validators, state machines. Anything that can be tested without a device.

### 4.2 What does NOT live in the domain layer

- Anything that imports `android.*` or `androidx.*` (except `androidx.annotation` is fine — it's pure annotations).
- Coroutines `Dispatchers.Main` references (use the injected dispatcher abstraction from `:core:common`).
- Retrofit, Room, Hilt, or any framework annotation.
- `String` resources or string IDs.

A feature module's `domain/` sub-package follows the same rule: no Android imports there either, even though the module as a whole has them in other packages. Keep `domain/` purely Kotlin.

### 4.3 Where to put a new type, in 30 seconds

When you create a new domain model or repository interface, ask: *does more than one feature use this?*

- **No** → put it in the feature's `domain/` package.
- **Yes** → put it in `:core:domain`.
- **I don't know yet** → assume one. Lift later when a second feature needs it.

This avoids both over-engineering (preemptively putting everything in `:core:domain`) and the trap of feature-to-feature dependencies (which happens when you put it in `feature:auth/domain/` and then `feature:profile` reaches across to use it).

### 4.4 Use cases — when to write one, when to skip

Don't write a use case for every repository call. The rule:

- **Skip the use case** when a ViewModel needs exactly one repository method, unmodified. The ViewModel can call `userRepository.getCurrentUser()` directly.
- **Write a use case** when there's actual logic — combining repositories, transforming data, enforcing business rules, or when the same operation is invoked from multiple ViewModels.

Bad use case (just forwards):
```kotlin
class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User = userRepository.getCurrentUser()
}
```

Good use case (encapsulates a multi-step flow):
```kotlin
class CompleteOnboardingUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferences: UserPreferences,
    private val analytics: Analytics,
) {
    suspend operator fun invoke(profile: OnboardingProfile) {
        val user = userRepository.createUser(profile)
        preferences.setOnboardingComplete(true)
        analytics.identify(user.id)
    }
}
```

---

## 5. Data layer

Like the domain layer, the data layer lives in two places: inside each feature module (`feature/.../data/`) for feature-owned repositories, and in `:core:data` for shared ones. The rules below apply to both.

### 5.1 Single source of truth

Every piece of data has exactly one source of truth. For most apps the rule is: **the database is the source of truth, the network is a refresh mechanism.**

This means a typical read flow is:

```kotlin
override fun observeUser(): Flow<User> = userDao
    .observeUser()
    .map { it.toDomain() }
    .distinctUntilChanged()

override suspend fun refreshUser() {
    val dto = userApi.getCurrentUser()
    userDao.upsert(dto.toEntity())
}
```

The UI subscribes to `observeUser()` and gets database updates automatically. The UI also calls `refreshUser()` when it wants fresh network data, which writes to the database and triggers a new emission. The UI never gets data directly from the network.

The exceptions are write operations (which usually go network-first then update the cache) and one-shot reads where caching is meaningless (a password-reset request, for example).

### 5.2 Mappers

Each source has its own representation; each gets mapped to the domain.

- `UserDto` (network) → `User` (domain): `UserDto.toDomain()`
- `UserEntity` (database) → `User` (domain): `UserEntity.toDomain()`
- `User` (domain) → `UserEntity` (for writes): `User.toEntity()`

Keep mappers as extension functions in the data module, one file per type. They should be trivially testable — just data in, data out, no I/O.

### 5.3 The Result wrapper

Repositories should return typed errors, not throw arbitrary exceptions to the UI. Use either:

- Kotlin's `Result<T>` for simple cases, OR
- A custom sealed class for richer error info:

```kotlin
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val cause: AppError) : ApiResult<Nothing>
}

sealed interface AppError {
    data object NetworkUnavailable : AppError
    data object Unauthorized : AppError
    data class Server(val code: Int, val message: String?) : AppError
    data class Unknown(val throwable: Throwable) : AppError
}
```

Define `AppError` in `:core:domain` (the UI needs to switch on it). Define `ApiResult` in `:core:common`.

`AppError.Unknown` is the catch-all. Map unmapped throwables to it at the data-layer boundary. Never let a `SocketTimeoutException` reach a ViewModel.

---

## 6. Network layer

The network layer splits across two locations under the hybrid model:

- **`:core:network`** owns the *infrastructure*: the Retrofit instance, OkHttp client, JSON config, interceptors, error-mapping utilities. There's exactly one of each, used by everything.
- **API interfaces and DTOs live with the feature that owns them.** `AuthApi` and `LoginDto` go in `:feature:auth/data/`. If an API is shared across features (rare but possible — e.g. `UserApi` if `User` got lifted to `:core:domain`), it moves to `:core:data`.

This is the same lift rule as before: APIs are feature-owned by default, lifted to `:core` only when a second feature needs them.

### 6.1 Retrofit setup

One `Retrofit` instance per base URL, defined in `:core:network`. For most apps, that's exactly one. Provide it via Hilt:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        @ApplicationContext context: Context,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BODY else NONE
        })
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
    }
}
```

Use **kotlinx.serialization** with Retrofit, not Moshi or Gson. Reasons: it's officially supported by JetBrains, works on KMP, generates code at compile time (no reflection), and integrates cleanly with `Result<T>` types.

### 6.2 API interfaces

One Retrofit interface per logical API surface, not one giant interface for the whole app:

```kotlin
interface UserApi {
    @GET("users/me")
    suspend fun getCurrentUser(): UserDto

    @PATCH("users/me")
    suspend fun updateUser(@Body update: UpdateUserDto): UserDto
}
```

Always use `suspend` for one-shot requests. If you need to expose streaming, use `Flow` via a Retrofit Flow adapter — but 95% of API calls are one-shot.

### 6.3 DTOs

DTOs live with their API — in the feature module that owns it, or in `:core:data` if the API was lifted. They never leak past the repository boundary. Naming convention: `XxxDto` for responses, `XxxRequest` for request bodies. Use `@SerialName` for any field where the wire name doesn't match Kotlin conventions:

```kotlin
@Serializable
data class UserDto(
    val id: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("created_at") val createdAt: Instant,
    val email: String,
)
```

**Don't add business logic to DTOs.** No computed properties, no validation methods. They're dumb wire-format containers. All logic happens after mapping to the domain model.

### 6.4 Error handling

Wrap every API call at the repository boundary. Don't make every Retrofit interface return `ApiResult<T>` — that pollutes the API surface. Instead:

```kotlin
// In the repository implementation
override suspend fun refreshUser(): ApiResult<Unit> = runCatchingApi {
    val dto = userApi.getCurrentUser()
    userDao.upsert(dto.toEntity())
}

// In :core:common
suspend inline fun <T> runCatchingApi(crossinline block: suspend () -> T): ApiResult<T> = try {
    ApiResult.Success(block())
} catch (e: HttpException) {
    ApiResult.Error(when (e.code()) {
        401 -> AppError.Unauthorized
        in 500..599 -> AppError.Server(e.code(), e.message())
        else -> AppError.Server(e.code(), e.message())
    })
} catch (e: IOException) {
    ApiResult.Error(AppError.NetworkUnavailable)
} catch (e: CancellationException) {
    throw e  // critical: never swallow cancellation
} catch (e: Throwable) {
    ApiResult.Error(AppError.Unknown(e))
}
```

The `CancellationException` re-throw is non-negotiable. Swallowing it breaks structured concurrency in subtle, hard-to-debug ways.

### 6.5 Auth

If your API uses bearer tokens, add an `AuthInterceptor` that reads the current token from a `TokenStore` (backed by DataStore) and injects it. Add an `Authenticator` (not interceptor) for refresh on 401 — interceptors run on every request, authenticators only run when the server says "401, try again":

```kotlin
class TokenAuthenticator @Inject constructor(
    private val tokenStore: TokenStore,
    private val authApi: AuthApi,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= 2) return null  // give up after one retry
        val newToken = runBlocking { refreshToken() } ?: return null
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }
    // ...
}
```

Never store tokens in `SharedPreferences` directly. Use DataStore, and consider EncryptedSharedPreferences or Android Keystore for high-sensitivity tokens.

---

## 7. Database layer

The database layer follows the same hybrid split as network:

- **`:core:database`** owns the *infrastructure*: the `RoomDatabase` class, type converters, migrations, the DI module that provides the database instance.
- **Entities and DAOs live with the feature that owns them.** `LoginAttemptEntity` and `LoginAttemptDao` go in `:feature:auth/data/`. If shared across features (e.g. `UserEntity`), they move to `:core:data`.

The `RoomDatabase` class itself references all entities and DAOs across the app — that's unavoidable, since Room needs to know the full schema at compile time. Treat it as a thin aggregator; the real per-entity logic stays in the feature modules.

### 7.1 Entities

Room entities are named `XxxEntity`. Like DTOs, they never leak past the repository boundary — repositories map to domain models before returning.

```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val email: String,
    val createdAt: Long,  // store Instants as Long epoch millis
)
```

Store timestamps as `Long` (epoch millis). Type converters for `Instant` are possible but add complexity for little benefit.

### 7.2 DAOs

One DAO per entity, returning `Flow` for observations and `suspend` for one-shots:

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    fun observe(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun get(id: String): UserEntity?

    @Upsert
    suspend fun upsert(entity: UserEntity)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun delete(id: String)
}
```

Avoid `@Insert(onConflict = REPLACE)` — use `@Upsert` instead. It's more explicit about intent.

### 7.3 Migrations

Every schema change needs an explicit migration. Never use `fallbackToDestructiveMigration()` in production builds — users lose data. Keep migrations in `:core:database/.../migrations/`, one file per migration:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE users ADD COLUMN avatar_url TEXT")
    }
}
```

Write a Room migration test for each. They're fast and catch real bugs.

---

## 8. Presentation layer — feature modules

### 8.1 ViewModel + UiState pattern

One ViewModel per screen, exposing exactly one `StateFlow<UiState>`:

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val refreshOrders: RefreshOrdersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<HomeEffect>()
    val effects: SharedFlow<HomeEffect> = _effects.asSharedFlow()

    init { observeUser() }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.RefreshClicked -> refresh()
            is HomeAction.OrderClicked -> navigateToOrder(action.orderId)
        }
    }

    private fun refresh() = viewModelScope.launch {
        _state.update { it.copy(isRefreshing = true) }
        when (val result = refreshOrders()) {
            is ApiResult.Success -> _state.update { it.copy(isRefreshing = false) }
            is ApiResult.Error -> {
                _state.update { it.copy(isRefreshing = false) }
                _effects.emit(HomeEffect.ShowError(result.cause.toMessage()))
            }
        }
    }
    // ...
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val user: User? = null,
    val orders: List<Order> = emptyList(),
)

sealed interface HomeAction {
    data object RefreshClicked : HomeAction
    data class OrderClicked(val orderId: String) : HomeAction
}

sealed interface HomeEffect {
    data class ShowError(val message: String) : HomeEffect
    data class NavigateToOrder(val orderId: String) : HomeEffect
}
```

Why this shape:

- **One state object** means the UI can't observe inconsistent intermediate states. If `isLoading` is true and `orders` is non-empty, that's a valid combined state (refreshing on a screen that already has data), not a bug.
- **Actions are a sealed interface** so the compiler tells you when a new action needs handling.
- **Effects are separate from state** because they're not state — showing a snackbar once is fundamentally different from "the orders list is empty."

### 8.2 Composables

Composables in a feature module should be split into two roles:

- **Stateful screen-level composable** (`HomeScreen`) — collects state from the ViewModel, wires effects, passes data down.
- **Stateless content composable** (`HomeContent`) — receives `state: HomeUiState` and `onAction: (HomeAction) -> Unit`, no ViewModel reference. This is the one you write `@Preview` annotations for.

```kotlin
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToOrder: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
                is HomeEffect.NavigateToOrder -> onNavigateToOrder(effect.orderId)
            }
        }
    }

    HomeContent(state = state, onAction = viewModel::onAction, snackbarHostState = snackbarHostState)
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    // pure UI
}

@Preview
@Composable
private fun HomeContentPreview() = AppTheme {
    HomeContent(state = HomeUiState(/* ... */), onAction = {}, snackbarHostState = SnackbarHostState())
}
```

The split lets you preview the UI without Hilt and write screenshot tests that don't need a ViewModel.

### 8.3 Navigation

Use Navigation-Compose with type-safe routes (Kotlin Serialization-based, available since Nav-Compose 2.8). Define routes inside each feature module:

```kotlin
// in :feature:home
@Serializable data object HomeRoute

fun NavGraphBuilder.homeGraph(onNavigateToOrder: (String) -> Unit) {
    composable<HomeRoute> {
        HomeScreen(onNavigateToOrder = onNavigateToOrder)
    }
}
```

The `:app` module composes the graphs together. Feature modules never reference each other's routes directly — `:app` is the only place that knows the full nav graph.

**Pass navigation callbacks as lambdas, never `NavController` references.** This keeps Composables testable and previewable.

---

## 9. Dependency injection — Hilt

### 9.1 Modules

One Hilt module per concern, not one giant module. With the hybrid layout, modules split across `:core` and feature locations:

```
:core:network/NetworkModule.kt          // Retrofit instance, OkHttp, Json — shared infrastructure
:core:database/DatabaseModule.kt        // RoomDatabase class, type converters
:core:datastore/DataStoreModule.kt      // DataStore instances
:core:data/RepositoryModule.kt          // @Binds for SHARED repository interfaces (e.g. UserRepository)

:feature:auth/data/AuthModule.kt        // provides AuthApi (from Retrofit), AuthDao (from RoomDatabase),
                                        // @Binds AuthRepository → AuthRepositoryImpl
:feature:profile/data/ProfileModule.kt  // same pattern, profile-specific
```

The pattern inside a feature's Hilt module:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides @Singleton
        fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create()

        @Provides @Singleton
        fun provideAuthDao(db: AppDatabase): AuthDao = db.authDao()
    }
}
```

The feature module injects the shared `Retrofit` and `AppDatabase` from `:core`, then exposes feature-specific APIs and DAOs that only its own code needs.

### 9.2 Scoping

Three scopes matter:

- **`@Singleton`** — one instance per app process. Use for Retrofit, Room database, repositories, anything stateful that should outlive screens.
- **`@ActivityRetainedScoped`** — survives configuration changes. Use rarely; ViewModels handle most of these cases.
- **Unscoped** — new instance per injection. The default. Use for use cases, mappers, anything stateless.

Don't over-scope. A use case that holds no state doesn't need to be `@Singleton` — let Hilt create one per ViewModel.

### 9.3 Binding interfaces

For repository interfaces, use `@Binds` in an abstract class, not `@Provides`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
```

`@Binds` generates less code than `@Provides` for the simple "this interface maps to that class" case.

---

## 10. Threading and coroutines

### 10.1 Dispatcher injection

Don't reference `Dispatchers.IO` or `Dispatchers.Default` directly in production code. Inject them:

```kotlin
// :core:common
@Qualifier @Retention(BINARY) annotation class IoDispatcher
@Qualifier @Retention(BINARY) annotation class DefaultDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides @IoDispatcher fun provideIo(): CoroutineDispatcher = Dispatchers.IO
    @Provides @DefaultDispatcher fun provideDefault(): CoroutineDispatcher = Dispatchers.Default
}
```

This lets tests inject `StandardTestDispatcher` and run coroutine code synchronously. Without this, your tests will be flaky and slow.

### 10.2 Where to do work

- **Network I/O** — Retrofit + suspend functions handle this; just call them, they're already on IO.
- **Disk I/O** — Room's suspend functions are already off the main thread. For file work, use `withContext(ioDispatcher)`.
- **CPU work** — `withContext(defaultDispatcher)` for parsing, sorting, computing.
- **UI** — never explicitly. `viewModelScope` defaults to Main; `collectAsStateWithLifecycle` handles UI dispatch.

### 10.3 viewModelScope rules

- Use `viewModelScope` for any coroutine launched from a ViewModel.
- Never `runBlocking` inside `viewModelScope`.
- Wrap network/database calls that need cancellation safety in their own try/catch — don't let one failed call cancel the scope.

---

## 11. Build setup

### 11.1 Version catalog

Use `gradle/libs.versions.toml` for all dependency declarations. Even on day one. Even in a single-module project. The conversion cost when you eventually split modules is significant; doing it from the start is free.

```toml
[versions]
kotlin = "2.1.0"
agp = "8.7.0"
hilt = "2.52"
compose-bom = "2024.12.01"
retrofit = "2.11.0"
room = "2.6.1"

[libraries]
androidx-compose-bom = { module = "androidx.compose:compose-bom", version.ref = "compose-bom" }
androidx-compose-ui = { module = "androidx.compose.ui:ui" }
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
retrofit-core = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
retrofit-kotlinx-serialization = { module = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter", version = "1.0.0" }
room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
room-ktx = { module = "androidx.room:room-ktx", version.ref = "room" }
room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
```

### 11.2 Convention plugins

Once you have more than two modules, copy-pasting `build.gradle.kts` files becomes a maintenance trap. Move shared build logic into Gradle convention plugins under `:build-logic`. Common ones:

- `android.application.convention` — used by `:app`
- `android.library.convention` — used by all `:feature:*` and most `:core:*`
- `android.library.compose.convention` — adds Compose setup
- `android.library.hilt.convention` — adds Hilt setup
- `jvm.library.convention` — used by `:core:domain` and other pure-JVM modules

Now a feature module's `build.gradle.kts` is 5 lines instead of 80, and updating Kotlin version is one file instead of twenty.

### 11.3 KSP, not KAPT

Use KSP for Hilt, Room, and any annotation processor that supports it. KAPT is roughly 2–4x slower and is being deprecated. As of late 2024 all the major processors (Hilt, Room, Glide, Moshi) have stable KSP support.

```kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)
}
```

---

## 12. Testing strategy

The pyramid for this architecture:

- **Unit tests (most)** — all `domain/` code, whether in `:core:domain` or inside a feature module. Use cases, mappers, validators, scoring logic. JUnit 5 on plain JVM, no Android dependencies. Fast: full suite should run in seconds.
- **Repository tests** — test repository implementations against in-memory Room and a `MockWebServer` for the network. Slower but still no emulator needed. Run them in the module where the repository lives (feature module for feature-owned repos, `:core:data` for shared ones).
- **ViewModel tests** — drive actions, assert state. Use `StandardTestDispatcher` and `Turbine`. These live in the feature module.
- **Compose UI tests (few)** — one smoke test per screen that asserts it renders without crashing. Don't write extensive UI tests; they're slow and brittle. Test logic in ViewModels, not in Composables.

`:core:testing` should provide:

- Fake implementations of shared repository interfaces (e.g. `FakeUserRepository`) that any feature can use in tests.
- A `MainDispatcherRule` for ViewModel tests.
- A `TestData` object with realistic fixtures for shared domain types.

Feature modules typically have their own internal test fakes for feature-only repositories, kept in the feature's `src/test/` directory.

---

## 13. Common mistakes to avoid

These show up over and over in real projects:

1. **Passing `Context` into ViewModels.** Use `@ApplicationContext` if absolutely necessary, but the right fix is usually a `ResourceProvider` interface in `:core:common`.
2. **Exposing `LiveData` from ViewModels.** Use `StateFlow`. LiveData is a relic.
3. **Calling `collect` inside a Composable's body.** Use `collectAsStateWithLifecycle()`. Never `collect` directly — it'll either crash or leak.
4. **Mixing one-shot and observable APIs in the same repository method.** A method either returns `T` once or returns `Flow<T>` forever. Pick one.
5. **Putting Retrofit DTOs into ViewModels.** Map at the repository boundary. Always.
6. **Catching `Throwable` without re-throwing `CancellationException`.** This silently breaks coroutine cancellation.
7. **`fallbackToDestructiveMigration()` in release builds.** Will eventually cost a user their data.
8. **One giant `AppModule` for Hilt.** Split by concern.
9. **Letting a domain layer depend on its own data layer.** The arrow always goes data → domain, never the reverse. This applies to both `:core:domain`/`:core:data` and inside any feature module's own `domain/`/`data/` packages. If you find yourself wanting the reverse, you have a missing interface in domain.
10. **One feature depending on another.** `implementation(project(":feature:profile"))` inside `:feature:auth` is the cardinal sin. Lift the shared concept to `:core` instead.
11. **Preemptively lifting types to `:core`.** Putting `Login` in `:core:domain` because "maybe another feature will need it someday" is how `:core:domain` becomes a junk drawer. Let the first feature own it; lift when a real second consumer appears.
12. **Skipping `Lifecycle` awareness on Flow collection.** Use `collectAsStateWithLifecycle` and `repeatOnLifecycle`. Background collection wastes battery.

---

## 14. When to break the rules

The rules above are starting points, not commandments. Reasonable exceptions:

- **Tiny apps** (one screen, prototype, internal tool) — skip the module split, keep one module, but still write use cases and use the UiState pattern. The discipline pays off even at small scale.
- **No backend** — skip `:core:network` entirely. Don't add Retrofit "just in case."
- **Compose-only, no XML** — assume this throughout. If you have a legacy Fragment, isolate it in its own feature module.
- **No DI at all** — possible for very small apps, but Hilt is cheap enough that even a 3-screen app benefits.

The thing you cannot break and still have an architecture is rule #1: **dependencies point inward.** Everything else is negotiable.

---

## 15. Reading order for a new teammate

If someone joins the project, point them at this file and tell them to read:

1. §1 (the five rules) — five minutes.
2. §2 (module structure) — to understand the layout.
3. §8 (presentation layer) — because that's where most code is.
4. §4 (domain) — to understand what "business logic" means in this codebase.
5. Everything else — as needed when they touch that area.

Most of this document is reference, not required reading.
