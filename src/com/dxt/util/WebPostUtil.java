package com.dxt.util;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

public class WebPostUtil {
	final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	private static ReturnMessage retMessage = new ReturnMessage();

	public static ReturnMessage getMessage(String url, String methodName,
			String userInfo) {
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		request.addProperty("userInfo", userInfo);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		try {
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				JSONObject ob = JSONObject.parseObject(name);
				int status = ob.getIntValue("status");
				retMessage.setStatus(status);
				retMessage.setMessage(ob.getString("message"));
			}else{
				retMessage.setStatus(0);
				retMessage.setMessage("Connection failure");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retMessage;
	}
}