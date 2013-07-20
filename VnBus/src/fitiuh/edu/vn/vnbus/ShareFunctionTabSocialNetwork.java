package fitiuh.edu.vn.vnbus;



import android.app.Fragment;
import android.os.Bundle;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myFragment=inflater.inflate(R.layout.activity_sharefunctionsocietynetwork, container,false);
		
		ls=(ShareHorizontaListView) myFragment.findViewById(R.id.lssocialnetwork);
		ls.setAdapter(new HAdapter());
		
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
