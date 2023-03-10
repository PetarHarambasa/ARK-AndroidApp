package hr.algebra.ark.api

import retrofit2.Call
import retrofit2.http.GET

//change it every time you start new ngrok forwarding api
const val API_URL = "https://fd03-94-250-167-253.eu.ngrok.io/"

interface ArkApi {
    @GET("arkCreatures")
    fun fetchCreatures(): Call<List<ApiCreature>>
}