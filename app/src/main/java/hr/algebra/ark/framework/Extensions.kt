package hr.algebra.ark.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.ark.model.Creature
import hr.algebra.ark.provider.ARK_PROVIDER_CONTENT_URI

fun View.applyAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() = startActivity(
    Intent(this, T::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
)

inline fun <reified T : Activity> Context.startActivity(key: String, value: Int) = startActivity(
    Intent(this, T::class.java).apply {
        putExtra(key, value)
    }
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
)

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean = true) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)


fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { cap ->
            return cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || cap.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
    }
    return false
}

fun callDelayed(delay: Long, runnable: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(
        runnable,
        delay
    )
}

fun hideUI(window: Window) {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
}

@SuppressLint("Range")
fun Context.fetchCreatures(): MutableList<Creature> {
    val creatures = mutableListOf<Creature>()
    val cursor = contentResolver.query(ARK_PROVIDER_CONTENT_URI, null, null, null, null)

    while (cursor != null && cursor.moveToNext()) {
        creatures.add(
            Creature(
                cursor.getLong(cursor.getColumnIndex(Creature::_id.name)),
                cursor.getString(cursor.getColumnIndex(Creature::creatureName.name)),
                cursor.getString(cursor.getColumnIndex(Creature::aboutCreature.name)),
                cursor.getString(cursor.getColumnIndex(Creature::groupType.name)),
                cursor.getString(cursor.getColumnIndex(Creature::diet.name)),
                cursor.getString(cursor.getColumnIndex(Creature::temperament.name)),
                cursor.getString(cursor.getColumnIndex(Creature::urlIcon.name)),
                cursor.getString(cursor.getColumnIndex(Creature::urlImage.name)),
                cursor.getString(cursor.getColumnIndex(Creature::audio.name)),
                cursor.getInt(cursor.getColumnIndex(Creature::favourite.name)) == 1

            )
        )
    }
    return creatures
}