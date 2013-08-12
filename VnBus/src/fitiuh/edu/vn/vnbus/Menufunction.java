package fitiuh.edu.vn.vnbus;

import java.io.IOException;
import java.net.SocketTimeoutException;

import fitiuh.edu.vn.gps.*;
import fitiuh.edu.vn.barcode.*;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import fitiuh.edu.vn.database.*;
import fitiuh.edu.vn.gps.GPSTracker;

public class Menufunction extends Activity implements OnClickListener {
	
	Intent intent;
	BusDBAdapter myDb;
	Bundle bundle;
	Button ticketP,booLove,inforBus,seeRoad;
	
	Bus infor=new Bus();
	Coordinate coordinate=new Coordinate();
	
	final String NAMESPACE="http://test_bus/";
	String METHOD_NAME;
	String SOAP_ACTION;
	final String URL="http://192.168.0.108:8080/BUS_PRO/Services?WSDL";
	SoapObject response;
	
	GPSTracker gpsTracker;
	ShareLocation sharelocaion;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_dashboardmenufunction);
		
		ticketP=(Button) findViewById(R.id.dashboard_button_payticket);
		booLove=(Button) findViewById(R.id.dashboard_button_bookmark);
		inforBus=(Button) findViewById(R.id.dashboard_button_ListInfoBus);
		seeRoad=(Button) findViewById(R.id.dashboard_button_SeeandSharelocation);
				
		openDB();
		CheckDBBusInfor();
		
		
		
		//event for scan barcode for pay money when start bus
		ticketP.setOnClickListener(this);
		
		//event for see bookmark
		booLove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,BookMarksFunction.class);
				startActivity(intent);
				
				
			}
		});
		
		//see road from map and see ticket electronics
		seeRoad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,ShareFunction.class);
				startActivity(intent);
				
			}
		});
		
		//search information of bus 
		inforBus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,RouterListsFunction.class);
				startActivity(intent);
				
			}
		});
		
		/*		
		IMG_SETUP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				boolean check=isNetworkOnline();
				//String id=callIdChooseDismiss();
				
				//Toast.makeText(getApplicationContext(),String.valueOf(getPhoneIdSim()), Toast.LENGTH_LONG).show();
				if (check==false) {
					Toast.makeText(getApplicationContext(),"No connect internet", Toast.LENGTH_LONG).show();
				}
				if(check==true){
					Toast.makeText(getApplicationContext(),"Connect internet ", Toast.LENGTH_LONG).show();
				}
				
			
			}
		});*/
			
	}
	
	
	//Pay ticket when move with bus
	@Override
	public void onClick(View v) {
		
		turnGPSOn();
		
		boolean check=isNetworkOnline();
		
		if(check==true){
			//check for scan button
			if(v.getId()==R.id.dashboard_button_payticket){
			//instantiate ZXing integration class
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//start scanning
			scanIntegrator.initiateScan();
			}
		}
		else{
			dialogNoAcceptInternet();
		}
		
		
		
	}
	
	//check internet
	public boolean isNetworkOnline() {
	    boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        }else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                status= true;
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return false;
	    }
	    return status;

	    } 
	
	//set up auto gps 
	private void turnGPSOn() {

	    String provider = android.provider.Settings.Secure.getString(
	            getContentResolver(),
	            android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	    if (!provider.contains("gps")) { // if gps is disabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings",
	                "com.android.settings.widget.SettingsAppWidgetProvider");
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3"));
	        sendBroadcast(poke);
	    }
	}
	
	//show Scan result from Barcode application
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		String valid;
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
			
			valid=callnumBus(scanContent);
			
			//show dialog not validation
			if(valid==null){
				dialogShowNoValid();
			}
			
			//show dialog ok and share location first
			if(valid!=null) {
				
				//show dialog
				dialogShowOK(scanContent);
				
				//share location
				sharelocaion=new ShareLocation();
				sharelocaion.callShare(Integer.parseInt(valid), getPhoneIdSim(), getLatitude(), getLongitude(), getTimeShare());
				
				//save information bus from scan into database
				myDb.deleteAllStore();
				myDb.insertRowStore(Integer.parseInt(valid), getTimeShare());
			}
			
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}
	
	//open database in mobi
	private void openDB() {
		myDb = new BusDBAdapter(this);
		myDb.open();
	}
	
	//close database
	private void closeDB() {
		myDb.close();
	}
	
	/**
	 * Check Data base for update
	 */
	public void CheckDBBusInfor(){
		if(myDb.getAllRows().getCount()==0){
			new backMethodInfor().execute();
		}
		
		if(myDb.getAllOrdi().getCount()==0){
			new backMethodCoordinate().execute();
		}
		
		if(myDb.getAllBar().getCount()==0){
			new backMethodBarcodeList().execute();
		}
		
	}
	
	//show dialog validation with id barcode in sqlite
	public void dialogShowOK(String scanContent)
	{
		AlertDialog.Builder alert=new AlertDialog.Builder(Menufunction.this);
		alert.setTitle("Hệ thống vận tại công cộng TP.HCM");
			
		alert.setMessage("Chào mừng bạn đến với xe buýt số "+String.valueOf(callnumBus(scanContent)));
		alert.setCancelable(true);
		alert.setIcon(R.drawable.valid);
		alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				
		@Override
		public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
			
			AlertDialog alertDialog = alert.create();
			alertDialog.show();
	}
	
	//show dialog when barcode not valid
	public void dialogShowNoValid(){
		AlertDialog.Builder alert=new AlertDialog.Builder(Menufunction.this);
		alert.setTitle("Mã vạch không hợp lệ !!!");
		
		alert.setMessage("Bạn có muốn thử lại ? ");
		alert.setCancelable(true);
		alert.setIcon(R.drawable.warn);
		alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ticketP.performClick();
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
	
	public void dialogNoAcceptInternet(){
		AlertDialog.Builder alert=new AlertDialog.Builder(Menufunction.this);
		alert.setTitle("Bạn cần kết nối internet để thực hiện!!!");
		
		alert.setMessage("Bạn có muốn kết nối internet? ");
		alert.setCancelable(true);
		alert.setIcon(R.drawable.warn);
		alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
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

	//Return number bus from id barcode scan
	public String callnumBus(String scanContent){
		String idbus=null;
		Cursor c=myDb.getRowTicket_Numberbus(scanContent);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			idbus=c.getString(0);
		}
		
		return idbus;
	}
	
	//Start connect web service and pasing data to sqlite
	//updata information bus: name,money.....
	public class backMethodInfor extends AsyncTask<SoapObject, SoapObject, SoapObject >{
		
		private Exception exception;
		private String ErrorMsg="";
		private final ProgressDialog dialog=new ProgressDialog(Menufunction.this);
		
		@Override
		protected SoapObject doInBackground(SoapObject... params) {
			
			METHOD_NAME="Update_Information";
			SOAP_ACTION="http://test_bus/Update_Information";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE,"Bus",new Bus().getClass());
            
            try {

                 AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
                 
                 androidHttpTransport.debug=true;

                 androidHttpTransport.call(SOAP_ACTION, envelope);
                                          
                 response = (SoapObject)envelope.bodyIn;
                 
            	}catch (Exception e) {
                    e.printStackTrace();
                }
				
			return response;
		}
		
		@Override
		protected void onPreExecute() {
			
			this.dialog.setMessage("Đang cập nhật dữ liệu cho bus.....");              
            this.dialog.show();
		}
		
		@Override
		protected void onPostExecute(SoapObject result) {
			
			if(getRespone(result)!=null){
				
				Bus bus[]=getRespone(result);
				
				for(int i=0;i<bus.length;i++){
					
					String culi=bus[i].STRETCH_ROAD;
					String donvidamnhan=bus[i].ADMIN_ACTIVITY;
					String giave=String.valueOf(bus[i].TICKET_FACE);
					String giancach=bus[i].SPACE_TRIP;
					String loaihinhhoatdong=bus[i].FORM_ACTIVITY;
					String loaixe=bus[i].FORM_BUS;
					String luotdi=bus[i].OUTWARD_JOURNEY;
					String luotve=bus[i].RETURN_JOURNEY;
					String matuyen=String.valueOf(bus[i].NUMBER_BUS);
					String sochuyen=bus[i].COUNT_TRIP;
					String tentuyen=bus[i].NAME_BUS;
					String thoigianchuyen=bus[i].TIME_TRIP;
					String thoigianhoatdong=bus[i].START_END;
					
					myDb.insertRow(Integer.parseInt(matuyen),tentuyen,luotdi,luotve,loaihinhhoatdong,culi,sochuyen,thoigianchuyen,giancach,thoigianhoatdong,Integer.parseInt(giave),donvidamnhan,loaixe);
					
					
				}
				

			 	} else {
			 		
                 Toast.makeText(getApplicationContext(),"Result Found is ==  "+ result + "", Toast.LENGTH_LONG).show();
                 
			 	}
			
			 super.onPostExecute(result);
			 
			 if (this.dialog.isShowing()) {

                 this.dialog.dismiss();
			 }
			
		}
		
	}
	
	//Connect Coordinate data in webservice and pasing sqlite data in mobile
	public class backMethodCoordinate extends AsyncTask<SoapObject, SoapObject, SoapObject > {

		private final ProgressDialog dialog=new ProgressDialog(Menufunction.this);
		
		@Override
		protected SoapObject doInBackground(SoapObject... params) {

			METHOD_NAME="Update_Location";
			SOAP_ACTION="http://test_bus/Update_Location";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE,"Coordinate",new Coordinate().getClass());
            
            try {

                 AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
                 
                 androidHttpTransport.debug=true;

                 androidHttpTransport.call(SOAP_ACTION, envelope);
                                          
                 response = (SoapObject)envelope.bodyIn;
                                                                                                          
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
				
			return response;
		}

		@Override
		protected void onPreExecute() {

			this.dialog.setMessage("Đang cập nhật dữ liệu cho tọa độ trạm.....");              
            this.dialog.show();
		}

		@Override
		protected void onPostExecute(SoapObject result) {
			
			if(getResponeCoordinate(result)!=null){
				
				Coordinate coordi[]=getResponeCoordinate(result);
				for(int i=0;i<coordi.length;i++){
					String address=coordi[i].ADDRESS;
					String longitude=String.valueOf(coordi[i].LONGITUDE);
					String direction=coordi[i].DIRECTION;
					String numberbus=String.valueOf(coordi[i].NUMBER_BUS);
					String latitude=String.valueOf(coordi[i].LATITUDE);
					
					myDb.insertRowOrdi(Integer.parseInt(numberbus), direction,Double.parseDouble(latitude),Double.parseDouble(longitude), address);
				}
				
			}
			
			else{
				 Toast.makeText(getApplicationContext(),"Result Found is ==  "+ result + "", Toast.LENGTH_LONG).show();
			}
			
			super.onPostExecute(result);
			
			if (this.dialog.isShowing()) {

                this.dialog.dismiss();
			 }
		}
		
		
		
	}
	
	//Connect barcodelist  data in webservice and pasing sqlite data in mobile
		public class backMethodBarcodeList extends AsyncTask<SoapObject, SoapObject, SoapObject > {

			private final ProgressDialog dialog=new ProgressDialog(Menufunction.this);
			
			@Override
			protected SoapObject doInBackground(SoapObject... params) {

				METHOD_NAME="CallListBarcode";
				SOAP_ACTION="http://test_bus/CallListBarcode";
				
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

	            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            envelope.setOutputSoapObject(request);
	            envelope.addMapping(NAMESPACE,"BarcodeList",new BarcodeList().getClass());
	            
	            try {

	                 AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
	                 
	                 androidHttpTransport.debug=true;

	                 androidHttpTransport.call(SOAP_ACTION, envelope);
	                                          
	                 response = (SoapObject)envelope.bodyIn;
	                                                                                                          
	                 } catch (Exception e) {
	                     e.printStackTrace();
	                 }
					
				return response;
			}

			@Override
			protected void onPreExecute() {

				this.dialog.setMessage("Đang cập nhật dữ liệu cho mã vạch.....");              
	            this.dialog.show();
			}

			@Override
			protected void onPostExecute(SoapObject result) {
				
				if(getResponeBarcodeList(result)!=null){
					
					BarcodeList barcode[]=getResponeBarcodeList(result);
					for(int i=0;i<barcode.length;i++){
						String idbarcode=barcode[i].idBarcode;
						String idimage=barcode[i].idImage;
						String idbus=String.valueOf(barcode[i].numberBus);
						
						myDb.insertListBarcode(Integer.parseInt(idbus), idbarcode, idimage);
					}
					
				}
				
				else{
					 Toast.makeText(getApplicationContext(),"Result Found is ==  "+ result + "", Toast.LENGTH_LONG).show();
				}
				
				super.onPostExecute(result);
				
				if (this.dialog.isShowing()) {

	                this.dialog.dismiss();
				 }
			}
				
		}

	//insert data bus in to array
	public static Bus[] getRespone(SoapObject soap){
		
		Bus[] busRes=new Bus[soap.getPropertyCount()];
		
		for(int i=0;i<busRes.length;i++){
			SoapObject pii=(SoapObject) soap.getProperty(i);
			
			Bus inforbus=new Bus();
			
			inforbus.STRETCH_ROAD=pii.getProperty(0).toString();
			inforbus.ADMIN_ACTIVITY=pii.getProperty(1).toString();
			inforbus.TICKET_FACE=Integer.parseInt(pii.getProperty(2).toString());
			inforbus.SPACE_TRIP=pii.getProperty(3).toString();
			inforbus.FORM_ACTIVITY=pii.getProperty(4).toString();
			inforbus.FORM_BUS=pii.getProperty(5).toString();
			inforbus.OUTWARD_JOURNEY=pii.getProperty(6).toString();
			inforbus.RETURN_JOURNEY=pii.getProperty(7).toString();
			inforbus.NUMBER_BUS=Integer.parseInt(pii.getProperty(8).toString());
			inforbus.COUNT_TRIP=pii.getProperty(9).toString();
			inforbus.NAME_BUS=pii.getProperty(10).toString();
			inforbus.TIME_TRIP=pii.getProperty(11).toString();
			inforbus.START_END=pii.getProperty(12).toString();
			
			busRes[i]=inforbus;
		}
		
		return busRes;
	}
	
	//insert coordinate into array from web services
	public static Coordinate[] getResponeCoordinate(SoapObject soap){
		
		Coordinate []cooR=new Coordinate[soap.getPropertyCount()];
		
		for(int i=0;i<cooR.length;i++){
			SoapObject coo=(SoapObject) soap.getProperty(i);
			
			Coordinate coordinate=new Coordinate();
			
			coordinate.ADDRESS=coo.getProperty(0).toString();
			coordinate.LONGITUDE=Double.parseDouble(coo.getProperty(1).toString());
			coordinate.DIRECTION=coo.getProperty(2).toString();
			coordinate.NUMBER_BUS=Integer.parseInt(coo.getProperty(3).toString());
			coordinate.LATITUDE=Double.parseDouble(coo.getProperty(4).toString());
			
			cooR[i]=coordinate;
		}
		
		return cooR;
	}
	
	//insert barcode id into array from web services
	public static BarcodeList[] getResponeBarcodeList(SoapObject soap){
		
		BarcodeList[]cooR=new BarcodeList[soap.getPropertyCount()];
		
		for(int i=0;i<cooR.length;i++){
			SoapObject coo=(SoapObject) soap.getProperty(i);
			
			BarcodeList bar=new BarcodeList();
			
			bar.idBarcode=coo.getProperty(0).toString();
			bar.idImage=coo.getProperty(1).toString();
			bar.numberBus=Integer.parseInt(coo.getProperty(2).toString());
			
			cooR[i]=bar;
		}
		
		return cooR;
	}
	
	public String getTimeShare(){
		
		Time today=new Time(Time.getCurrentTimezone());
		today.setToNow();
		
		return today.format("%k:%M:%S");
	}
	
	public double getLatitude(){
		
		gpsTracker=new GPSTracker(Menufunction.this);
		
		return gpsTracker.getLatitude();
	}
	
	public double getLongitude(){
		
		gpsTracker=new GPSTracker(Menufunction.this);
		
		return gpsTracker.getLongitude();
	}
	
	
	
	public double getPhoneIdSim(){
		
		TelephonyManager tm=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		return Double.parseDouble(tm.getSimSerialNumber());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menufunction, menu);
		return true;
	}
	

}
