package hr.algebra.ark.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.ark.DATA_IMPORTED
import hr.algebra.ark.MainScreenActivity
import hr.algebra.ark.framework.setBooleanPreference
import hr.algebra.ark.framework.startActivity

class ArkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<MainScreenActivity>()
    }
}