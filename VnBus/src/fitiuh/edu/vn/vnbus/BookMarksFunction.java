package fitiuh.edu.vn.vnbus;

import fitiuh.edu.vn.database.BusDBAdapter;
import fitiuh.edu.vn.vnbus.R.menu;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BookMarksFunction extends Activity {
	
	Intent intent;
	BusDBAdapter myDb;
	TextView myText=null;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookmarksfunction);
		getActionBar().setTitle("Bookmarks");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		openDB();
		StatusBookmarks();
		lv=(ListView) findViewById(R.id.lvshowbookmarks);
		populateListViewFromDB();
	}
	
	//ckeck data and show 
	private void StatusBookmarks(){
		if(myDb.getAllRowsFavour().getCount()==0){
			
			RelativeLayout relative=(RelativeLayout) findViewById(R.id.mylayoutbookmark);
			myText=new TextView(this);
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
