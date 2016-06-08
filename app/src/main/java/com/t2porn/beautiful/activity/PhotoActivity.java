package com.t2porn.beautiful.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.t2porn.beautiful.R;

public class PhotoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Uri uri = Uri.parse(url);
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.image_view);
        draweeView.setImageURI(uri);
    }
}
