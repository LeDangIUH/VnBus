package fitiuh.edu.vn.database;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Bus implements KvmSerializable  {
	
	public int NUMBER_BUS;//ma tuyen
	public String NAME_BUS;//ten tuyen
	public String OUTWARD_JOURNEY;//luot di
	public String RETURN_JOURNEY;//luot ve
	public String FORM_ACTIVITY;//loai hinh hoat dong
	public String STRETCH_ROAD; //cu li
	public String COUNT_TRIP;//so chuyen
	public String TIME_TRIP;//thoi gian chuyen
	public String SPACE_TRIP;//gian cach
	public String START_END;//thoi gian hoat dong
	public String FORM_BUS;//loai xe
	public int TICKET_FACE;//gia ve
	public String ADMIN_ACTIVITY;//don vi dam nhan
	
	public Bus(){}
	
	public Bus(int nUMBER_BUS, String nAME_BUS, String oUTWARD_JOURNEY,
			String rETURN_JOURNEY, String fORM_ACTIVITY, String sTRETCH_ROAD,
			String cOUNT_TRIP, String tIME_TRIP, String sPACE_TRIP,
			String sTART_END, String fORM_BUS, int tICKET_FACE,
			String aDMIN_ACTIVITY) {
		super();
		NUMBER_BUS = nUMBER_BUS;
		NAME_BUS = nAME_BUS;
		OUTWARD_JOURNEY = oUTWARD_JOURNEY;
		RETURN_JOURNEY = rETURN_JOURNEY;
		FORM_ACTIVITY = fORM_ACTIVITY;
		STRETCH_ROAD = sTRETCH_ROAD;
		COUNT_TRIP = cOUNT_TRIP;
		TIME_TRIP = tIME_TRIP;
		SPACE_TRIP = sPACE_TRIP;
		START_END = sTART_END;
		FORM_BUS = fORM_BUS;
		TICKET_FACE = tICKET_FACE;
		ADMIN_ACTIVITY = aDMIN_ACTIVITY;
	}

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
			return STRETCH_ROAD;
		case 1:
			return 	ADMIN_ACTIVITY;
		case 2:
			return TICKET_FACE;
		case 3:
			return SPACE_TRIP;
		case 4:
			return FORM_ACTIVITY;
		case 5:
			return FORM_BUS;
		case 6:
			return OUTWARD_JOURNEY;
		case 7:
			return RETURN_JOURNEY;
		case 8:
			return NUMBER_BUS;
		case 9:
			return COUNT_TRIP;
		case 10:
			return NAME_BUS;
		case 11:
			return TIME_TRIP;
		case 12:
			return START_END;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		
		return 0;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		
		
	}

}
