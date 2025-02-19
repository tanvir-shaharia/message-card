package com.nascenia.composeexcercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

   var state by mutableStateOf(ChatState())

    fun onRemoteTokenChange(newToken: String){
       state = state.copy(
           remoteToken = newToken
       )
    }

    fun onSubmitRemoteToken(){
       state = state.copy(
           isEnteringToken = false
       )
    }

    fun onMessageChange(message: String){
        state = state.copy(
            messageText = message
        )
    }

    fun sendMessage() {
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = state.remoteToken ,
                notification = NotificationBody(
                    title = "New message!",
                    body = state.messageText
                )
            )
            try {
                val response = RetrofitClient.notificationsApi.sentMassage(messageDto)
            } catch (e: Exception) {
                // Handle failure, maybe show a toast or log the error
            }
        }
    }

    fun broadcast() {
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = state.remoteToken ,
                notification = NotificationBody(
                    title = "New message!",
                    body = state.messageText
                )
            )
            try {
                val response = RetrofitClient.notificationsApi.broadcast(messageDto)
            } catch (e: Exception) {
                // Handle failure, maybe show a toast or log the error
            }
        }
    }
}