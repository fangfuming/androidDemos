package com.example.cameraxdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private PreviewView mPreviewView;
    private Button mTakePhotoBtn;
    private ListenableFuture<ProcessCameraProvider> mProviderListenableFuture;
    private Camera mCamera;
    private ProcessCameraProvider mProcessCameraProvider;
    private ImageCapture mImageCapture;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

    }

    private void initEvent() {

        mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //默认有权限
                ImageCapture.OutputFileOptions outputFileOptions =
                        new ImageCapture.OutputFileOptions.Builder(new File(getBaseContext().getExternalCacheDir(),System.currentTimeMillis()+".jpg")).build();
                mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(MainActivity.this),
                        new ImageCapture.OnImageSavedCallback() {
                            @Override
                            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                                Toast.makeText(getBaseContext(),"保存成功",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(@NonNull ImageCaptureException exception) {
                                Toast.makeText(getBaseContext(),"保存失败",Toast.LENGTH_LONG).show();
                            }
                        }
                );

            }

            ;
        });
    }

    //绑定预览
    private void bindPreView(ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        mImageCapture = new ImageCapture.Builder().build();
        mCamera = processCameraProvider.bindToLifecycle(this, cameraSelector, preview, mImageCapture);
        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());
    }

    //检查权限
    private boolean checkYourPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int i = checkSelfPermission(Manifest.permission.CAMERA);
            int i2 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (i == PackageManager.PERMISSION_GRANTED && i2 == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }else {
            return true;
        }

        return false;
    }

    private void initView() {
        mTakePhotoBtn = findViewById(R.id.takePhotoBtn);
        mPreviewView = findViewById(R.id.preView);

        mProviderListenableFuture = ProcessCameraProvider.getInstance(this);

        //默认赋予了权限 打开预览
        if (checkYourPermission()) {
            //有权限 打开摄像头
            mProviderListenableFuture.addListener(()->{
                try {
                    mProcessCameraProvider = mProviderListenableFuture.get();
                    bindPreView(mProcessCameraProvider);
                }catch (Exception e){

                }

            }, ContextCompat.getMainExecutor(MainActivity.this));
        }else {
            //无权限
            //todo
        }
    }
}