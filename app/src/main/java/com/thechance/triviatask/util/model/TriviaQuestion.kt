package com.thechance.triviatask.util.model

import com.google.gson.annotations.SerializedName

data class TriviaQuestion(
    @SerializedName("response_code")
    val responseCode: Int?,
    @SerializedName("results")
    val itemTypes: List<TriviaResult>?
)
