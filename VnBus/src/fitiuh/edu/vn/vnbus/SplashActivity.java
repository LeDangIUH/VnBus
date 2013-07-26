package fitiuh.edu.vn.vnbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	
	private static String TAG=SplashActivity.class.getName();
	private static long SLEEP_TIME=2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove action bar(title bar)
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		setContentView(R.layout.activity_splashstart);
		
		IntentLauncher laucher=new IntentLauncher();
		laucher.start();
	}
	
	private class IntentLauncher extends Thread{
		@Override
		/**
    	 * Sleep for some time and than start new activity.
    	 */
		public void run() {
			try {
				//sleep time
				Thread.sleep(SLEEP_TIME*1000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			
			Intent intent=new Intent(SplashActivity.this,TicketAction.class);
			SplashActivity.this.startActivity(intent);
			SplashActivity.this.finish();
		}
	}

}
