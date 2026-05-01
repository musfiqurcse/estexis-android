package com.travelhugai.travelplanner.core.observers

import com.extexis.core.android.data.qualifiers.dispachers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CreatePlanObserver {

    val flow: Flow<Unit>

    suspend fun onPlanCreated()

}

class CreatePlanObserverImpl @Inject constructor(
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : CreatePlanObserver {

    private val _planCreatedEvents = MutableSharedFlow<Unit>(
        replay = 0,              // don’t replay old events
        extraBufferCapacity = 1  // allow a little buffering
    )

    override val flow = _planCreatedEvents

    private fun emitLoginStatus() {
        CoroutineScope(dispatcher).launch {
            _planCreatedEvents.emit(Unit)
        }
    }

    override suspend fun onPlanCreated() {
        emitLoginStatus()
    }

}
