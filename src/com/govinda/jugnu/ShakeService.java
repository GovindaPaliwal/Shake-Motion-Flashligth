package com.govinda.jugnu;

import java.util.Random;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ShakeService extends Service implements ShakeDetector.Listener
{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	 	    ShakeDetector sd = new ShakeDetector(this);
	 	    sd.start(sensorManager);
	 	  //  Toast.makeText(getApplicationContext(), "Start", 2000).show();
	 	    
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	 	    ShakeDetector sd = new ShakeDetector(this);
	 	    sd.stop();
	 	  //  Toast.makeText(getApplicationContext(), "Start", 2000).show();
	}
    @Override
    public IBinder onBind(Intent intent) 
    {
    	
        return null;
    }


	@Override
	public void hearShake() {
		// TODO Auto-generated method stub
		Log.e("Shake", "Yes");
		/*Intent it = new Intent("intent.my.action");

		it.setComponent(new ComponentName(getPackageName(), MainActivity.class.getName()));
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(it);	*/
//startActivity(new Intent(getApplicationContext(), MainActivity.class));
	   
	/*	Intent receiver = new Intent(getApplicationContext(), FlashlightWidgetReceiver.class);
        receiver.setAction("COM_FLASHLIGHT");
       receiver.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, receiver, 0);

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
   appWidgetManager.updateAppWidget(appWidgetIds, views);*/
		if (MainActivity.active==false) 
		{
			   AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(getApplicationContext());
					ComponentName thisAppWidget = new ComponentName(getApplicationContext()
						.getPackageName(), FlashlightWidgetReceiver.class.getName());
					Intent updateIntent = new Intent(getApplicationContext(), FlashlightWidgetReceiver.class);
					int[] appWidgetIds = appWidgetManager
						.getAppWidgetIds(thisAppWidget);
					updateIntent.setAction("COM_FLASHLIGHT");
				        updateIntent.putExtra(AppWidgetManager.
				                 EXTRA_APPWIDGET_IDS, appWidgetIds);
					getApplicationContext().sendBroadcast(updateIntent);
			   
			   
		}

   
	}
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	// TODO Auto-generated method stub
    	return super.onStartCommand(intent, flags, startId);
    }
	

}

