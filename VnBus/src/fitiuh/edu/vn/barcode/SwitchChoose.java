package fitiuh.edu.vn.barcode;

import fitiuh.edu.vn.vnbus.R;

public class SwitchChoose {
	
	enum numberBus{MaTuyen1,MaTuyen2,MaTuyen3,MaTuyen4,MaTuyen5,MaTuyen6,MaTuyen7,
				   MaTuyen8,MaTUyen9,MaTUyen10};
	
	//get id image from number bus
	public int imgBusFormData(int number) {
		
		int img=0;
		
		switch(number){
		
		case 1:
			img= R.drawable.numbers_1_icon;
			break;
		case 2:
			img= R.drawable.numbers_2_icon;
			break;
		case 3:
			img= R.drawable.numbers_3_icon;
			break;
		case 4:
			img= R.drawable.numbers_4_icon;
			break;
		case 5:
			img= R.drawable.numbers_5_icon;
			break;
		case 6:
			img= R.drawable.numbers_6_icon;
			break;
		case 7:
			img= R.drawable.numbers_7_icon;
			break;
		case 8:
			img= R.drawable.numbers_8_icon;
			break;
		case 9:
			img= R.drawable.numbers_9_icon;
			break;
		}
		
		return img;
		
	}
	
	public int iconMarkers(String numbus){
		
		int img=0;
		int numBus=Integer.parseInt(numbus);
		switch(numBus){
		case 1:
			img=R.drawable.marker1;
			break;
		case 2:
			img=R.drawable._marker1;
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		
		}
		
		return img;
	}
	
	//get id image
	public String barCodeScan(String idBarcodeScan){
		
		numberBus numberbus=numberBus.valueOf(idBarcodeScan);
		
		switch(numberbus){
			case MaTuyen1:
				return "R.drawable.numbers_1_icon";
			case MaTuyen2:
				return "R.drawable.numbers_2_icon";
			case MaTuyen3:
				return "R.drawable.numbers_3_icon";
			case MaTuyen4:
				return "R.drawable.numbers_4_icon";
			case MaTuyen5:
				return "R.drawable.numbers_5_icon";
			case MaTuyen6:
				return "R.drawable.numbers_6_icon";
			case MaTuyen7:
				return "R.drawable.numbers_7_icon";
			case MaTuyen8:
				return "R.drawable.numbers_8_icon";
			case MaTUyen9:
				return "R.drawable.numbers_9_icon";
		}
		
		return null;
	}
	
	//get number bus
	public int getnumberBus(String idBarcodeScan){
		int a=0;
		
		for (numberBus num:numberBus.values()){
			if(a==0){
				numberBus numberbus=numberBus.valueOf(idBarcodeScan);
				
				switch(numberbus){
					case MaTuyen1:
						a=1;break;
					case MaTuyen2:
						a=2;break;
					case MaTuyen3:
						a=3;break;
					case MaTuyen4:
						a=4;break;
					case MaTuyen5:
						a=5;break;
					case MaTuyen6:
						a=6;break;
					case MaTuyen7:
						a=7;break;
					case MaTuyen8:
						a=8;break;
					case MaTUyen9:
						a=9;break;	
				}
		
			}else{
				
				a=0;	
			}
		}
		
		return a;
		
	}
				

}
