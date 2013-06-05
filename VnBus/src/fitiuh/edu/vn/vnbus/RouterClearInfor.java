package fitiuh.edu.vn.vnbus;

import java.util.ArrayList;
import java.util.List;

import fitiuh.edu.vn.database.Bus;
import fitiuh.edu.vn.database.BusDBAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.MailTo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RouterClearInfor extends Activity{
	
	BusDBAdapter myDb;
	Intent intent;
	Bundle bundle;
	TextView txt;
	
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
		
		txt=(TextView) findViewById(R.id.txtshowInforbus);
		txt.setText(ShowDataOfClearBus(callnumberbus()));
	
		
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
	
	private String ShowDataOfClearBus(int numberbus){
		
		String myShow="";
		Cursor c=myDb.getRow(numberbus);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			myShow="Mã tuyến: "+c.getString(0)+"\n"+"Tên tuyến: "+c.getString(1)+"\n"+"Loại hình hoạt động: "+c.getString(4)+"\n"+"Giá vé: "+c.getString(10)
					+"\n"+"Cự ly: "+c.getString(5)+"\n"+"Số chuyến: "+c.getString(6)+"\n"+"Thời gian chuyến: "+c.getString(7)+"\n"
					+"Giãn cách: "+c.getString(8)+"\n"+"Thời gian hoạt động: "+c.getString(9)+"\n"+" Loại xe: "+c.getString(12)
					+"\n"+"Đơn vị đảm nhận: "+c.getString(11);
		}
		//for(int i=0;i<)
		return myShow;
	}
	
	private String RouterOutwardReturn(int numberbus,int id){
		
		String myShow="";
		Cursor c=myDb.getRow(numberbus);
		
		if(id==0){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
				myShow="Lượt đi: "+"\n"+c.getString(2);
			}
		}
		if(id==1){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
				myShow="Lượt về: "+"\n"+c.getString(3);
			}
		}
		
		return myShow;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case android.R.id.home:
			intent=new Intent(RouterClearInfor.this,RouterListsFunction.class);
			startActivity(intent);
			break;
		case R.id.chooseoutwardreturn:{
			final Dialog dialog=new Dialog(RouterClearInfor.this);
			dialog.setContentView(R.layout.activity_outwardreturnrouter);
			dialog.setTitle("Lộ trình tuyến "+callnumberbus());
			
			final TextView txtshow=(TextView) dialog.findViewById(R.id.txtoutwardreturn);
			Spinner spinner=(Spinner) dialog.findViewById(R.id.spinnerchoose);
			ShowSpinner(spinner);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					if(parent.getSelectedItemPosition()==0)
						txtshow.setText(RouterOutwardReturn(callnumberbus(), 0));
					if(parent.getSelectedItemPosition()==1){
						txtshow.setText(RouterOutwardReturn(callnumberbus(), 1));
					}
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
			
			dialog.show();
		}
		break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void ShowSpinner(Spinner spinner){
		
		List<String>list=new ArrayList<String>();
		list.add("Lượt đi");
		list.add("Lượt về");
		
		ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(arrayadapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menurouterclear, menu);
		return true;
	}

}
