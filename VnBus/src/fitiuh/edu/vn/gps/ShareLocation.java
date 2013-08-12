package fitiuh.edu.vn.gps;

import fitiuh.edu.vn.database.*;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;


import android.os.AsyncTask;
import android.text.format.Time;

public class ShareLocation {
	
	final String NAMESPACE="http://test_bus/";
	final String METHOD_NAME="Share_Locations";
	final String SOAP_ACTION="http://test_bus/Share_Locations";
	final String URL="http://192.168.0.108:8080/BUS_PRO/Services?WSDL";
	SoapObject response;
	
	
	int numberBus;
	double phoneIdSim;
	double Latitude;
	double Longitude;
	String timeShare;
	
	public void callShare(int numberbus,double phoneidsim,double latitude,double longitude,String timeshare){
		
		numberBus=numberbus;
		phoneIdSim=phoneidsim;
		Latitude=latitude;
		Longitude=longitude;
		timeShare=timeshare;
	
		new backMethod().execute();	
	}
	
	
	
	public class backMethod extends AsyncTask<SoapObject, SoapObject, SoapObject > {

		@Override
		protected SoapObject doInBackground(SoapObject... params) {
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			PropertyInfo pi = new PropertyInfo();
	        pi.setName("MaTuyen");
	        pi.setValue(numberBus);
	        pi.setType(Integer.class);
	        request.addProperty(pi);
	        
	        PropertyInfo pi1=new PropertyInfo();
	        pi1.setName("sdt");
	        pi1.setValue(phoneIdSim);
	        pi1.setType(Double.class);
	        request.addProperty(pi1);
	        
	        PropertyInfo pi2 = new PropertyInfo();
	        pi2.setName("ViDo");
	        pi2.setType(Double.class);
	        pi2.setValue(Latitude);
	        request.addProperty(pi2);
	        
	        
	        PropertyInfo pi3 = new PropertyInfo();
	        pi3.setName("KinhDo");
	        pi3.setType(Double.class);
	        pi3.setValue(Longitude);
	        request.addProperty(pi3);
	        
			
	        PropertyInfo pi4=new PropertyInfo();
	        pi4.setName("TimeShare");
	        pi4.setType(String.class);
	        pi4.setValue(timeShare);
	        request.addProperty(pi4);

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	           
            envelope.setOutputSoapObject(request);
            
            MarshalDouble md=new MarshalDouble();
            md.register(envelope);
            
            envelope.addMapping(NAMESPACE,"ShareOption",new ShareOption().getClass());
            
            try {

                AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
                
                androidHttpTransport.debug=true;

                androidHttpTransport.call(SOAP_ACTION, envelope);
                                         
                //response = (SoapObject)envelope.getResponse();
                                                                                                 
                } catch (Exception e) {
                    e.printStackTrace();
                }

			return response;
		}
		
	}
	
	public static class MarshalDouble implements Marshal{

		@Override
		public Object readInstance(XmlPullParser arg0, String arg1,
				String arg2, PropertyInfo arg3) throws IOException,
				XmlPullParserException {
			// TODO Auto-generated method stub
			return Double.parseDouble(arg0.nextText());
		}

		@Override
		public void register(SoapSerializationEnvelope arg0) {
			arg0.addMapping(arg0.xsd, "double", Double.class, this);
			
		}

		@Override
		public void writeInstance(XmlSerializer arg0, Object arg1)
				throws IOException {
			arg0.text(arg1.toString());
			
		}
		
	}
	
	

}
