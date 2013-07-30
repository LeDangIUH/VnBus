package fitiuh.edu.vn.database;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class BarcodeList implements KvmSerializable {
	
	public int numberBus;
	public String idBarcode;
	public String idImage;
	
	public BarcodeList() {
		// TODO Auto-generated constructor stub
	}

	public BarcodeList(int numberBus, String idBarcode, String idImage) {
		super();
		this.numberBus = numberBus;
		this.idBarcode = idBarcode;
		this.idImage = idImage;
	}

	@Override
	public Object getProperty(int arg0) {
		
		switch(arg0){
		case 0:
			return idBarcode;
		case 1:
			return idImage;
		case 2:
			return numberBus;
		
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0){
		case 0:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="idBarcode";
			break;
		case 1:
			arg2.type=PropertyInfo.STRING_CLASS;
			arg2.name="idImage";
			break;
		case 2:
			arg2.type=PropertyInfo.INTEGER_CLASS;
			arg2.name="numberBus";
			break;
		}
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch(arg0){
		case 0:
			idBarcode=arg1.toString();
			break;
		case 1:
			idImage=arg1.toString();
			break;
		case 2:
			numberBus=Integer.parseInt(arg1.toString());
			break;
		default:
			break;
		}
		
	}
	
	

}
