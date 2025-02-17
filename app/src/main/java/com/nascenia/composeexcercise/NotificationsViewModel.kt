package com.nascenia.composeexcercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

   private var state by mutableStateOf(ChatState())

    fun onRemoteTokenChange(newToken: String){
       state = state.copy(
           remoteToken = newToken
       )
    }

    fun onSubmitRemoteToken(newToken: String){
       state = state.copy(
           isEnteringToken = false
       )
    }

    fun onMessageChange(message: String){
        state = state.copy(
            messageText = message
        )
    }

    fun sendMessage(sendMessageDto: SendMessageDto) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.notificationsApi.sentMassage(sendMessageDto)
            } catch (e: Exception) {
                // Handle failure, maybe show a toast or log the error
            }
        }
    }

    fun broadcast(sendMessageDto: SendMessageDto) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.notificationsApi.broadcast(sendMessageDto)
            } catch (e: Exception) {
                // Handle failure, maybe show a toast or log the error
            }
        }
    }
}