package com.example.biometricdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt.PromptInfo mPromptInfo;
    private BiometricPrompt mBiometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Executor mainExecutor = ContextCompat.getMainExecutor(this);


        mBiometricPrompt = new BiometricPrompt(this, mainExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                Log.d("方","错误了"+errString);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationFailed() {
                Log.d("方","验证失败了");
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                Log.d("方","成功了"+result.toString());
                super.onAuthenticationSucceeded(result);
            }
        });


        mPromptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("支付验证").setSubtitle("我要验证你的指纹").setNegativeButtonText("请使用指纹进行支付")
                .build();

    }

    public void verifyFinger(View view) {
        mBiometricPrompt.authenticate(mPromptInfo);
    }
}