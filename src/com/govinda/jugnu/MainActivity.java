package com.govinda.jugnu;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.govinda.jugnu.R;

public class MainActivity extends Activity
{	
	public ImageView lightbulb;
	private static boolean isLightOn = false;
    private static Camera camera;
    Parameters param;
    PowerManager pm;
    boolean isScreenon;
    WakeLock wakelock;
    public static boolean active=false;
	
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	active=false;
    }
 	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	active=true;
    	// stopService(new Intent(getApplicationContext(), ShakeService.class));
         //SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
 	  //  ShakeDetector sd = new ShakeDetector(this);
 	   // sd.start(sensorManager);
         
         
         
         pm = (PowerManager) getSystemService(POWER_SERVICE);
 		wakelock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,"My brightlight");
       
         if (!getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
 		{
         	Toast.makeText(getApplicationContext(), "Your device does not have flash light support", Toast.LENGTH_SHORT).show();
 	        finish();
 		}else{
 			 lightbulb = (ImageView) findViewById(R.id.lightbulb);
 			 
 			 if(isLightOn) {
 				 lightbulb.setImageResource(R.drawable.lighton_large);
 			 } else {
 				 lightbulb.setImageResource(R.drawable.lightof_large);
                  
 			 }    
 		}
        lightbulb.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 			if (isLightOn) {
                  if (camera != null) {
                          camera.stopPreview();
                          camera.release();
                          camera = null;
                          wakelock.release();
                  }
                  lightbulb.setImageResource(R.drawable.lightof_large);
                  isLightOn = false;
                  
 			} else {
 				camera = Camera.open();

                  if(camera == null) {
                          Toast.makeText(getApplicationContext(), "Your device does not have flash light support", Toast.LENGTH_SHORT).show();
                  } else {
                          // Set the torch flash mode
                          param = camera.getParameters();
                          List<String> pList = camera.getParameters().getSupportedFlashModes();
                          if(pList.contains(Parameters.FLASH_MODE_TORCH)){
                         	 param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                          }else if(pList.contains(Parameters.FLASH_MODE_ON)){
                         	 param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                          }
                          
                          param.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
                          try {
                                  camera.setParameters(param);
                                  camera.startPreview();
                                  isLightOn = true;
                                  lightbulb.setImageResource(R.drawable.lighton_large);
                                  wakelock.acquire();
                          } catch (Exception e) {
                                  Toast.makeText(getApplicationContext(),"Your device does not have flash light support", Toast.LENGTH_SHORT).show();
                                  wakelock.release();
                          }
                  }
          }
 		
 			}
 		});
         
    }
    

	/*@Override
	public void hearShake() {
		// TODO Auto-generated method stub
		if (isLightOn) {
           

            lightbulb.setImageResource(R.drawable.lightof_large);
            isLightOn = false;
            
		} else {
			camera = Camera.open();

            if(camera == null) {
                    Toast.makeText(getApplicationContext(), "Your device does not have flash light support", Toast.LENGTH_SHORT).show();
            } else {
                    // Set the torch flash mode
                    param = camera.getParameters();
                    List<String> pList = camera.getParameters().getSupportedFlashModes();
                    if(pList.contains(Parameters.FLASH_MODE_TORCH)){
                   	 param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    }else if(pList.contains(Parameters.FLASH_MODE_ON)){
                   	 param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    }
                    
                    param.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
                    try {
                            camera.setParameters(param);
                            camera.startPreview();
                            isLightOn = true;
                            lightbulb.setImageResource(R.drawable.lighton_large);
                            wakelock.acquire();
                    } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Your device does not have flash light support", Toast.LENGTH_SHORT).show();
                            wakelock.release();
                    }
            }
    }
	}*/
     
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
}
