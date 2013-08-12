package fitiuh.edu.vn.vnbus;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fitiuh.edu.vn.database.BusDBAdapter;
import fitiuh.edu.vn.vnbus.R.menu;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookMarksFunction extends Activity {
	
	Intent intent;
	BusDBAdapter myDb;
	TextView myText=null;
	ListView lv;
	
	ImageButton imgGo,imgReturn;
	private final LatLng LOCATION_SURRREY = new LatLng(10.820908200869496,106.68407135789789);
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookmarksfunction);
		
		getActionBar().setTitle("Bookmarks");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		openDB();
		StatusBookmarks();
		
		lv=(ListView) findViewById(R.id.lvshowbookmarks);
		
		//show listview
		populateListViewFromDB();
		
		//setting listview click
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Toast.makeText(getApplicationContext(), "Alo", Toast.LENGTH_LONG).show();
				
				final int numberbus=SelectForNum((int)id);
				
				setContentView(R.layout.activity_sharefunction);
				
				imgGo=(ImageButton) findViewById(R.id.imgGo);
				imgReturn=(ImageButton) findViewById(R.id.imgReturn);
				
				map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
				/*map.addMarker(new MarkerOptions().position(LOCATION_SURRREY)
						.title("Find me here!")
						.snippet("coi chung")
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));*/
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));
				
				//show market on map
				showMarker(numberbus,"di");
				
				/**
				 * image setting
				 */
				imgGo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//Toast.makeText(getApplicationContext(), "Go", Toast.LENGTH_LONG).show();
						map.clear();
						showMarker(numberbus,"di");
					}
				});
				
				imgReturn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//Toast.makeText(getApplicationContext(), "Return", Toast.LENGTH_LONG).show();
						map.clear();
						showMarker(numberbus,"ve");
					}
				});
				
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final int numberbus=SelectForNum((int)id);				
				myDb.deleteRowFavourite(numberbus);
				populateListViewFromDB();
				
				return false;
			}
		});
	}
	
	//ckeck data and show 
	private void StatusBookmarks(){
		if(myDb.getAllRowsFavour().getCount()==0){
			
			RelativeLayout relative=(RelativeLayout) findViewById(R.id.mylayoutbookmark);
			myText=new TextView(this);
			myText.setTextColor(R.style.txtinLsViewSociaty);
			myText.setText("Hiện tại bạn chưa thêm Bookmarks vào.");
			
			relative.addView(myText);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}
	
	
	private void openDB(){
		myDb = new BusDBAdapter(this);
		myDb.open();
	}
	
	private void closeDB(){
		myDb.close();
	}
	
	private void drawMarker(LatLng point){
    	// Creating an instance of MarkerOptions
    	MarkerOptions markerOptions = new MarkerOptions();					
    		
    	// Setting latitude and longitude for the marker
    	markerOptions.position(point);
    	markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1));
    	// Adding marker on the Google Map
    	map.addMarker(markerOptions);    		
    }
	
	public void showMarker(int number,String type){
		
		//String num=edt1.getText().toString();
		
		Cursor c =myDb.getAllRowsOrdi(number,type);
		
		double lat=0;
		double Long=0;
		String Add=null;
		
		c.moveToFirst();
		
		for(int i=0;i<c.getCount();i++){
			
			lat=c.getDouble(c.getColumnIndex(BusDBAdapter.KEY_LAT));
			Long=c.getDouble(c.getColumnIndex(BusDBAdapter.KEY_LNG));
			Add=c.getString(c.getColumnIndex(BusDBAdapter.KEY_ADD));
			
			LatLng location =new LatLng(lat,Long);
			drawMarker(location);
			
			//Log.d(String.valueOf(lat), "kk");
			
			c.moveToNext();
		}
	}
	
	//select number bus
	public int SelectForNum(int idInDB) {
				
		int numberbus=0;
		Cursor cursor = myDb.getRow(idInDB);
		if (cursor.moveToFirst()) {
				numberbus= cursor.getInt(BusDBAdapter.COL_BUSNUMBER);
			}
		cursor.close();
		return numberbus;
				
	}
	
	public void populateListViewFromDB() {
		
		Cursor cursor = myDb.getAllRowsFavour();
			
		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);
			
		// Setup mapping from cursor to view fields:
		String[] fromFieldNames = new String[]
					{BusDBAdapter.KEY_BUSNUMFavour,  BusDBAdapter.KEY_BUSNAMEFavour};
		int[] toViewIDs = new int[]
					{R.id.txtTicket,          R.id.txtNameOneRouter};
			
		// Create adapter to may columns of the DB onto elemesnt in the UI.
		SimpleCursorAdapter myCursorAdapter = 
				new SimpleCursorAdapter(
							this,		// Context
							R.layout.activity_onerouter,	// Row layout template
							cursor,					// cursor (set of DB records to map)
							fromFieldNames,			// DB Column names
							toViewIDs				// View IDs to put information in
							);
			
			// Set the adapter for the list view
			lv.setAdapter(myCursorAdapter);
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			intent=new Intent(this,Menufunction.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
