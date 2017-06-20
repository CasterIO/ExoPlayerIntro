package io.caster.exoplayerintro

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {
  private lateinit var exoPlayer: ExoPlayer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val renderersFactory = DefaultRenderersFactory(this,
        null, // drmSessionManager: DrmSessionManager
        DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)

    val trackSelector = DefaultTrackSelector()
    exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector)

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

  override fun onDestroy() {
    super.onDestroy()

    exoPlayer.release()
  }
}
