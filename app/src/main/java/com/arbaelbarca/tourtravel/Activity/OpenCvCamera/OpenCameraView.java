package com.arbaelbarca.tourtravel.Activity.OpenCvCamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.arbaelbarca.tourtravel.Activity.OpenCvCamera.utils.FolderUtil;
import com.arbaelbarca.tourtravel.Activity.OpenCvCamera.utils.Utilities;
import com.arbaelbarca.tourtravel.Cons;
import com.arbaelbarca.tourtravel.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.io.File;

public class OpenCameraView extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    CameraBridgeViewBase cameraBridgeViewBase;
    ImageView imgTakeCapture;
    OpenCameraUtils cameraUtils;

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface
                        .SUCCESS:
                    cameraBridgeViewBase.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        init();
    }

    private void init() {
        imgTakeCapture = findViewById(R.id.take_picture);
        cameraBridgeViewBase = findViewById(R.id.imgCameraCv);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        imgTakeCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outPicture = Cons.SCAN_IMAGE_LOCATION + File.separator + Utilities.generateFilename();
                FolderUtil.createDefaultFolder(Cons.SCAN_IMAGE_LOCATION);

                Toast.makeText(OpenCameraView.this, "Picture has been taken ", Toast.LENGTH_LONG).show();
                Log.d("", "Path " + outPicture);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("Library Tidak ada", " yess ");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, this, baseLoaderCallback);
        } else {
            Log.d("Library Tidak ada", " No else ");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
