package hr.algebra.ark.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.ark.api.ArkFetcher

private const val JOB_ID = 1

@Suppress("DEPRECATION")
class ArkService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        ArkFetcher(this).fetchCreatures()
    }

    companion object {
        fun enqueue(context: Context) {
            enqueueWork(
                context, ArkService::class.java, JOB_ID,
                Intent(context, ArkService::class.java)
            )
        }
    }
}