package com.dxt.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.OnlineQuestionAnswer;
import com.dxt.model.User;

public class WebPostUtil {
	final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	private static ReturnMessage retMessage = new ReturnMessage();

	public static User getUserById(String url, String methodName,
			String id){
		User u = null ;
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		request.addProperty("id", id);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		try {
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				u = JSON.parseObject(name, User.class);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}
	
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
	
	public static List<OnlineQuestion> getOnlineQuestions(String url, String methodName,
			String searchBean){
		List<OnlineQuestion> questions =new ArrayList<OnlineQuestion>();
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		request.addProperty("searchBean", searchBean);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		try {
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				questions = JSON.parseArray(name, OnlineQuestion.class);
				for (OnlineQuestion onlineQuestion : questions) {
					Log.v("com.dxt", onlineQuestion.toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;
	}
	
	public static List<OnlineQuestionAnswer> getOnlineQuestionAnswer(String url, String methodName,
			String questionID){
		List<OnlineQuestionAnswer> answers =new ArrayList<OnlineQuestionAnswer>();
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		request.addProperty("onlineQuestionID", questionID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		try {
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				answers = JSON.parseArray(name, OnlineQuestionAnswer.class);
				for (OnlineQuestionAnswer answer : answers) {
					Log.v("com.dxt", answer.toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answers;
	}
	
	public static void saveOnlineQuestionAnswer(String url,String methodName,String answer){
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		request.addProperty("answer", answer);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		try {
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				Log.v("lll---", name);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static boolean uploadImage(String methodName,String fileName, String imageBuffer,String SERVICE_URL) {  
		// TODO Auto-generated method stub
		//String namespace = "http://134.192.44.105:8080/SSH2/service/IService"; // �����ռ䣬���������˵ýӿڣ�ע����׺û��
		//String namespace = StringConstant.SERVICE_URL+"service"; 																		// .wsdl��
		// ��������������x-fireʵ��webservice�ӿڵ�
		//String url = "http://134.192.44.105:8080/SSH2/service/IService"; // ��Ӧ��url
		// ���¾��� ���ù����ˣ������׵Ļ� �뿴���webservice�ĵ�
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("filename", fileName); // ����1 ͼƬ��
		soapObject.addProperty("image", imageBuffer); // ����2 ͼƬ�ַ���
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		//envelope.dotNet = false;
		//envelope.setOutputSoapObject(soapObject);
		envelope.bodyOut = soapObject;
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		try {
			httpTranstation.call(null, envelope); // ��һ���ڴ����
			if(envelope.getResponse() != null){
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				//Log.i(TAG, name);
				return true;
			}else{
				//
			//	Log.i(StringConstant.SERVICE, "Connection failure");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
