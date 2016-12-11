package me.ashimrajkonwar.androidexplore;

import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        VideoView coverVideo = (VideoView) findViewById(R.id.surface_view);

        ViewGroup.LayoutParams cover_layer_param = coverVideo.getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        cover_layer_param.width = size.x;




        String uriPath = "android.resource://me.ashimrajkonwar.androidexplore/" + R.raw.cover_new;
        Uri uri2 = Uri.parse(uriPath);
        coverVideo.setVideoURI(uri2);
        coverVideo.requestFocus();
        coverVideo.start();
    }
}
