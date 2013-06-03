package fitiuh.edu.vn.vnbus;

import fitiuh.edu.vn.database.BusDBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class RouterClearInfor extends Activity {
	
	BusDBAdapter myDb;
	Intent intent;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routerclear);
		
		openDB();
		
		//chaning title
		setTitle(String.valueOf(callnumberbus())+" "+callnamebus());
		//remove icon
		getActionBar().setDisplayShowHomeEnabled(false);
		//call back button
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public int callnumberbus() {
		
		intent=new Intent();
		intent=getIntent();
		bundle=intent.getBundleExtra("Tobus");
	
		return bundle.getInt("Numberbus"); 
	}
	public String callnamebus() {
		
		intent=new Intent();
		intent=getIntent();
		bundle=intent.getBundleExtra("Tobus");
	
		return bundle.getString("Namebus"); 
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case android.R.id.home:
			intent=new Intent(RouterClearInfor.this,RouterListsFunction.class);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
	}

}
