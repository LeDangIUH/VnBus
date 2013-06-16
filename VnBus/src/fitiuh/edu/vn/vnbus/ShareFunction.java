package fitiuh.edu.vn.vnbus;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import fitiuh.edu.vn.database.*;

public class ShareFunction extends Activity {
	
	private final LatLng LOCATION_SURRREY = new LatLng(10.820908200869496,106.68407135789789);
	private GoogleMap map;
	Intent intent;
	Bundle bundle;
	BusDBAdapter myDb;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharefunction);
		
		openDB();
		
		getActionBar().setTitle("Hành Trình Bus");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));
		
		
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
	
	public void ShowNumBusFilter(ListView ls){
		
		Cursor cursor=myDb.getAllRows();
		startManagingCursor(cursor);
		String[] fromFileName=new String[]{BusDBAdapter.KEY_BUSNUMBER};
		int[]toViewId=new int[]{R.id.txtFilterNumBus};
		SimpleCursorAdapter mycursoradapter=new SimpleCursorAdapter
				(this,R.layout.activity_sharefilteronerow, cursor, fromFileName, toViewId);
		ls.setAdapter(mycursoradapter);
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
			
		}
		return super.onOptionsItemSelected(item);
	}
	
}
