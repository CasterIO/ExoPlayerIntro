package io.caster.exoplayerintro;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
  private ExoPlayer exoPlayer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this, null,
        DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);
    DefaultTrackSelector trackSelector = new DefaultTrackSelector();
    exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);

    String userAgent = Util.getUserAgent(this, "ExoPlayerIntro");
    MediaSource mediaSource =
        new ExtractorMediaSource(Uri.parse("asset:///bensound-thejazzpiano.mp3"),
            new DefaultDataSourceFactory(this, userAgent), new DefaultExtractorsFactory(), null,
            null);
    exoPlayer.prepare(mediaSource);
    exoPlayer.setPlayWhenReady(true);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    exoPlayer.release();
  }
}
