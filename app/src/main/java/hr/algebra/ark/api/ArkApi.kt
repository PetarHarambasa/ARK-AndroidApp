package hr.algebra.ark.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://53e6-94-250-167-253.eu.ngrok.io/"

interface ArkApi {
    @GET("arkCreatures")
    fun fetchCreatures(): Call<List<ApiCreature>>
}