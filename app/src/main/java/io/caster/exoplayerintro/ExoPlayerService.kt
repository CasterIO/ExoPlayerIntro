package io.caster.exoplayerintro

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ExoPlayerService : Service() {

  override fun onBind(intent: Intent): IBinder? = null
}
