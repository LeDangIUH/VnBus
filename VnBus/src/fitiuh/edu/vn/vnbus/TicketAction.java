package fitiuh.edu.vn.vnbus;

import fitiuh.edu.vn.gps.*;
import fitiuh.edu.vn.barcode.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicketAction extends Activity implements OnClickListener{
	
	Button accept,dismiss,scan,gps,phone;
	int idchoose;//for identity for show or not show share location for share function
	Intent intent;
	Bundle buldle;
	TextView formatTxt,contentTxt,gpsshow,phoneshow;
	GPSTracker gpsTracker;
	//call class
	SwitchChoose switchChoose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView(R.layout.activity_ticket);
		
		//set on choose ticket
		accept=(Button) findViewById(R.id.btnacceptticket);
		accept.setOnClickListener(this);
		
		
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
		
		//formatTxt=(TextView) findViewById(R.id.scan_format);
		//contentTxt=(TextView) findViewById(R.id.scan_content);
		
		scan=(Button) findViewById(R.id.scan_button);
		scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//dialogShowOK("MaTuyen11");
				switchChoose=new SwitchChoose();
				int a=switchChoose.getnumberBus("MaTuyen11");
				
				if(a==0){
					dialogShowNoValid();
				}else{
					dialogShowOK("MaTuyen1");
				}
			}
		});
		
		
		gpsshow=(TextView) findViewById(R.id.txtshowgps);
		gps=(Button) findViewById(R.id.gps_Button);
		gps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gpsTracker=new GPSTracker(TicketAction.this);
				
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
	                gpsTracker.showSettingsAlert();//I don't work
	            }
	            
			}
		});
		
		
		
		phoneshow=(TextView) findViewById(R.id.show_phonetxt);
		phone=(Button) findViewById(R.id.phone_btn);
		phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TelephonyManager manger=(TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
				//phoneshow.setText(manger.getLine1Number());
				//phoneshow.setText(manger.getSimSerialNumber());
				
				//phoneshow.setText(getMy10DigitPhoneNumber());
				
				TelephonyManager tm=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				
				//phoneshow.setText(tm.getLine1Number());
				phoneshow.setText(tm.getSimSerialNumber());
			}
		});
	}
	
	private String getMyPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
            getSystemService(Context.TELEPHONY_SERVICE); 
        return mTelephonyMgr.getLine1Number();
    }

    private String getMy10DigitPhoneNumber(){
        String s = getMyPhoneNumber();
        return s.substring(2);
    }
	
	@Override
	public void onClick(View v) {
		//check for scan button
		if(v.getId()==R.id.btnacceptticket){
			//instantiate ZXing integration class
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//start scanning
			scanIntegrator.initiateScan();
		}
		
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
			//---output to UI
			//formatTxt.setText("FORMAT: "+scanFormat);
			//contentTxt.setText("CONTENT: "+scanContent);
			//Toast.makeText(getApplicationContext(),String.valueOf(callnumBus(scanContent)),Toast.LENGTH_LONG).show();
			if(callnumBus(scanContent)==0){
				dialogShowNoValid();
			}
			else {
				dialogShowOK(scanContent);
			}
			
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	//show dialog
	public void dialogShowOK(String scanContent)
	{
		AlertDialog.Builder alert=new AlertDialog.Builder(TicketAction.this);
		alert.setTitle("Hệ thống vận tại công cộng TP.HCM");
		
		alert.setMessage("Chào mừng bạn đến với xe buýt số "+String.valueOf(callnumBus(scanContent)));
		alert.setCancelable(true);
		alert.setIcon(R.drawable.valid);
		alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				intent=new Intent(TicketAction.this, Menufunction.class);
				startActivity(intent);
			}
		});
		
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}
	
	//show dialog when id no valid
	
	public void dialogShowNoValid(){
		AlertDialog.Builder alert=new AlertDialog.Builder(TicketAction.this);
		alert.setTitle("Mã vạch không hợp lệ !!!");
		
		alert.setMessage("Bạn có muốn thử lại ? ");
		alert.setCancelable(true);
		alert.setIcon(R.drawable.warn);
		alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				accept.performClick();
			}
		});
		
		alert.setNegativeButton("Cancle",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}
	
	//call number bus from switchchoose 
	public int callnumBus(String scanContent){
		switchChoose=new SwitchChoose();
			
		return switchChoose.getnumberBus(scanContent);
	}
	
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onOptionsMenuClosed(menu);
	}

	

}
