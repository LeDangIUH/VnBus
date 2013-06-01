package fitiuh.edu.vn.vnbus;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.os.Bundle;

public class ShareFunction extends Activity {
	
	private final LatLng LOCATION_SURRREY = new LatLng(10.820908200869496,106.68407135789789);
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharefunction);
		
		map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));
		
	}
	
}
