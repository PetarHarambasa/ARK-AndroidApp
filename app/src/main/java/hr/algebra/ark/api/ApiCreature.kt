package hr.algebra.ark.api

import com.google.gson.annotations.SerializedName

data class ApiCreature(
    @SerializedName("creatureName") val creatureName: String,
    @SerializedName("aboutCreature") val aboutCreature: String,
    @SerializedName("groupType") val groupType: String,
    @SerializedName("diet") val diet: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("urlIcon") val urlIcon: String,
    @SerializedName("urlImage") val urlImage: String,
    @SerializedName("audio") val audio: String
)