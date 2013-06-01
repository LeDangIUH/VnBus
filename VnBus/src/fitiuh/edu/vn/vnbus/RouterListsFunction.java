package fitiuh.edu.vn.vnbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RouterListsFunction extends Activity {
	
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routerlistsfunition);
		
		//Back MenuFunction
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
