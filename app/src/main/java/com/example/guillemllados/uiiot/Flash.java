package com.example.guillemllados.uiiot;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by guillemllados on 2/11/17.
 */

public class Flash {

    private boolean isLighOn = false;
    private boolean hasFlash;
    Camera.Parameters params;
    private Context context;

    private Camera camera;

    private Boolean[] data;


    int iteracion;
    int size;

    public Flash(Context context){

        getCamera();
        this.context = context;
        PackageManager pm = context.getPackageManager();

    }

    public void sendData(Boolean[] enviar){
        data = enviar;
        size = enviar.length;
        iteracion = 0;
        chooseonoff();

    }



    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();

            } catch (RuntimeException e) {
                // Log.e("Camera error. Failed to Open. Error:", e.getMessage());
            }
        }
    }
    private void turnOnFlash() {
        //  if(!isLighOn) {
        if (camera==null || params ==null){
            return;
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isLighOn = true;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                iteracion=iteracion+1;
                chooseonoff();
            }
        }, 1);
        //}
    }

    private void turnOffFlash(){
        //  if(isLighOn){

        if (camera ==null || params==null){
            return;
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isLighOn = false;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                iteracion=iteracion+1;
                chooseonoff();
            }
        }, 1);

        // }
    }
    private void turnOffFlashdef(){

        if (camera ==null || params==null){
            return;
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isLighOn = false;
    }
    private void chooseonoff(){
        if(iteracion < size){
            if(data[iteracion]){
                turnOnFlash();
            } else {
                turnOffFlash();
            }
        } else {
            turnOffFlashdef();
        }

    }
    protected void releaseCamera() {
        if (camera != null){
            camera.release();


        }
    }



}
