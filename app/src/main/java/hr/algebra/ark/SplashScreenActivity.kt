package hr.algebra.ark

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.ark.databinding.ActivitySplashScreenBinding
import hr.algebra.ark.framework.*
import hr.algebra.ark.services.ArkService
import hr.algebra.ark.services.BackgroundSoundService


private const val DELAY = 5000L
const val DATA_IMPORTED = "hr.algebra.ark.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideUI(window)

        playBackgroundMusic()

        startAnimations()

        redirect()
    }

    private fun playBackgroundMusic() {
        val intent = Intent(this, BackgroundSoundService::class.java)
        startService(intent)
    }

    private fun startAnimations() {
        binding.tvSplashText.applyAnimation(R.anim.loading)
        binding.ivLoading.applyAnimation(R.anim.loading)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<MainScreenActivity>() }
        } else {
            if (isOnline()) {
                ArkService.enqueue(this)
            } else {
                binding.tvSplashText.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() }
            }
        }
    }
}