package fitiuh.edu.vn.vnbus;

import fitiuh.edu.vn.barcode.SwitchChoose;
import fitiuh.edu.vn.database.BusDBAdapter;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShareFunctionTabGPS extends Fragment {

	ImageView img;
	TextView nameT,timeS;
	
	BusDBAdapter myDb;
	SwitchChoose switchChoose;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myFragment=inflater.inflate(R.layout.activity_sharefunctiongps, container, false);
		openDB();

		img=(ImageView) myFragment.findViewById(R.id.imgnumbussociety);
		nameT=(TextView) myFragment.findViewById(R.id.txtnamebusgps);
		timeS=(TextView) myFragment.findViewById(R.id.txtshowtimegps);
		
		switchChoose=new SwitchChoose();
		img.setImageResource(switchChoose.imgBusFormData(getNumber()));
		timeS.setText(getTime());
		nameT.setText(getName(getNumber()));
		
		//Toast.makeText(getActivity().getApplicationContext(),getName(1), Toast.LENGTH_LONG).show();
		
		
		return myFragment;
	}
	
	private int getNumber(){
		int numBus=0;
		Cursor c=myDb.getAllRowsStore();
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			numBus=c.getInt(0);
		}
		
		return numBus;
	}
	
	private String getTime(){
		String time="hh:mm:ss";
		
		Cursor c=myDb.getAllRowsStore();
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			time=c.getString(1);
		}
		return time;
	}
	
	private String getName(int number){
		String name="Tuyến xe buýt";
		Cursor c=myDb.getRow(number);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			name=c.getString(1);
		}
		
		return name;
	}
	
	@Override
	public void onDestroy() {
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
		
}
