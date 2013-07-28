package fitiuh.edu.vn.vnbus;

import fitiuh.edu.vn.gps.*;
import fitiuh.edu.vn.barcode.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicketAction extends Activity implements OnClickListener{
	
	Button accept,dismiss,scan,gps;
	int idchoose;//for identity for show or not show share location for share function
	Intent intent;
	Bundle buldle;
	TextView formatTxt,contentTxt,gpsshow;
	GPSTracker gpsTracker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView(R.layout.activity_ticket);
		
		
		//set on choose ticket
		accept=(Button) findViewById(R.id.btnacceptticket);
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		//set on no choose ticket
		dismiss=(Button) findViewById(R.id.btnNoAcceptticket);
		dismiss.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buldle=new Bundle();
				buldle.putInt("dismiss", 0);
				intent=new Intent(TicketAction.this, Menufunction.class);
				intent.putExtra("idChooseNo", buldle);
				
				startActivity(intent);
				
			}
		});
		
		formatTxt=(TextView) findViewById(R.id.scan_format);
		contentTxt=(TextView) findViewById(R.id.scan_content);
		scan=(Button) findViewById(R.id.scan_button);
		scan.setOnClickListener(this);
		
		
		gpsshow=(TextView) findViewById(R.id.txtshowgps);
		gps=(Button) findViewById(R.id.gps_Button);
		gps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gpsTracker=new GPSTracker(getApplicationContext());
				
				  // check if GPS enabled    
	            if(gpsTracker.canGetLocation()){
	                 
	                double latitude = gpsTracker.getLatitude();
	                double longitude = gpsTracker.getLongitude();
	                 
	                // \n is for new line
	                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
	                gpsshow.setText(String.valueOf(latitude)+"\n"+String.valueOf(longitude));
	                
	            }else{
	                // can't get location
	                // GPS or Network is not enabled
	                // Ask user to enable GPS/network in settings
	                gpsTracker.showSettingsAlert();
	            }
	            
				
			}
		});
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		//check we have a valid result
		if (scanningResult != null) {
			//get content from Intent Result
			String scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			//output to UI
			formatTxt.setText("FORMAT: "+scanFormat);
			contentTxt.setText("CONTENT: "+scanContent);
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public void onClick(View v) {
		//check for scan button
				if(v.getId()==R.id.scan_button){
					//instantiate ZXing integration class
					IntentIntegrator scanIntegrator = new IntentIntegrator(this);
					//start scanning
					scanIntegrator.initiateScan();
				}
	}

}
