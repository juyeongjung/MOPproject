package com.kmu.mopproject;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.io.IOException;
import android.app.Activity;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

//public class bucket_list extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bucket_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//}

public class bucket_list extends Activity implements SurfaceHolder.Callback {

    Button myButton;
    MediaRecorder mediaRecorder;
    SurfaceHolder surfaceHolder;
    boolean is_recording;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        is_recording = false;

        mediaRecorder = new MediaRecorder();
        initMediaRecorder();

        setContentView(R.layout.content_bucket_list);

        SurfaceView myVideoView = (SurfaceView) findViewById(R.id.videoview);
        surfaceHolder = myVideoView.getHolder();
        surfaceHolder.addCallback(this);

        myButton = (Button) findViewById(R.id.mybutton);
        myButton.setOnClickListener(myButtonOnClickListener);
    }

    private Button.OnClickListener myButtonOnClickListener = new Button.OnClickListener() {

        public void onClick(View arg0) {
            if (is_recording) {
                mediaRecorder.stop();
                mediaRecorder.release();
                finish();
            } else {
                mediaRecorder.start();
                is_recording = true;
                myButton.setText("녹화중지");
            }
        }
    };

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        prepareMediaRecorder();
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    private void initMediaRecorder() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile camcorderProfile_HQ = CamcorderProfile
                .get(CamcorderProfile.QUALITY_LOW);
        mediaRecorder.setProfile(camcorderProfile_HQ);
        mediaRecorder.setOutputFile("/sdcard/video.mp4");
        mediaRecorder.setMaxDuration(60000); // �ִ� �ð��� 60�ʷ� �����Ѵ�.
        mediaRecorder.setMaxFileSize(5000000); // �ִ� ����  ũ�⸦ 5MB�� �����Ѵ�.
    }

    private void prepareMediaRecorder() {
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
