package me.ashimrajkonwar.androidexplore;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;



import android.widget.LinearLayout;


import java.io.IOException;

public class MainActivity extends Activity implements TextureView.SurfaceTextureListener{




    private static final String TAG = MainActivity.class.getName();

    private MediaPlayer mMediaPlayer;
    private TextureView mTextureView;
    private float mVideoWidth;
    private float mVideoHeight;
    private static final String FILE_NAME = "cover_x.MP4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        VideoView coverVideo = (VideoView) findViewById(R.id.surface_view);
//
//        ViewGroup.LayoutParams cover_layer_param = coverVideo.getLayoutParams();

//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        cover_layer_param.width = size.x;




//        String uriPath = "android.resource://me.ashimrajkonwar.androidexplore/" + R.raw.cover_new;
//        Uri uri2 = Uri.parse(uriPath);
//        coverVideo.setVideoURI(uri2);
//        coverVideo.requestFocus();
//        coverVideo.start();

//        calculateVideoSize();
//        initView();
    }



    private void calculateVideoSize() {
        try {
            AssetFileDescriptor afd = getAssets().openFd(FILE_NAME);
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(
                    afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            String height = metaRetriever
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String width = metaRetriever
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            mVideoHeight = Float.parseFloat(height);
            mVideoWidth = Float.parseFloat(width);






        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (NumberFormatException e) {
            Log.d(TAG, e.getMessage());
        }
    }


    private void initView() {
        mTextureView = (TextureView) findViewById(R.id.textureView);
        mTextureView.setSurfaceTextureListener(this);



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int coverFrameHeight = Math.round((mVideoHeight / mVideoWidth) * (size.x));
        int coverFrameWidth = size.x;


        FrameLayout rootView = (FrameLayout) findViewById(R.id.rootView);
        ViewGroup.LayoutParams cover_layer_param = rootView.getLayoutParams();
        cover_layer_param.height = coverFrameHeight;



        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(coverFrameWidth, coverFrameHeight));


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Surface surface = new Surface(surfaceTexture);

        try {
            AssetFileDescriptor afd = getAssets().openFd(FILE_NAME);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer
                    .setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.setSurface(surface);
            mMediaPlayer.setLooping(true);

            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getMessage());
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        } catch (IllegalStateException e) {
            Log.d(TAG, e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }


    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
