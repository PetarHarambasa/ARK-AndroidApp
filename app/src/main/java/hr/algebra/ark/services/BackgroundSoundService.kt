package hr.algebra.ark.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import hr.algebra.ark.R

class BackgroundSoundService : Service() {
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.arkgenesispartone)

        mediaPlayer!!.isLooping = true
        mediaPlayer!!.setVolume(25f, 25f)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return startId
    }

    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
    }
}