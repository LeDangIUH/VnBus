package fitiuh.edu.vn.database;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ShareOption implements KvmSerializable {
	
	public int IDNUMBERBUS;
	public double PHONEIDSIM;
	public double LATITUDE;
	public double LONGITUDE;
	public String TIMESHARE;
	
	public ShareOption() {
		// TODO Auto-generated constructor stub
	}

	public ShareOption(int iDNUMBERBUS, double pHONEIDSIM,
			double lATITUDE, double lONGITUDE, String tIMESHARE) {
		super();
		IDNUMBERBUS = iDNUMBERBUS;
		PHONEIDSIM = pHONEIDSIM;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		TIMESHARE = tIMESHARE;
	}

	@Override
	public Object getProperty(int arg0) {
		
		switch(arg0){
		case 0:
			return LONGITUDE;
		case 1:
			return IDNUMBERBUS;
		case 2:
			return PHONEIDSIM;
		case 3:
			return TIMESHARE;
		case 4:
			return LATITUDE;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		
		switch(arg0){
		case 0:
			arg2.type=Double.class;
			arg2.name="LONGITUDE";
			break;
		case 1:
			arg2.type=PropertyInfo.INTEGER_CLASS;
			arg2.name="IDNUMBERBUS";
			break;
		case 2:
			arg2.type=Double.class;
			arg2.name="PHONEIDSIM";
			break;
		case 3:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="TIMESHARE";
			break;
		case 4:
			arg2.type=Double.class;
			arg2.name="LATITUDE";
			break;
		}		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
		case 0:
			LONGITUDE=Double.parseDouble(arg1.toString());
			break;
		case 1:
			IDNUMBERBUS=Integer.parseInt(arg1.toString());
			break;
		case 2:
			PHONEIDSIM=Double.parseDouble(arg1.toString());
			break;
		case 3:
			TIMESHARE=arg1.toString();
			break;
		case 4:
			LATITUDE=Double.parseDouble(arg1.toString());
			break;
		default:
			break;
		}
		
	}
	
	
	

}
