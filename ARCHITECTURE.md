# ExtexisAndroid — Architecture Guide

This document describes the architecture as it actually exists in this project. The goal is that another developer (or an AI agent) can read this file and recreate the same setup from scratch on a new project, or extend this one without breaking conventions.

The architecture is **feature-first with shared core modules**, built on **Jetpack Compose, Hilt, Retrofit + Moshi, DataStore, Coroutines, KSP, and Kotlin 2.x**. Multi-module Gradle. Modern navigation via Compose-Navigation typed routes (kotlinx-serialization).

---

## Table of contents

1. [Five rules that don't bend](#1-five-rules-that-dont-bend)
2. [Module layout](#2-module-layout)
3. [Dependency graph](#3-dependency-graph)
4. [Core modules — what each one is for](#4-core-modules)
5. [Feature modules — the template](#5-feature-modules)
6. [Sub-flows inside a feature](#6-sub-flows-inside-a-feature)
7. [Theme system](#7-theme-system)
8. [Design system (GDS)](#8-design-system-gds)
9. [Navigation](#9-navigation)
10. [Dependency injection](#10-dependency-injection)
11. [State management](#11-state-management)
12. [Error handling](#12-error-handling)
13. [Splash + start destination](#13-splash--start-destination)
14. [Detekt](#14-detekt)
15. [Setting up a new project from scratch](#15-setting-up-a-new-project-from-scratch)
16. [Adding a new feature — checklist](#16-adding-a-new-feature)
17. [Conventions glossary](#17-conventions-glossary)

---

## 1. Five rules that don't bend

1. **Dependencies point inward.** UI → domain. Data → domain. Domain depends on nothing app-specific.
2. **`:core:domain` is pure JVM.** No Android, no Retrofit, no Moshi, no Hilt-Android. Only `javax.inject` is allowed (it's pure JVM).
3. **Features never depend on other features.** If two features need the same use case or model, the use case interface goes to `:core:domain`, the impl goes to `:core:data`, the API goes to `:core:network`. Then both features depend on `:core:domain`.
4. **Repositories return domain types, not DTOs.** Moshi-annotated `ApiXxxResponse` classes never leave the data layer. Map at the repository boundary.
5. **One state per ViewModel.** Use `StateFlow<UiState>` for the screen state, `mutableStateOf(FormState)` for high-frequency text input updates, `Channel<NavigationEvent>` for navigation side effects. Never mix.

---

## 2. Module layout

```
:app                            (Android application — Hilt entry, NavHost wiring, splash)
:build-logic/convention         (Gradle convention plugins)

:core:
  :common                       (pure JVM — ApiResult only, no deps)
  :domain                       (pure JVM — shared use cases, repo interfaces, models, error mappers)
  :network                      (Android lib — Retrofit/OkHttp/Moshi setup, shared DTOs, TokenProvider iface, NetworkConfig, AuthVerificationApi, ApiResult-flavored shared APIs)
  :data                         (Android lib — shared repo impls + Hilt @Binds modules)
  :database                     (Android lib — Room setup [scaffold])
  :datastore                    (Android lib — DataStore wrappers: AccessToken, RefreshToken, AppOnBoarding, Locale)
  :navigation                   (pure JVM — @Serializable typed routes, OtpPurpose, DocumentUploadType)
  :presentation                 (Android lib — BaseViewModel, UiMessageEvent)
  :ui                           (Android lib — theme, typography, design system "gds")
  :testing                      (pure JVM — shared test fakes [scaffold])

:features:
  :welcome                      (splash/welcome screen + AppOnBoardingPreference write)
  :login                        (Login feature — full vertical slice)
  :registration                 (Registration feature — country picker, role selector)
  :forgotpassword               (Forgot password feature)
  :otp                          (OTP feature — multi-purpose, driven by OtpPurpose enum)
  :verifyexistinguser           (Sub-feature gating unverified login users; depends on shared AuthVerificationApi)
  :home                         (Bottom-nav SHELL only — owns HomeShellScreen + nested NavHost + bottom nav)
  :dashboard                    (Tab feature — owned via :home)
  :listings                     (Tab feature)
  :addlisting                   (Tab feature — 3-step wizard)
  :messages                     (Tab feature)
  :menu                         (Tab feature)
  :profile                      (Profile screen — sibling of Home in outer NavHost)
  :kyc                          (KYC hub + sub-flows in sub-packages: kyc/passport/, kyc/document/)
```

---

## 3. Dependency graph

```
:app
 ├── all features
 ├── :core:domain, :core:data, :core:network, :core:datastore, :core:ui, :core:presentation, :core:navigation, :core:common
 │
:features:home
 ├── :features:dashboard, :listings, :addlisting, :messages, :menu  (tab features)
 ├── :core:ui, :core:presentation, :core:navigation, :core:domain, :core:common
 │
:features:* (each)
 ├── :core:domain      (for shared use cases / repo interfaces)
 ├── :core:ui          (theme + design system)
 ├── :core:presentation (BaseViewModel)
 ├── :core:navigation  (typed routes)
 ├── :core:common      (ApiResult)
 ├── :core:network     (only if the feature owns its own API/DTOs)
 │
:core:data
 ├── :core:domain, :core:network, :core:common
 │
:core:domain
 ├── :core:common
 │
:core:network
 ├── :core:common
 │
:core:ui
 ├── :core:presentation (some)
```

**Forbidden:**
- Feature → feature dependencies
- `:core:domain` → anything Android
- `:core:network` → `:core:domain` (the other direction — `:core:data` is the glue)
- `:core:ui` → any feature

---

## 4. Core modules

### `:core:common` (pure JVM)

Owns the absolute minimum that everything else can depend on. Currently contains:
- `ApiResult<T>` sealed class (`Success(data)` / `Error(statusCode, code, message, details)`)

`build.gradle.kts`: pure Kotlin JVM, no Android plugins, no deps.

### `:core:domain` (pure JVM)

Owns:
- Shared use case interfaces + implementations (`SendOtpForVerificationUseCase`, `SendOtpForVerificationUseCaseImpl`)
- Shared repository **interfaces** (`AuthVerificationRepository`)
- Shared error abstraction: `ErrorMapper` interface (in `core:ui/error` — see §12), `AppErrorMapper`

`build.gradle.kts`: pure Kotlin JVM. Single non-project dep: `javax.inject:javax.inject:1` (needed for `@Inject` annotations on use case impls).

```kotlin
plugins { id("org.jetbrains.kotlin.jvm") }
dependencies {
    implementation(project(":core:common"))
    implementation("javax.inject:javax.inject:1")
}
```

### `:core:network` (Android lib)

Owns shared HTTP infrastructure:
- `NetworkConfig` (endpoint paths, base URL constants)
- Retrofit/OkHttp/Moshi Hilt module (`NetworkModule`)
- Interceptors: `AuthorizationTokenInterceptor`
- `executeSafeApiCall` (wraps Retrofit calls into `ApiResult`)
- `TokenProvider` interface (impl lives in `:app`)
- Build config field: `API_BASE_URL`
- **Shared DTOs** used by multiple features (`ApiUserResponse`, `ReSendOtpRequest`, `ApiReSendOtpResponse`)
- **Shared Retrofit API interfaces** (`AuthVerificationApi`) — only when 2+ features call the same endpoint

Single feature-owned APIs (`LoginApi`, `RegistrationApi`) stay in their respective feature modules.

### `:core:data` (Android lib)

Owns:
- Shared repository **implementations** (`AuthVerificationRepositoryImpl`)
- Hilt `@Binds` modules that wire shared repo interfaces → impls, and shared use case interfaces → impls (`AuthVerificationModule`)

This is the glue layer between `:core:domain` (interfaces) and `:core:network` (DTOs). Mappers from DTO → domain model live here too.

### `:core:datastore` (Android lib)

Owns:
- `AppPreference<T>` interface (generic get/set/observe/delete)
- `AppDataStorePreference<T>` abstract base
- Concrete preferences: `AccessTokenPreference`, `RefreshTokenPreference`, `AppOnBoardingPreference`, `LocalePreferences`
- `PreferencesKey` (all DataStore keys)
- `DataStoreModule` Hilt module

### `:core:navigation` (pure JVM)

Owns all `@Serializable` typed routes used by Compose-Navigation. Each route is its own file:
- `LoginRoute`, `RegistrationRoute`, `ForgotPasswordRoute`, `OtpRoute(email, lastName, purpose)`, `HomeRoute`, `ProfileRoute`, `KycRoute`, `PassportVerificationRoute`, `DocumentUploadRoute(type)`, etc.
- Enums used in routes: `OtpPurpose`, `DocumentUploadType`

`build.gradle.kts`: pure JVM + `kotlinx-serialization`.

### `:core:presentation` (Android lib)

Owns:
- `BaseViewModel` (exposes a shared `MutableSharedFlow<UiMessageEvent>`)
- `UiMessageEvent` (sealed: `ToastMessage(UiText)`, `SnackBarMessage(UiText)`)

### `:core:ui` (Android lib)

The biggest core module. Owns:
- Theme (`AppTheme.colors`, `AppTheme.dimensions`, `AppTheme.typography`) — see §7
- Generic Design System ("gds") — see §8
- `UiText` (resource-id or raw string wrapper; resolves to `String` at composition)
- Error system: `ErrorMapper` interface, `AppErrorMapper` — see §12
- Validation use cases: `ValidateEmailUseCase`, `ValidatePasswordUseCase`, `ValidateNonEmptyFieldUseCase` — used directly in ViewModels (these aren't network-dependent)

### `:core:testing`, `:core:database`

Scaffolds for future tests and Room. Not heavily populated yet.

---

## 5. Feature modules

Every feature follows the same vertical-slice layout.

### 5.1 Folder structure

```
features/<feature>/
├── build.gradle.kts
├── src/main/AndroidManifest.xml       (just <manifest />)
├── src/main/res/values/strings.xml    (feature-specific strings)
└── src/main/java/com/estexis/<feature>/
    ├── data/
    │   ├── api/                        (Retrofit interfaces)
    │   ├── remote/                     (Remote data sources — wrap api in executeSafeApiCall)
    │   ├── repository/                 (interface + Impl)
    │   ├── request/                    (Moshi-annotated request DTOs)
    │   ├── response/                   (Moshi-annotated response DTOs)
    │   └── mapper/                     (DTO → domain mapping extensions)
    ├── di/
    │   └── <Feature>Module.kt          (Hilt @Module — binds repo, remote source, use cases; @Provides api)
    ├── domain/
    │   ├── <Feature>UseCase.kt         (interface + Impl)
    │   ├── <Feature>Params.kt          (domain input model)
    │   └── <Feature>ErrorMapper.kt     (sub-error enum + mapper extending AppErrorMapper)
    └── ui/
        ├── <Feature>Screen.kt          (dumb Composable — takes state + event lambda)
        ├── <Feature>ViewModel.kt       (@HiltViewModel)
        ├── <Feature>State.kt           (UiState + optional FormState)
        ├── <Feature>UiEvent.kt         (sealed class of events + NavigationEvent)
        └── <Feature>NavGraph.kt        (NavGraphBuilder extension)
```

### 5.2 `build.gradle.kts` template

```kotlin
plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.estexis.<feature>"
    compileSdk = 36
    defaultConfig { minSdk = 24 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures { compose = true }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:network"))      // omit if feature has no API
    implementation(project(":core:ui"))
    implementation(project(":core:presentation"))

    implementation(libs.retrofit)                 // omit if no API
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    ksp(libs.moshi.codegen)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    testImplementation(libs.junit)
    testImplementation(project(":core:testing"))
}
```

A feature that doesn't own its own data layer (just consumes shared core domain — e.g. `:features:verifyexistinguser`) omits `:core:network`, Retrofit, and Moshi entirely.

### 5.3 Naming conventions

| Element | Convention | Example |
|---|---|---|
| Composable screen | `<Feature>Screen.kt` | `LoginScreen.kt` |
| ViewModel | `<Feature>ViewModel.kt` | `LoginViewModel.kt` |
| UI state | `<Feature>ScreenUiState` / `<Feature>State` | `LoginScreenUiState`, `OtpState` |
| Form state (high-frequency text input) | `<Feature>FormState` | `LoginFormState` |
| UI event | `<Feature>UiEvent` | `LoginUiEvent` |
| Navigation event | `<Feature>NavigationEvent` | `LoginNavigationEvent` |
| Nav graph extension | `<Feature>NavGraph.kt` with `fun NavGraphBuilder.<feature>NavGraph(navController: NavHostController)` | `LoginNavGraph` |
| Repository | `<Feature>Repository` (interface) + `<Feature>RepositoryImpl` | `LoginRepository`, `LoginRepositoryImpl` |
| Use case | `<Action>UseCase` (interface) + `<Action>UseCaseImpl` | `LoginUseCase`, `LoginUseCaseImpl` |
| Hilt module | `<Feature>Module` | `LoginModule` |
| Request DTO | `<Action>Request` | `LoginRequest` |
| Response DTO | `Api<Action>Response` | `ApiLoginResponse` |

### 5.4 The Screen / ViewModel split

`<Feature>Screen.kt` is a **dumb composable**. It takes:
- `state: <Feature>State`
- `formState: <Feature>FormState` (optional)
- `event: (<Feature>UiEvent) -> Unit`

It never references a `ViewModel`. The NavGraph creates the VM and bridges state ↔ events into the screen.

```kotlin
// LoginScreen.kt
@Composable
fun LoginScreen(
    state: LoginScreenUiState,
    formState: LoginFormState,
    event: (LoginUiEvent) -> Unit,
) { /* pure UI */ }
```

```kotlin
// LoginNavGraph.kt
fun NavGraphBuilder.loginNavGraph(navController: NavHostController) {
    composable<LoginRoute> {
        val viewModel: LoginViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    LoginNavigationEvent.ToHome -> navController.navigate(HomeRoute) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                    LoginNavigationEvent.ToSignUp -> navController.navigate(RegistrationRoute)
                    // ...
                }
            }
        }

        LoginScreen(
            state = state,
            formState = viewModel.formState,
            event = viewModel::onEvent,
        )
    }
}
```

### 5.5 ViewModel pattern

```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel() {

    var formState by mutableStateOf(LoginFormState())  // high-frequency text input
        private set

    private val _state = MutableStateFlow(LoginScreenUiState())  // low-frequency UI state
    val state: StateFlow<LoginScreenUiState> = _state

    private val _navigationEvent = Channel<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> formState = formState.copy(email = event.email)
            // ...
            LoginUiEvent.LoginClicked -> login()
        }
    }
}
```

**Key principles:**
- `formState` uses `mutableStateOf` because text fields fire on every keystroke (StateFlow + `copy` would cause unnecessary recompositions of unrelated subscribers).
- `_state` is `MutableStateFlow` because UI state changes are infrequent and need lifecycle-aware collection.
- Navigation events use `Channel` (not `SharedFlow` or `StateFlow`) so each event fires exactly once — even after configuration changes or re-subscription.

---

## 6. Sub-flows inside a feature

When a feature contains multiple sub-flows that share data (e.g. KYC has Passport Verification, NID, Driving License, Bank Statement upload, Utility Bill upload), keep them in **sub-packages within the same Gradle module** rather than splitting into new modules.

### Layout

```
features/kyc/src/main/java/com/estexis/kyc/
├── ui/                              (hub — KYC main screen)
│   ├── KycScreen.kt
│   ├── KycViewModel.kt
│   ├── KycState.kt
│   ├── KycUiEvent.kt
│   └── KycNavGraph.kt               (registers ALL sub-flow nav graphs)
├── passport/
│   └── ui/
│       ├── PassportVerificationScreen.kt
│       ├── PassportVerificationViewModel.kt
│       ├── PassportVerificationState.kt
│       ├── PassportVerificationUiEvent.kt
│       ├── PassportVerificationNavGraph.kt
│       └── steps/                   (per-step composables for wizard flows)
└── document/
    └── ui/
        ├── DocumentUploadScreen.kt
        ├── DocumentUploadViewModel.kt
        ├── DocumentUploadState.kt
        ├── DocumentUploadUiEvent.kt
        └── DocumentUploadNavGraph.kt
```

### Why sub-packages, not separate modules

- Sub-flows often share domain concepts (KycStatus, document patterns, common UI like `DocumentCaptureCard`)
- Avoid the boilerplate cost of N Gradle modules for tightly related flows
- The hub's NavGraph registers all sub-flow nav graphs:

```kotlin
fun NavGraphBuilder.kycNavGraph(navController: NavHostController) {
    composable<KycRoute> { /* hub */ }

    passportVerificationNavGraph(navController)
    documentUploadNavGraph(navController)
    // future: nidVerificationNavGraph, drivingLicenseNavGraph, etc.
}
```

### When to promote to a separate module

- A flow needs a heavyweight SDK (Onfido, Jumio, FaceTec) that other flows shouldn't pull in
- A flow grows past ~10 screens
- A separate team takes ownership

Until then, sub-packages are simpler.

### Single-screen multi-purpose pattern

When the same screen layout serves N variants (e.g. `DocumentUploadScreen` for `BANK_STATEMENT` and `UTILITY_BILL`), pass an enum via the route and derive titles/copy from state:

```kotlin
@Serializable
data class DocumentUploadRoute(val type: DocumentUploadType)

enum class DocumentUploadType { BANK_STATEMENT, UTILITY_BILL }
```

In the VM, read it once via `savedStateHandle.toRoute<DocumentUploadRoute>().type`. To add a third variant, add an enum case + update `state.title`. No new screen, no new VM.

OTP uses the same pattern with `OtpPurpose` (`REGISTRATION`, `FORGOT_PASSWORD`, `VERIFY_EXISTING_USER`).

---

## 7. Theme system

All design tokens live in `:core:ui/theme/` and are accessed via `AppTheme.*` composables.

### Files

```
core/ui/src/main/java/com/estexis/core/ui/theme/
├── AppThemeColors.kt    (AppColorsContract interface + Light/Dark palettes)
├── AppDimensions.kt     (AppSpaces, AppSizes, AppRadius, AppBorder objects + AppDimensions class)
├── AppTextStyles.kt     (TextStyle constants — H1/H2/H3 Regular/Bold, BodyText1/2/3, ParagraphText1)
└── Theme.kt             (ExtexisAndroidTheme composable, MONTSERRAT font family, CompositionLocals, AppTheme object)
```

### Accessing tokens

```kotlin
val colors = AppTheme.colors           // AppColorsContract
val dimensions = AppTheme.dimensions   // AppDimensions
val typography = AppTheme.typography   // AppTextStyles

Text(
    text = "Hello",
    style = AppTheme.typography.H2Bold,    // 30sp/36sp/bold Montserrat
    color = colors.primary,                // light: 0xFFEA5B1B, dark: same
    modifier = Modifier.padding(horizontal = dimensions.spaces.x4)  // 16dp
)
```

### Color tokens (`AppColorsContract`)

```
primary, onPrimary, primaryContainer
secondary, onSecondary, secondaryContainer
tertiary, onTertiary, tertiaryContainer
background, onBackground
surface, surfaceDim
action, error, onError, success, border, white
```

Light + Dark palettes both implement the same contract. Switching is automatic via `isSystemInDarkTheme()` inside `ExtexisAndroidTheme`.

### Dimension tokens

- `dimensions.spaces.x0/x05/x1/x2/x3/x4/x5/x6/x7/x8/x10/x12/x16/x24/x32/x48` — for padding/gaps (4dp unit grid)
- `dimensions.sizes.x0…x192` — for explicit widths/heights/icon sizes
- `dimensions.radius.xsmall/small/medium/large/xlarge/xxlarge/pill/card/button`
- `dimensions.borders.veryLow/low/medium/high/veryHigh`

The unit grid is 4dp (`x1 = 4dp`, `x4 = 16dp`, `x8 = 32dp`, etc.).

### Typography (`AppTextStyles`)

Spec-driven, 14 styles defined to a designer's spec sheet:

```
H1Regular  (36/48)  H1Bold  (36/48)
H2Regular  (30/36)  H2Bold  (30/36)
H3Regular  (24/30)  H3Bold  (24/30)
BodyText1Regular  (16/22)  BodyText1Bold  (16/22)
BodyText2Regular  (14/18)  BodyText2Bold  (14/18)
BodyText3Regular  (12/15)  BodyText3Bold  (12/15)
ParagraphText1Regular  (14/22)  ParagraphText1Bold  (14/22)
```

All use the Montserrat font family loaded from `core/ui/res/font/`. The Material3 `Typography` (`AppMaterialTypography` in `Theme.kt`) maps these into Material slots so default Material components (like `Button`, `TextField`) pick up the styling automatically.

### Theme entry point

Every Composable subtree that uses `AppTheme.*` must be wrapped in `ExtexisAndroidTheme`:

```kotlin
@main
class MainActivity : ComponentActivity() {
    override fun onCreate(...) {
        setContent {
            ExtexisAndroidTheme { AppNavigation(...) }
        }
    }
}
```

Previews always wrap in it too.

---

## 8. Design system (GDS)

`core/ui/.../gds/` is the catalog of reusable composables. The rule for putting something here vs leaving it in a feature:

- **Put in gds when** the pattern will be reused across features (text fields, buttons, dialogs, list rows, dropdowns)
- **Keep in feature when** the composition is feature-specific (e.g. `CountryPickerBottomSheet` lives in `:features:registration` because only registration has countries)

### Current catalog

| Component | Purpose |
|---|---|
| `AppButton` | Primary / Secondary / Outlined button. Loading state. Leading/trailing icons. |
| `AppTextField` | Outlined text field. Label + asterisk for required. Error state. Password toggle (built-in eye icons). `leadingIcon`/`trailingIcon` are `@DrawableRes Int?`. |
| `AppIconButton` | Circular icon button (back arrows, top-bar actions) |
| `AppOtpField` | 6-cell OTP input with focused-cell highlight |
| `AppTitleBar` | Reusable screen header — optional back arrow + optional title + optional right icon |
| `AppLoadingDialog` | Full-screen modal loading overlay (semi-transparent backdrop + white rounded card + spinner) |
| `AppHorizontalDivider` | Standard divider |
| `ActionButton` | Inline text-only button (links like "Sign up now", "Forgot Password?") |
| `VerticalSpacer` / `HorizontalSpacer` | Spacing helpers |
| `StepIndicator` | Numbered step circles + connecting line + labels (for wizards) |
| `StepperCounter` | – / value / + counter for numeric form fields |
| `DropdownPickerField` | Pill-shaped dropdown with chevron, opens `DropdownMenu` |
| `CollapsibleSection` | Orange-bordered pill header + chevron + `AnimatedVisibility` content |
| `ToggleRow` | Icon + title + subtitle + Switch (settings rows) — accepts a `leading` slot composable |
| `ToggleRowLeadingCircle` | Companion helper: colored letter circle used as `leading` for `ToggleRow` |
| `KeyValueRow` | Left label, right value (for property context, summary lists) |
| `ProfileMenuRow` | Pill row with leading icon + label + trailing chevron (settings menu pattern) |
| `UploadCard` | Cloud icon + "Click here to upload" — for image/file pickers |
| `BrowseFilesCard` | Dashed-border card + image icon + "Browse Files" button (KYC document upload pattern) |
| `UploadedFileRow` | File icon + filename + trash icon (after upload) |
| `MediaThumbnail` | Content slot + X delete chip overlay (image gallery in forms) |
| `DocumentCaptureCard` | Pink-blush placeholder when empty, green check when captured, camera-add icon overlay |
| `AppIcon` | Drawable resource aliases (e.g. `AppIcon.IcMail.resId` → `R.drawable.ic_mail`) |

### Component design rules

- Every gds composable has `@Preview` (often multiple — empty/filled/error states)
- All sizing/spacing comes from `AppTheme.dimensions`, never hardcoded `.dp` (`16.dp` is fine in previews only)
- All colors come from `AppTheme.colors`, never `Color(0xFFXXXXXX)` (brand colors specifically — colored social-channel circles are a justified exception)
- All typography comes from `AppTheme.typography` via `Text(... style = ...)`
- Composables expose `modifier: Modifier = Modifier` parameter, default last in non-optional position to follow Compose conventions

---

## 9. Navigation

### 9.1 Typed routes

Every destination has a `@Serializable` route in `:core:navigation`:

```kotlin
@Serializable data object LoginRoute
@Serializable data class OtpRoute(val email: String, val lastName: String, val purpose: OtpPurpose)
```

Routes that need args are `data class`. Routes without args are `data object`.

Enums used in routes (e.g. `OtpPurpose`, `DocumentUploadType`) are plain enums — `kotlinx-serialization` handles them automatically via name.

### 9.2 Per-feature NavGraph extensions

Each feature exposes a single function on `NavGraphBuilder`:

```kotlin
fun NavGraphBuilder.loginNavGraph(navController: NavHostController) {
    composable<LoginRoute> { /* ... */ }
}
```

These take the outer `NavHostController` so the feature can `navController.navigate(SomeOtherRoute)` to siblings.

### 9.3 App-level NavHost

`:app/.../AppNavigation.kt` is the single source of truth for the outer graph:

```kotlin
NavHost(navController, startDestination) {
    welcomeScreenNavGraph(navController)
    loginNavGraph(navController)
    existingUserVerificationNavGraph(navController)
    registrationNavGraph(navController)
    forgotPasswordNavGraph(navController)
    otpNavGraph(navController)
    homeNavGraph(navController)
    profileNavGraph(navController)
    kycNavGraph(navController)         // also registers passport + document sub-flow nav graphs
}
```

### 9.4 Nested NavHost (per-tab state preservation)

`features:home` runs its own nested `NavHost` inside the bottom-nav scaffold. Each tab feature exposes its own `*NavGraph()` (no args — uses parent NavController scope), and the home shell wires them:

```kotlin
// inside HomeShellScreen
val homeNavController = rememberNavController()

NavHost(navController = homeNavController, startDestination = DashboardRoute) {
    dashboardNavGraph()
    listingsNavGraph()
    addListingNavGraph()
    messagesNavGraph()
    menuNavGraph()
}
```

Bottom-nav switching uses standard state-preserving `popUpTo + saveState + launchSingleTop + restoreState`:

```kotlin
navController.navigate(route) {
    popUpTo(DashboardRoute) { saveState = true }
    launchSingleTop = true
    restoreState = true
}
```

The selected-tab indicator is **derived from the back stack**, not stored separately:

```kotlin
val backStackEntry by homeNavController.currentBackStackEntryAsState()
val selectedTab = HomeTab.fromRoute(backStackEntry?.destination?.route)
```

### 9.5 Navigation events from VM

ViewModels never hold a `NavHostController`. They emit `<Feature>NavigationEvent` via a `Channel`, and the NavGraph extension collects events and calls `navController.navigate(...)`:

```kotlin
// in ViewModel
private val _navigationEvent = Channel<LoginNavigationEvent>()
val navigationEvent = _navigationEvent.receiveAsFlow()

// in NavGraph
LaunchedEffect(Unit) {
    viewModel.navigationEvent.collectLatest { event ->
        when (event) {
            LoginNavigationEvent.ToHome -> navController.navigate(HomeRoute) { ... }
        }
    }
}
```

This keeps VMs testable on pure JVM.

### 9.6 Profile + KYC: outside the bottom-nav shell

Tap profile icon in home top bar → navigates to `ProfileRoute` in the **outer** NavHost (not home's nested one). This means the bottom nav literally doesn't render — there's no Scaffold with `bottomBar` above it. Same for KYC, Passport Verification, Document Upload.

The home top bar pulls in `onProfileClicked: () -> Unit` from its surrounding NavGraph extension, which calls `navController.navigate(ProfileRoute)` on the outer NavController.

---

## 10. Dependency injection

### 10.1 Hilt setup

- `@HiltAndroidApp` on `EstexisApp` in `:app`
- `@AndroidEntryPoint` on `MainActivity`
- `@HiltViewModel` on every ViewModel
- KSP-backed (`alias(libs.plugins.ksp)` + `ksp(libs.hilt.compiler)`)

### 10.2 Module per concern

Each feature has its own Hilt module in `feature/.../di/`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    @Binds @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds @Singleton
    abstract fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    companion object {
        @Provides @Singleton
        fun provideLoginApi(
            @RetrofitNoAuthorizationHeader retrofit: Retrofit,
        ): LoginApi = retrofit.create(LoginApi::class.java)
    }
}
```

`@RetrofitNoAuthorizationHeader` is a qualifier defined in `:core:network` — APIs that don't need auth tokens use it.

### 10.3 Shared bindings

`:core:data/.../di/AuthVerificationModule.kt` binds:
- Shared repository impl → interface
- Shared use case impl → interface
- Shared Retrofit API instance

`:app` depends on `:core:data` so Hilt's KSP processor sees these bindings.

### 10.4 ViewModel injection

Always via `hiltViewModel()` from inside `composable<Route>{}`:

```kotlin
composable<LoginRoute> {
    val viewModel: LoginViewModel = hiltViewModel()
    // ...
}
```

If a ViewModel needs route args, inject `SavedStateHandle` and call `savedStateHandle.toRoute<MyRoute>()`:

```kotlin
@HiltViewModel
class OtpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val verifyEmailUseCase: VerifyEmailUseCase,
) : BaseViewModel() {
    private val route = savedStateHandle.toRoute<OtpRoute>()
    // use route.email, route.lastName, route.purpose
}
```

### 10.5 TokenProvider pattern

`core:network` defines `interface TokenProvider`. `:app/.../DataStoreTokenProvider` implements it (delegating to `AccessTokenPreference` and `RefreshTokenPreference` from `:core:datastore`). `:app/.../RepositoryModule` binds the impl. This way `:core:network` stays unaware of DataStore.

---

## 11. State management

### 11.1 Three state kinds

| Kind | Type | Why |
|---|---|---|
| UI state (low-frequency) | `MutableStateFlow<UiState>` | Loading/error states, observable across recompositions, survives config changes |
| Form state (text inputs) | `mutableStateOf(FormState)` | Compose-aware, fine-grained recomposition, no Flow overhead per keystroke |
| Navigation effects | `Channel<NavigationEvent>` | Each event fires exactly once even after reconfiguration |

### 11.2 UiText pattern

`UiText` (in `:core:ui/util/`) is a sealed type that holds either a `@StringRes` ID + args, or a raw `String`. Resolved via `.asString(context)` or `.asString()` (composable, uses `LocalContext`). Used for error messages so VMs stay context-free.

```kotlin
data class LoginScreenUiState(
    val isLoading: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
)
```

### 11.3 Validation use cases

Shared in `:core:ui/util/validation/`:
- `ValidateEmailUseCase` — regex-based email check
- `ValidatePasswordUseCase` — length, complexity, confirm-match
- `ValidateNonEmptyFieldUseCase` — generic non-blank check with custom error ID

Each returns a `ValidationResult(isSuccessful: Boolean, errorMessage: UiText?)`. VMs call them and copy the result into UI state.

---

## 12. Error handling

### 12.1 ApiResult

All network calls return `ApiResult<T>` (in `:core:common`). `executeSafeApiCall { api.something() }` from `:core:network` wraps a Retrofit call into `ApiResult`.

```kotlin
override suspend fun login(email: String, password: String): ApiResult<ApiLoginResponse> =
    executeSafeApiCall { loginApi.login(LoginRequest(email, password)) }
```

### 12.2 Error mapping — interface + delegation

`:core:ui/error/ErrorMapper.kt` defines:

```kotlin
interface ErrorMapper {
    fun map(code: String?): UiText
}
```

`AppErrorMapper` (in same package) is the global object implementing it — handles generic backend codes (`E_2001` unauthorized, `NO_INTERNET`, `E_2999` server, etc.) and falls back to "Something went wrong".

Each feature defines its **own** `<Feature>ErrorMapper` in `:features:<feature>/domain/`:

```kotlin
object LoginErrorMapper : ErrorMapper {
    override fun map(code: String?): UiText = when (code) {
        "E_2002" -> UiText.StringResource(R.string.login_error_invalid_credentials)
        else     -> AppErrorMapper.map(code)   // delegate to global for unknown codes
    }
}
```

No code duplication, no missing fallbacks.

### 12.3 Parsing error bodies

Backend returns errors like `{ "detail": ["E_2002"] }` or `{ "email": ["E_1001"] }`. `parseErrorBody` in `executeSafeApiCall` walks the JSON object, finds the first array value, and uses its first element as the error code. This handles both response shapes uniformly.

---

## 13. Splash + start destination

### 13.1 System splash (Android 12+)

- `androidx.core:core-splashscreen` lib added
- `themes.xml` has `Theme.App.Starting` with `parent="Theme.SplashScreen"`, custom icon + bg
- `AndroidManifest.xml` activity uses `android:theme="@style/Theme.App.Starting"`
- `MainActivity.onCreate` calls `installSplashScreen()` **before** `super.onCreate(savedInstanceState)`

### 13.2 Determining start destination

`SplashViewModel` (in `:app`) is `@HiltViewModel`, injects `AppOnBoardingPreference` and `AccessTokenPreference`. On init it reads both DataStore values and sets `_onboardingState` to one of:

```kotlin
sealed class OnBoardingState {
    data object Loading : OnBoardingState()
    data object FirstLaunch : OnBoardingState()
    data object LoggedOut : OnBoardingState()
    data object LoggedIn : OnBoardingState()
}
```

`MainActivity` keeps the system splash visible while state is `Loading`:

```kotlin
splashScreen.setKeepOnScreenCondition { viewModel.onboardingState.value is OnBoardingState.Loading }
```

Once state resolves, `AppNavigation` picks a start destination:

| State | Start destination |
|---|---|
| `FirstLaunch` | `WelcomeScreenRoute` |
| `LoggedOut` | `LoginRoute` |
| `LoggedIn` | `HomeRoute` |
| `Loading` | (early `return` — render nothing) |

`features:welcome`'s "Get Started" button writes `true` to `AppOnBoardingPreference` before navigating to login, so on next launch state resolves to `LoggedOut` instead of `FirstLaunch`.

---

## 14. Detekt

Detekt is configured at the root build level + `subprojects { apply(...) }`:
- `gradle/libs.versions.toml`: `detekt = "1.23.8"` + `detekt-formatting` library + `detekt` plugin
- Root `build.gradle.kts`: applies plugin + configures per-subproject baselines + enables `autoCorrect = true`
- `config/detekt/detekt.yml`: tuned rules — `LongMethod` ignores `@Composable`, `LongParameterList` ignores `@Composable`/`@HiltViewModel`/`@Inject`, `FunctionNaming` ignores `@Composable`, etc.
- `subprojects { tasks.withType<Detekt>().configureEach { autoCorrect = true } }` — formatting issues auto-fix on every run

Run with `./gradlew detekt --continue` to lint all modules in one pass (the `--continue` flag is also pinned in `gradle.properties` as `org.gradle.continue=true`).

---

## 15. Setting up a new project from scratch

If you're starting fresh and want to recreate this architecture, here's the order:

1. **Create the project** — Android Studio "Empty Compose Activity", Kotlin 2.x, AGP 9.x.
2. **Move the version catalog** — set up `gradle/libs.versions.toml` with everything: kotlin, agp, hilt, ksp, retrofit, moshi, datastore-preferences, compose-bom, navigation-compose, kotlinx-serialization, core-splashscreen, junit. Use this project's version catalog as a reference.
3. **`settings.gradle.kts`** — declare `pluginManagement { includeBuild("build-logic") }` and `dependencyResolutionManagement` with `repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)`. Include `:app` and the core scaffold modules.
4. **`build-logic` convention plugin** — create the `build-logic/convention/` sub-build with at least `AndroidApplicationConventionPlugin` (configures compileSdk, minSdk, java version, compose, buildConfig). Project applies it via `alias(libs.plugins.travelplanner.android.application)` or similar.
5. **Bootstrap the core modules** in this order — each is a 5-minute scaffold:
   - `:core:common` (pure JVM): `ApiResult.kt` sealed class.
   - `:core:domain` (pure JVM + javax.inject): empty for now, awaiting first shared use case.
   - `:core:navigation` (pure JVM + kotlinx-serialization): empty for now, awaiting first route.
   - `:core:network` (Android lib + Hilt + Retrofit + Moshi + KSP): `NetworkConfig`, `NetworkModule`, `executeSafeApiCall`, `TokenProvider` interface, qualifier `@RetrofitNoAuthorizationHeader`.
   - `:core:datastore` (Android lib + Hilt): `AppPreference<T>` interface, `AppDataStorePreference` base, `DataStoreModule`. Add `AccessTokenPreference`, `RefreshTokenPreference`, `AppOnBoardingPreference` when first needed.
   - `:core:presentation` (Android lib): `BaseViewModel`, `UiMessageEvent`.
   - `:core:ui` (Android lib + Compose + material-icons-extended):
     - `theme/AppThemeColors.kt`, `theme/AppDimensions.kt`, `theme/AppTextStyles.kt`, `theme/Theme.kt` (`ExtexisAndroidTheme`, `AppTheme` object, `MONTSERRAT` FontFamily)
     - `util/UiText.kt`
     - `util/validation/` use cases
     - `error/ErrorMapper.kt` + `AppErrorMapper.kt`
     - `gds/` start empty — add components as you build features that need them
     - Add the Montserrat .ttf files to `res/font/`
   - `:core:data` (Android lib + Hilt): empty until first shared use case lands.
6. **Wire the `:app` module:**
   - `MainActivity` + `EstexisApp` (`@HiltAndroidApp`)
   - `themes.xml` with `Theme.App.Starting` (parent `Theme.SplashScreen`)
   - `AndroidManifest.xml` activity uses `Theme.App.Starting`
   - `MainActivity.onCreate` calls `installSplashScreen()` before `super.onCreate`
   - `AppNavigation.kt` skeleton — `NavHost(navController, startDestination)` with feature nav graphs added as you build them
   - `SplashViewModel` + `OnBoardingState` + `setKeepOnScreenCondition`
   - `DataStoreTokenProvider` impl + Hilt binding in `RepositoryModule`
7. **Add Detekt** — `libs.versions.toml`, root `build.gradle.kts` `subprojects { ... }`, `config/detekt/detekt.yml`, `gradle.properties` `org.gradle.continue=true`.
8. **Build the first feature** (e.g. login) following the feature template in §5.

A full bootstrap takes about 2 hours and gives you a working empty app at the welcome screen.

---

## 16. Adding a new feature

For an AI agent or developer adding a feature `:features:newfeature`:

1. **Add to `settings.gradle.kts`:** `include(":features:newfeature")`
2. **Create folder structure:**
   ```
   features/newfeature/
   ├── build.gradle.kts                 (copy from another feature, change namespace)
   └── src/main/
       ├── AndroidManifest.xml          (just <manifest />)
       ├── res/values/strings.xml
       └── java/com/estexis/newfeature/
           ├── data/api/                (if feature has API calls)
           ├── data/remote/
           ├── data/repository/
           ├── data/request/
           ├── data/response/
           ├── di/
           ├── domain/
           └── ui/
   ```
3. **Add route** to `:core:navigation`: `NewFeatureRoute.kt` with `@Serializable data object NewFeatureRoute` (or data class if it takes args).
4. **Write the screen + VM** following the template in §5.3–5.5. Use existing gds components where possible. Add new gds components only when the pattern is genuinely reusable.
5. **Write the NavGraph extension:**
   ```kotlin
   fun NavGraphBuilder.newFeatureNavGraph(navController: NavHostController) {
       composable<NewFeatureRoute> {
           val viewModel: NewFeatureViewModel = hiltViewModel()
           val state by viewModel.state.collectAsState()
           LaunchedEffect(Unit) { /* collect navigation events */ }
           NewFeatureScreen(state = state, event = viewModel::onEvent)
       }
   }
   ```
6. **Register the nav graph** in `:app/.../AppNavigation.kt`:
   ```kotlin
   newFeatureNavGraph(navController)
   ```
7. **Add to `:app/build.gradle.kts`:** `implementation(project(":features:newfeature"))`
8. **Wire navigation FROM other features** by having their VMs emit a `NavigationEvent.ToNewFeature` and the calling NavGraph extension handle it with `navController.navigate(NewFeatureRoute)`.
9. **Verify:** `./gradlew :features:newfeature:compileDebugKotlin` then `./gradlew :app:compileDebugKotlin`.

If the feature contains multiple sub-flows that share state/data, organize them as sub-packages (see §6) rather than creating more modules.

---

## 17. Conventions glossary

| Decision | Why |
|---|---|
| Moshi (not kotlinx-serialization) for DTOs | KSP-friendly, mature, smaller binary footprint, allows `@field:Json(name = "x")` for non-standard JSON keys |
| kotlinx-serialization for routes only | Required by Compose-Navigation typed routes |
| `Channel` for navigation events | Each event fires exactly once; no replay surprises on config change |
| `mutableStateOf(FormState)` for text inputs | Per-keystroke recomposition without StateFlow overhead |
| `MutableStateFlow<UiState>` for UI state | Lifecycle-aware collection, survives config change cleanly |
| Sub-packages over sub-modules for related flows | Less Gradle ceremony, easier to share helpers between flows |
| Outer NavHost for "leaves home" destinations | `popUpTo`/scaffold-level boundaries make "no bottom nav on profile" structural, not conditional |
| `*NavGraph(navController: NavHostController)` extension on `NavGraphBuilder` | Consistent across every feature, VM gets created in correct scope, navigation stays out of VM |
| `hiltViewModel()` inside `composable<Route>{}` | Standard Compose-Nav + Hilt integration. SavedStateHandle gets the route args automatically. |
| `@RetrofitNoAuthorizationHeader` qualifier | Auth-free endpoints (login, register, refresh) use a separate Retrofit instance with no auth interceptor |
| `AppTheme.colors/dimensions/typography` accessors | Single import (`AppTheme`) gives you everything theme-related. No CompositionLocal mistakes. |
| KSP over KAPT | 2-4x faster build, native Kotlin support, Hilt/Moshi/Room all support it stably |
| `--continue` in `gradle.properties` | Detekt and other lint tasks run all modules even if one fails — better feedback per build |
| `enabled && !isLoading` on action buttons | Standard pattern — loading state implicitly disables the button, no separate prop needed |
| Validation use cases in `:core:ui/util/validation/` | They produce `UiText` (UI-layer concept), so they live with UI. They're not network-dependent so no need for domain placement. |

---

## Reading order for a new contributor

1. §1 (rules) — 5 min
2. §2 (module layout) — 5 min
3. §5 (feature template) — 10 min
4. §8 (gds catalog) + §7 (theme) — 5 min skim, reference later
5. §9 (navigation) — 10 min
6. §16 (adding a feature) — when building the first feature

Everything else is reference material consulted when you touch that area.
