package com.example.madcampweek1.utils

object API {
    const val BASE_URL : String = "http://192.249.18.225:5000/"

    const val CLIENT_ID : String = ""

    const val SEARCH_PHOTOS : String = "search/photos"
    const val POST_TOKEN : String = "post/token"
}

enum class RESPONSE_STATE {
    OKAY, FAIL
}