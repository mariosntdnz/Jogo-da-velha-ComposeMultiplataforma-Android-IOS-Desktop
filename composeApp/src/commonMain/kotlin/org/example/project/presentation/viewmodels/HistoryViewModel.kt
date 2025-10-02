package org.example.project.presentation.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.models.HistoryEntry
import org.example.project.domain.useCase.DeleteCurrentGameUseCase
import org.example.project.domain.useCase.GetHistoryUseCase
import org.example.project.domain.useCase.HistoryFilterType

enum class HistoryEntryType {
    Continue,
    Finished;

    fun getLabel(): String {
        return when(this) {
            Continue -> "Em andamento"
            Finished -> "Encerrado"
        }
    }

    fun getColor(): Color {
        return when(this) {
            Continue -> Color.Green.copy(alpha = .25f)
            Finished -> Color.Red.copy(alpha = .5f)
        }
    }
}

data class HistoryState(
    val historyItems: List<HistoryEntry> = emptyList(),
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val errorMsg: String = ""
)

class HistoryViewModel(
    filterType: HistoryFilterType,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteCurrentGameUseCase: DeleteCurrentGameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    private var observeHistoryStateJob: Job? = null

    init {
        observeHistoryStateJob = viewModelScope.launch {
            getHistoryUseCase(filterType).collect { history ->
                _state.update { oldState ->
                    oldState.copy(
                        historyItems = history,
                        isLoading = false,
                        hasError = history.isEmpty(),
                        errorMsg = filterType.getErrorLabel()
                    )
                }
            }
        }
    }

    fun delete(gameId: Long) {
        viewModelScope.launch {
            deleteCurrentGameUseCase(gameId)
        }
    }
}