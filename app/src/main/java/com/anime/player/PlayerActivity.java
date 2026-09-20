package com.anime.player;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class PlayerActivity extends AppCompatActivity {
    
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        PlayerView playerView = findViewById(R.id.player_view);
        
        // Recebe o link de vídeo (.m3u8 ou .mp4) enviado pelo JavaScript
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        // Constrói o ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Prepara e dá play no vídeo automaticamente
        if (videoUrl != null && !videoUrl.isEmpty()) {
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play(); 
        }
    }

    // Libera a memória e pausa o vídeo quando o usuário fechar a tela do player
    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
