package fitiuh.edu.vn.vnbus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import fitiuh.edu.vn.database.*;

public class RouterListsFunction extends Activity {
	
	Intent intent;
	BusDBAdapter myDb;
	ListView lv;
	TextView txt;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routerlistsfunition);
		
		openDB();
		//Back MenuFunction
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		lv=(ListView) findViewById(R.id.lvShowRouter);
		populateListViewFromDB();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				
				final Dialog dialog=new Dialog(RouterListsFunction.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.activity_dialogrouter);
				
				
				ImageButton dialogclearinfor=(ImageButton) dialog.findViewById(R.id.imgClearInfor);
				ImageButton dialogmaprouter=(ImageButton) dialog.findViewById(R.id.Imgmaprouter);
				ImageButton dialogbookmark=(ImageButton) dialog.findViewById(R.id.imgaddfavou);
				
				final int numberbus=SelectForNum((int)arg3);
				final String namebus=SelectForName((int)arg3);
				
				dialogclearinfor.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						bundle=new Bundle();
						bundle.putInt("Numberbus", numberbus);
						bundle.putString("Namebus", namebus);
						intent=new Intent(RouterListsFunction.this,RouterClearInfor.class);
						intent.putExtra("Tobus", bundle);
						startActivity(intent);
					}
				});
				
				dialogmaprouter.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						
					}
				});
				
				dialogbookmark.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						myDb.insertRowFavour(numberbus, namebus);
						dialog.dismiss();
					}
				});
				
				dialog.show();
				
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
	
	//show listview
	public void populateListViewFromDB() {
			
		Cursor cursor = myDb.getAllRows();
			
		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);
			
		// Setup mapping from cursor to view fields:
		String[] fromFieldNames = new String[]
				{BusDBAdapter.KEY_BUSNUMBER,  BusDBAdapter.KEY_BUSNAME};
		int[] toViewIDs = new int[]
				{R.id.txtNumOneRouter,          R.id.txtNameOneRouter};
			
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
	//select name bus
	public String SelectForName(int idInDB) {
			
		String namebus=null;
		Cursor cursor = myDb.getRow(idInDB);
		if (cursor.moveToFirst()) {
			namebus = cursor.getString(BusDBAdapter.COL_BUSNAME);
			}
		cursor.close();
		return namebus ;	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.routerlistsfunction, menu);
		return super.onCreateOptionsMenu(menu);
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
