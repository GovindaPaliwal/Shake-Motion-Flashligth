package com.govinda.jugnu;

import android.app.Application;
import android.content.Intent;

public class Jugnu extends Application
{
 @Override
public void onCreate() {
	// TODO Auto-generated method stub
	super.onCreate();
	  startService(new Intent(getApplicationContext(), ShakeService.class));
      
}
}
