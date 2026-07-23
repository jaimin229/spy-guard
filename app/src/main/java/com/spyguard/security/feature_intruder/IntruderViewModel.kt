package com.spyguard.security.feature_intruder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.domain.repository.IntruderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntruderViewModel @Inject constructor(
    private val intruderRepository: IntruderRepository
) : ViewModel() {

    val intruderLogs: Flow<List<IntruderLogEntity>> = intruderRepository.getAllIntruderLogs()

    fun deleteIntruderLog(log: IntruderLogEntity) {
        viewModelScope.launch {
            intruderRepository.deleteIntruderLog(log)
        }
    }

    fun clearAllIntruderLogs() {
        viewModelScope.launch {
            intruderRepository.clearAllIntruderLogs()
        }
    }
}
