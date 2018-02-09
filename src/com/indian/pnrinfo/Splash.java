package com.indian.pnrinfo;

import com.indian.pnrinfo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {
	Context mContext=Splash.this;
    SharedPreferences appPreferences;
    boolean isAppInstalled = false;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// fullScreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		  appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
          isAppInstalled = appPreferences.getBoolean("isAppInstalled",false);
          if(isAppInstalled==false){
          /**
           * create short code
           */
          Intent shortcutIntent = new Intent(getApplicationContext(),Splash.class);
          shortcutIntent.setAction(Intent.ACTION_MAIN);
          Intent intent = new Intent();
          intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
          intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, 
        		  "PNR");
          intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
          intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
          getApplicationContext().sendBroadcast(intent);
          /**
           * Make preference true
           */
          SharedPreferences.Editor editor = appPreferences.edit();
          editor.putBoolean("isAppInstalled", true);
          editor.commit();
   }

           Thread thread = new Thread() {
			public void run() {

				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				finally {
					Intent intent = new Intent("com.indian.pnrinfo.PNRSTATUS");

					startActivity(intent);
				}
			}
		};
		thread.start();
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
