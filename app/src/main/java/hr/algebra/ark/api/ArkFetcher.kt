package hr.algebra.ark.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.ark.framework.sendBroadcast
import hr.algebra.ark.handler.downloadAudioAndStore
import hr.algebra.ark.handler.downloadIconAndStore
import hr.algebra.ark.handler.downloadImageAndStore
import hr.algebra.ark.model.Creature
import hr.algebra.ark.provider.ARK_PROVIDER_CONTENT_URI
import hr.algebra.ark.services.ArkReceiver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArkFetcher(private val context: Context) {

    private var arkApi: ArkApi

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        arkApi = retrofit.create(ArkApi::class.java)
    }

    fun fetchCreatures() {
        val request = arkApi.fetchCreatures()
        request.enqueue(object : Callback<List<ApiCreature>> {
            override fun onResponse(
                call: Call<List<ApiCreature>>,
                response: Response<List<ApiCreature>>
            ) {
                response?.body()?.let { populateCreatures(it) }
            }

            override fun onFailure(call: Call<List<ApiCreature>>, t: Throwable) {
                Log.e("API", t.toString(), t)
            }

        })
    }

    private fun populateCreatures(apiCreatureList: List<ApiCreature>) {
        GlobalScope.launch {
            apiCreatureList.forEach {
                val iconPath = downloadIconAndStore(context, it.urlIcon)
                val imagePath = downloadImageAndStore(context, it.urlImage)
                val audioPath = downloadAudioAndStore(context, it.audio)

                val values = ContentValues().apply {
                    put(Creature::creatureName.name, it.creatureName)
                    put(Creature::aboutCreature.name, it.aboutCreature)
                    put(Creature::groupType.name, it.groupType)
                    put(Creature::diet.name, it.diet)
                    put(Creature::temperament.name, it.temperament)
                    put(Creature::urlIcon.name, iconPath)
                    put(Creature::urlImage.name, imagePath)
                    put(Creature::audio.name, audioPath)
                    put(Creature::favourite.name, false)
                    put(Creature::ratingStars.name, 0)
                }
                context.contentResolver.insert(ARK_PROVIDER_CONTENT_URI, values)
            }
            context.sendBroadcast<ArkReceiver>()
        }
    }
}