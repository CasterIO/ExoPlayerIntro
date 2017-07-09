package io.caster.exoplayerintro

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {
  private lateinit var exoPlayer: SimpleExoPlayer
  private lateinit var eventLogger: EventLogger

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun initializeExoplayer() {
    val renderersFactory = DefaultRenderersFactory(this,
        null, // drmSessionManager: DrmSessionManager
        DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)

    val trackSelector = DefaultTrackSelector()
    exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector)

    eventLogger = EventLogger(trackSelector)
    exoPlayer.addListener(eventLogger)
    exoPlayer.setAudioDebugListener(eventLogger)
    exoPlayer.setMetadataOutput(eventLogger)

    val userAgent = Util.getUserAgent(this, "ExoPlayerIntro")
    val mediaSource = ExtractorMediaSource(
        Uri.parse("asset:///bensound-thejazzpiano.mp3"),
        DefaultDataSourceFactory(this, userAgent),
        DefaultExtractorsFactory(),
        null, // eventHandler: Handler
        null) // eventListener: ExtractorMediaSource.EventListener

    exoPlayer.prepare(mediaSource)
    exoPlayer.playWhenReady = true
  }

  fun releaseExoplayer() {
    exoPlayer.release()
  }

  override fun onStart() {
    super.onStart()
    // Account for multi-window introduced in Nougat
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      initializeExoplayer()
    }
  }

  override fun onResume() {
    super.onResume()
    // Account for multi-window introduced in Nougat
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      initializeExoplayer()
    }
  }

  override fun onPause() {
    super.onPause()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      releaseExoplayer()
    }
  }

  override fun onStop() {
    super.onStop()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      releaseExoplayer()
    }
  }

}
