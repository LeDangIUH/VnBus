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
		return 13;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0)
		{
		case 0:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="STRETCH_ROAD";
			break;
		case 1:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="ADMIN_ACTIVITY";
			break;
		case 2:
			arg2.type=PropertyInfo.INTEGER_CLASS;
			arg2.name="TICKET_FACE";
			break;
		case 3:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="SPACE_TRIP";
			break;
		case 4:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="FORM_ACTIVITY";
			break;
		case 5:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="FORM_BUS";
			break;
		case 6:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="OUTWARD_JOURNEY";
			break;
		case 7:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="RETURN_JOURNEY";
			break;
		case 8:
			arg2.type=PropertyInfo.INTEGER_CLASS;
			arg2.name="NUMBER_BUS";
			break;
		case 9:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="COUNT_TRIP";
			break;
		case 10:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="NAME_BUS";
			break;
		case 11:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="TIME_TRIP";
			break;
		case 12:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="START_END";
			break;
		}
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
		case 0:
			STRETCH_ROAD=arg1.toString();
			break;
		case 1:
			ADMIN_ACTIVITY=arg1.toString();
			break;
		case 2:
			TICKET_FACE=Integer.parseInt(arg1.toString());
			break;
		case 3:
			SPACE_TRIP=arg1.toString();
			break;
		case 4:
			FORM_ACTIVITY=arg1.toString();
			break;
		case 5:
			FORM_BUS=arg1.toString();
			break;
		case 6:
			OUTWARD_JOURNEY=arg1.toString();
			break;
		case 7:
			RETURN_JOURNEY=arg1.toString();
			break;
		case 8:
			NUMBER_BUS=Integer.parseInt(arg1.toString());
			break;
		case 9:
			COUNT_TRIP=arg1.toString();
			break;
		case 10:
			NAME_BUS=arg1.toString();
			break;
		case 11:
			TIME_TRIP=arg1.toString();
			break;
		case 12:
			START_END=arg1.toString();
			break;
		default:
			break;
		}
		
	}

}
