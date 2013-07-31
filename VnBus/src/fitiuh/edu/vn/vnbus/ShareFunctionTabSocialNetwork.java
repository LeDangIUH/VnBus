package fitiuh.edu.vn.vnbus;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ShareFunctionTabSocialNetwork extends Fragment {
	
	ShareHorizontaListView ls;
	GoogleMap map;
	private final LatLng LOCATION_SURRREY = new LatLng(10.820908200869496,106.68407135789789);

	private static View myFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 if (myFragment != null) {
		        ViewGroup parent = (ViewGroup) myFragment.getParent();
		        if (parent != null)
		            parent.removeView(myFragment);
		    }
		    try {
		    	myFragment = inflater.inflate(R.layout.activity_sharefunctionsocietynetwork, container, false);
		    	
		    	ls=(ShareHorizontaListView) myFragment.findViewById(R.id.lssocialnetwork);
				ls.setAdapter(new HAdapter());
				
		    } catch (InflateException e) {
		  
				map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();		
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_SURRREY, 10));

		    }
		    return myFragment;
	}
	
	private static String[] dataObjects = new String[]{ "Text #1",
		"Text #2",
		"Text #3",
		"Text android",
		"Text connnect",
		"www.androidconnect.org"}; 
	
	private class  HAdapter extends BaseAdapter{
		
		public HAdapter(){
			super();
		}

		
		public int getCount() {
			return dataObjects.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sociatynetworkitem, null);
			ImageView im1=(ImageView) retval.findViewById(R.id.imgnumbussociety);
			TextView t1=(TextView) retval.findViewById(R.id.timesociety);
			TextView t2=(TextView) retval.findViewById(R.id.timesociety);
			TextView t3=(TextView) retval.findViewById(R.id.timesociety);
			TextView t4=(TextView) retval.findViewById(R.id.timesociety);
			//t3.setText(dataObjects[position]);
			
			return retval;
		}
	};
}
