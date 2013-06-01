package fitiuh.edu.vn.vnbus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Menufunction extends Activity {
	
	ImageButton IMG_SHARE,IMG_BOOKMARKS,IMG_ROUTERLIST,IMG_SEARCHS,IMG_SETUP;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menufunction);
		
		IMG_SHARE=(ImageButton) findViewById(R.id.ImgShare);
		IMG_BOOKMARKS=(ImageButton) findViewById(R.id.imgBookmarks);
		IMG_ROUTERLIST=(ImageButton) findViewById(R.id.imgRouterlist);
		IMG_SEARCHS=(ImageButton) findViewById(R.id.ImgSearchs);
		IMG_SETUP=(ImageButton) findViewById(R.id.imgSetup);
		
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menufunction, menu);
		return true;
	}
	
	

}
