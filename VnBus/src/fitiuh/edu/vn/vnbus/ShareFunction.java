package fitiuh.edu.vn.vnbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import fitiuh.edu.vn.database.*;

public class ShareFunction extends Activity {
	
	private final LatLng LOCATION_SURRREY = new LatLng(10.820908200869496,106.68407135789789);
	private GoogleMap map;
	Intent intent;
	Bundle bundle;
	BusDBAdapter myDb;
	AlertDialog.Builder alertdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_sharefunction);
		
		openDB();
		
		getActionBar().setTitle("Hành Trình Bus");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setDisplayShowHomeEnabled(false);
		
		//map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));
		
		//DialogWhatShare();
		//showMarker("di");
		
		//add tab in layout
		ActionBar actionbar=getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tabGps=actionbar.newTab();
		tabGps.setText("Chia Sẻ");
		tabGps.setTabListener(new TabListener<ShareFunctionTabGPS>(this, "GPS",ShareFunctionTabGPS.class));
		actionbar.addTab(tabGps);
		
		Tab tabSociety=actionbar.newTab();
		tabSociety.setText("Lộ Trình");
		//tabSociety.setIcon(R.drawable.social_share);
		tabSociety.setTabListener(new TabListener<ShareFunctionTabSocialNetwork>(this, "Society",ShareFunctionTabSocialNetwork.class));
		actionbar.addTab(tabSociety);
		
	}
	
	public static class TabListener<T extends Fragment>implements ActionBar.TabListener{

		private final Activity myActivity;
		private final String myTag;
		private final Class<T> myClass;

	public TabListener(Activity activity, String tag, Class<T> cls) {
		myActivity = activity;
		myTag = tag;
		myClass = cls;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		Fragment myFragment = myActivity.getFragmentManager().findFragmentByTag(myTag);
	
		// Check if the fragment is already initialized
		if (myFragment == null) {
			// If not, instantiate and add it to the activity
			myFragment = Fragment.instantiate(myActivity, myClass.getName());
			ft.add(android.R.id.content, myFragment, myTag);
		} else {
			// If it exists, simply attach it in order to show it
			ft.attach(myFragment);
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	
		Fragment myFragment = myActivity.getFragmentManager().findFragmentByTag(myTag);
	
		if (myFragment != null) {
			// Detach the fragment, because another one is being attached
			ft.detach(myFragment);
		}
	
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
		}

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
	
	private void drawMarker(LatLng point){
    	// Creating an instance of MarkerOptions
    	MarkerOptions markerOptions = new MarkerOptions();					
    		
    	// Setting latitude and longitude for the marker
    	markerOptions.position(point);
    		
    	// Adding marker on the Google Map
    	map.addMarker(markerOptions);    		
    }
	
	public void showMarker(String type){
		
		//String num=edt1.getText().toString();
		
		Cursor c =myDb.getAllRowsOrdi(1,type);
		
		double lat=0;
		double Long=0;
		String Add=null;
		
		c.moveToFirst();
		
		for(int i=0;i<c.getCount();i++){
			
			lat=c.getDouble(c.getColumnIndex(BusDBAdapter.KEY_LAT));
			Long=c.getDouble(c.getColumnIndex(BusDBAdapter.KEY_LNG));
			Add=c.getString(c.getColumnIndex(BusDBAdapter.KEY_ADD));
			
			LatLng location =new LatLng(Long, lat);
			drawMarker(location);
			
			//Log.d(String.valueOf(lat), "kk");
			
			c.moveToNext();
		}
	}
	
	
	public void ShowNumBusFilter(ListView ls){
		
		Cursor cursor=myDb.getAllRows();
		startManagingCursor(cursor);
		String[] fromFileName=new String[]{BusDBAdapter.KEY_BUSNUMBER};
		int[]toViewId=new int[]{R.id.txtFilterNumBus};
		SimpleCursorAdapter mycursoradapter=new SimpleCursorAdapter
				(this,R.layout.activity_sharefilteronerow, cursor, fromFileName, toViewId);
		ls.setAdapter(mycursoradapter);
	}
	
	//show Dialog share
	public void DialogShareCoordinate(){
		
		final Dialog dialog=new Dialog(ShareFunction.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_whatsharedialog);
		
		Spinner spinner=(Spinner) dialog.findViewById(R.id.spinnerWhatShare);
		showSpinner(spinner);
		
		Button accept=(Button) dialog.findViewById(R.id.btnWhatShare);
		Button cancle=(Button) dialog.findViewById(R.id.btnWhatShareCancle);
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
			}
		});
		
		cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		
		dialog.show();
	}
	
	//add item to spinner from sqlite
	public void showSpinner(Spinner spinner){
		Cursor speciesCursor = myDb.getNumberBusAllRow();
		startManagingCursor(speciesCursor);
		
		String[] from = new String[] {BusDBAdapter.KEY_BUSNUMBER };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter speciesSpinnerAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, speciesCursor, from, to);
		speciesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(speciesSpinnerAdapter);
	}
	
	//show dialog what use have share infor your bus 
	public void DialogWhatShare(){
		alertdialog=new AlertDialog.Builder(ShareFunction.this);
		alertdialog.setMessage("Bạn chỉ sử dụng chức năng này khi bạn chia sẽ thông tin tuyến xe bus của bạn."
								+" Bạn có muốn chia sẻ thông tin tuyến của bạn ?");
		alertdialog.setCancelable(true);
		
		alertdialog.setNegativeButton("Không",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		alertdialog.setPositiveButton("Chấp nhận",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				DialogShareCoordinate();
			}
		});
		
		
		AlertDialog alert=alertdialog.create();
		alert.show();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sharefuction, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			intent=new Intent(ShareFunction.this,Menufunction.class);
			startActivity(intent);
			break;
		case R.id.IconFilter:{
			final Dialog dialog=new Dialog(ShareFunction.this);
			dialog.setContentView(R.layout.activity_sharefilter);
			dialog.setTitle("Hiển thị tuyến");
			
			//show listview filter numbus
			ListView ls=(ListView) dialog.findViewById(R.id.lsvFilter);
			ShowNumBusFilter(ls);
			
			
			dialog.show();
		}
		break;
		//case R.id.IconRefresh:
			//break;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
