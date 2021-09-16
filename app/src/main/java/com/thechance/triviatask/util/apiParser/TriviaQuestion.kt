package com.thechance.triviatask.util.apiParser

import com.thechance.triviatask.util.apiParser.Result

data class TriviaQuestion(
    val response_code: Int?,
    val results: List<Result>?
)