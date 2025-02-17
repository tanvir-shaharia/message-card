package com.nascenia.composeexcercise

import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationsApi {

    @POST("/send")
    suspend fun sentMassage(
        @Body body: SendMessageDto
    )

    @POST("/broadcast")
    suspend fun broadcast(
        @Body body: SendMessageDto
    )
}