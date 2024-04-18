package com.mvk.imageloader.ui.loader

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import java.util.UUID

/**
 * An implementation of [State] to abstract away loading state logic. Use [doWhileLoading] method
 * to wrap any of your operations to make the value of [State] true while the operation is being
 * performed. Once the operation is executed the state will automatically go to false state.
 *
 * This class supports multiple parallel operations being executed at once, that means, this method
 * can be called from different places to execute different operations at once (parallelly). The
 * value of the [State] will be true while at-least a single task is being actively executed, and
 * the value will go back to false after all of the operations are done.
 */
class LoadingState internal constructor(
    private val state: MutableState<Boolean>,
) : State<Boolean> by state {

    private val inProgressTasks: MutableList<UUID> = mutableListOf()

    fun <T> doWhileLoad(logic: () -> T): T = impl(logic)

    suspend fun <T> doWhileLoading(logic: suspend () -> T): T = impl { logic() }

    private inline fun <T> impl(logic: () -> T): T {

        val taskId = UUID.randomUUID()

        synchronized(inProgressTasks) {
            inProgressTasks.add(taskId)
            state.value = inProgressTasks.isNotEmpty()
        }

        val value = logic()

        synchronized(inProgressTasks) {
            inProgressTasks.remove(taskId)
            state.value = inProgressTasks.isNotEmpty()
        }

        return value
    }
}

fun LoadingState(
    initialValue: Boolean = false,
): LoadingState = LoadingState(
    state = mutableStateOf(initialValue),
)
