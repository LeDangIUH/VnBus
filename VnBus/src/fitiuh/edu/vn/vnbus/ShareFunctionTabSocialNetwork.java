package fitiuh.edu.vn.vnbus;


import java.util.ArrayList;
import java.util.HashMap;

import fitiuh.edu.vn.barcode.SwitchChoose;
import fitiuh.edu.vn.database.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.R.integer;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.HapticFeedbackConstants;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ShareFunctionTabSocialNetwork extends Fragment {
	
	SwitchChoose switchChoose=new SwitchChoose();
	
	private final LatLng LOCATION_SURRREY = new LatLng(10.820908200869496,106.68407135789789);
	private static View myFragment;
	
	ShareHorizontaListView ls;
	GoogleMap map;
	
	//connect server call all share
	final String NAMESPACE="http://test_bus/";
	String METHOD_NAME;
	String SOAP_ACTION;
	final String URL="http://192.168.0.109:8080/BUS_PRO/Services?WSDL";
	SoapObject response;
	
	BusDBAdapter myDb;
	
	ArrayList<HashMap<String,String>> myList=new ArrayList<HashMap<String,String>>();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 if (myFragment != null) {
		        ViewGroup parent = (ViewGroup) myFragment.getParent();
		        if (parent != null)
		            parent.removeView(myFragment);
		    }
		    try {
		    	myFragment = inflater.inflate(R.layout.activity_sharefunctionsocietynetwork, container, false);
		    	
		    	openDB();
		    	//list view
		    	ls=(ShareHorizontaListView) myFragment.findViewById(R.id.lssocialnetwork);
				//ls.setAdapter(new HAdapter());
				
				//map
				map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();		
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));
				
				//run update share
				StartUpdate();
				
				
				//call event for listview list
				ls.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Toast.makeText(getActivity(), "alo", Toast.LENGTH_LONG).show();
						
					}
				});
				
		    } catch (InflateException e) {
		  
				map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();		
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));

		    }
		    return myFragment;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeDB();
	}
	
	//open database in mobi
	private void openDB() {
		myDb = new BusDBAdapter(getActivity().getApplicationContext());
		myDb.open();
	}
		
	//close database
	private void closeDB() {
		myDb.close();
	}
	
	public void StartUpdate(){
		new backMethodCallShare().execute();
	}
	
	
	//Connect Coordinate data in webservice and pasing sqlite data in mobile
	public class backMethodCallShare extends AsyncTask<SoapObject, SoapObject, SoapObject > {

		private final ProgressDialog dialog=new ProgressDialog(ShareFunctionTabSocialNetwork.this.getActivity());

		@Override
		protected SoapObject doInBackground(SoapObject... params) {
			
			METHOD_NAME="CallAllShare";
			SOAP_ACTION="http://test_bus/CallAllShare";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE,"ShareOption",new ShareOption().getClass());
            
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
			this.dialog.setMessage("Đang cập nhật dữ liệu.....");              
            this.dialog.show();
		}
		
		
		@Override
		protected void onPostExecute(SoapObject result) {
			
			
			
			if(getResponeShare(result)!=null){
				
				ShareOption share[]=getResponeShare(result);
				for(int i=0;i<share.length;i++){
					String longitude=String.valueOf(share[i].LONGITUDE);
					String numbus=convertStringImage(String.valueOf(share[i].IDNUMBERBUS));
					String PhoneId=String.valueOf((share[i].PHONEIDSIM));
					String timeShare=share[i].TIMESHARE;
					String latitude=String.valueOf(share[i].LATITUDE);
					
					
					HashMap<String, String> map=new HashMap<String, String>();
					
					
					
					map.put("numbus",numbus);
					map.put("PhoneId", PhoneId);
					map.put("longitude",longitude);
					map.put("latitude",latitude);
					map.put("timeShare","Thời gian: "+timeShare);
					
					/**
					 * add into myList
					 */
					myList.add(map);
					
					
					
					//myDb.insertRowShare(Integer.parseInt(numbus),Double.valueOf(PhoneId),Double.valueOf(latitude), Double.valueOf(longitude),timeShare);
					
					/**
					 * call show listview
					 */
					//populateListViewFromDB();
					
					//call show map
					
					//drop table
					//myDb.deleteAllTABLEShare();
				}
				
				SimpleAdapter appAdapter=new SimpleAdapter(getActivity().getApplicationContext(),myList,R.layout.activity_sociatynetworkitem,
						new String[]{"numbus","timeShare"},new int[]{R.id.imgnumbussociety,R.id.timesociety});
				
				ls.setAdapter(appAdapter);
				
			}
			
			else{
				Toast.makeText(getActivity().getApplicationContext(),"Result Found is ==  "+ result + "", Toast.LENGTH_LONG).show();
			}
			
			super.onPostExecute(result);
			
			if (this.dialog.isShowing()) {

                this.dialog.dismiss();
			 }

			
		}
			
	}
	
	
	//insert coordinate into array from web services
	public static ShareOption[] getResponeShare(SoapObject soap){
			
		ShareOption []cooR=new ShareOption[soap.getPropertyCount()];
			
		for(int i=0;i<cooR.length;i++){
			SoapObject coo=(SoapObject) soap.getProperty(i);
				
			ShareOption shareoption=new ShareOption();
				
			shareoption.LONGITUDE=Double.parseDouble(coo.getProperty(0).toString());
			shareoption.IDNUMBERBUS=Integer.parseInt(coo.getProperty(1).toString());
			shareoption.PHONEIDSIM=Double.parseDouble(coo.getProperty(2).toString());
			shareoption.TIMESHARE=coo.getProperty(3).toString();
			shareoption.LATITUDE=Double.parseDouble(coo.getProperty(4).toString());
				
			cooR[i]=shareoption;
		}
			
		return cooR;
	}
	
	private String convertStringImage(String numbus){
		
		int numBus=Integer.parseInt(numbus);
		
		return Integer.toString(switchChoose.imgBusFormData(numBus));
	}
	
	private void drawMarker(LatLng point){
    	// Creating an instance of MarkerOptions
    	MarkerOptions markerOptions = new MarkerOptions();					
    		
    	// Setting latitude and longitude for the marker
    	markerOptions.position(point);
    		
    	// Adding marker on the Google Map
    	map.addMarker(markerOptions);    		
    }
	
	public void showMarker(){
		
	}
	
	
	/*public void populateListViewFromDB() {
		
		Cursor cursor = myDb.getAllTABLEShare();
		
		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		getActivity().startManagingCursor(cursor);
			
		// Setup mapping from cursor to view fields:
		String[] fromFieldNames = new String[]
				{BusDBAdapter.KEY_BUSNUMSHARE,  BusDBAdapter.KEY_TIMESHARE};
		int[] toViewIDs = new int[]
				{R.id.timesociety,          R.id.speedsociety};
			
		// Create adapter to may columns of the DB onto elemesnt in the UI.
		SimpleCursorAdapter myCursorAdapter = 
				new SimpleCursorAdapter(
						getActivity().getApplicationContext(),		// Context
						R.layout.activity_sociatynetworkitem,	// Row layout template
						cursor,					// cursor (set of DB records to map)
						fromFieldNames,			// DB Column names
						toViewIDs				// View IDs to put information in
						);
			
		// Set the adapter for the list view
		ls.setAdapter(myCursorAdapter);
		
	}*/
	
	
}
