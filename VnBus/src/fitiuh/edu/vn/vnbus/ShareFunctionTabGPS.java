package fitiuh.edu.vn.vnbus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShareFunctionTabGPS extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragment=inflater.inflate(R.layout.activity_sharefunctiongps, container, false);
		return myFragment;
	}
}
