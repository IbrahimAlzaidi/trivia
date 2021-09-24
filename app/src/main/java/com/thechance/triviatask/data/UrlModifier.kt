package com.thechance.triviatask.data

object UrlModifier {
    private val list = listOf("easy","medium","hard")
    private var myUrl: String = ""
    val url: String
        get() = myUrl

    fun getUrl(amount: String?,difficulty: String?){
        myUrl = "https://opentdb.com/api.php?amount=${amount}&difficulty=${difficulty}&type=multiple"
    }
    val difficultyList: List<String>
        get() = list
}
