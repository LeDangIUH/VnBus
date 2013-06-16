package fitiuh.edu.vn.database;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Coordinate implements KvmSerializable {
	
	public int NUMBER_BUS;
	public String DIRECTION;
	public double LONGITUDE;
	public double LATITUDE;
	public String ADDRESS;
	
	public Coordinate() {}
	

	public Coordinate(int nUMBER_BUS, String dIRECTION, double lONGITUDE,
			double lATITUDE, String aDDRESS) {
		super();
		NUMBER_BUS = nUMBER_BUS;
		DIRECTION = dIRECTION;
		LONGITUDE = lONGITUDE;
		LATITUDE = lATITUDE;
		ADDRESS = aDDRESS;
	}

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
			return ADDRESS;
		case 1:
			return LONGITUDE;
		case 2:
			return DIRECTION;
		case 3:
			return NUMBER_BUS;
		case 4:
			return LATITUDE;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 5;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0){
		case 0:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="ADDRESS";
			break;
		case 1:
			arg2.type=Double.class;
			arg2.name="LONGITUDE";
			break;
		case 2:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="DIRECTION";
			break;
		case 3:
			arg2.type=PropertyInfo.INTEGER_CLASS;
			arg2.name="NUMBER_BUS";
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
			ADDRESS=arg1.toString();
			break;
		case 1:
			LONGITUDE=Double.parseDouble(arg1.toString());
			break;
		case 2:
			DIRECTION=arg1.toString();
			break;
		case 3:
			NUMBER_BUS=Integer.parseInt(arg1.toString());
			break;
		case 4:
			LATITUDE=Double.parseDouble(arg1.toString());
			break;
		default:
			break;
		}
	}

}
