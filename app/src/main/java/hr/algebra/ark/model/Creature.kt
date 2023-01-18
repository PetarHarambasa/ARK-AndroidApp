package hr.algebra.ark.model

data class Creature(
    var _id: Long?,
    var creatureName: String,
    var aboutCreature: String,
    var groupType: String,
    var diet: String,
    var temperament: String,
    var urlIcon: String,
    var urlImage: String,
    var audio: String,
    var favourite: Boolean,
    var ratingStars: Float
)
