package fitiuh.edu.vn.vnbus;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import fitiuh.edu.vn.database.*;

public class Menufunction extends Activity {
	
	ImageButton IMG_SHARE,IMG_BOOKMARKS,IMG_ROUTERLIST,IMG_SEARCHS,IMG_SETUP;
	Intent intent;
	BusDBAdapter myDb;
	
	/**
	 * Get data from services
	 */
	Bus infor=new Bus();
	Coordinate coordinate=new Coordinate();
	//InetAddress ipAddress;
	final String NAMESPACE="http://test_bus/";
	String METHOD_NAME;
	String SOAP_ACTION;
	final String URL="http://27.78.123.11:8080/BUS_PRO/Services?WSDL";
	SoapObject response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menufunction);
		
		IMG_SHARE=(ImageButton) findViewById(R.id.ImgShare);
		IMG_BOOKMARKS=(ImageButton) findViewById(R.id.imgBookmarks);
		IMG_ROUTERLIST=(ImageButton) findViewById(R.id.imgRouterlist);
		IMG_SEARCHS=(ImageButton) findViewById(R.id.ImgSearchs);
		IMG_SETUP=(ImageButton) findViewById(R.id.imgSetup);
		
		openDB();
		CheckDBBusInfor();
		
		IMG_SHARE.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,ShareFunction.class);
				startActivity(intent);
			}
		});
		
		IMG_BOOKMARKS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,BookMarksFunction.class);
				startActivity(intent);
				
			}
		});
		
		
		IMG_ROUTERLIST.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,RouterListsFunction.class);
				startActivity(intent);
			}
		});
		
		IMG_SEARCHS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent=new Intent(Menufunction.this,SearchsFunction.class);
				startActivity(intent);
			}
		});
		
		IMG_SETUP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}
	
	private void openDB() {
		myDb = new BusDBAdapter(this);
		myDb.open();
	}
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
	}
	
	//Start connect web service and pasing data to sqlite
	public class backMethodInfor extends AsyncTask<SoapObject, SoapObject, SoapObject >{
		

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
                                                                                                          
                 } catch (Exception e) {
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
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menufunction, menu);
		return true;
	}
	
	

}
