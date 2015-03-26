package com.dxt.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
	
	public static LinkedList<OnlineQuestion> getOnlineQuestions(String url, String methodName,
			String searchBean){
		LinkedList<OnlineQuestion> ret =new LinkedList<OnlineQuestion>();
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
				ret.addAll(0, questions);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public static LinkedList<OnlineQuestion> getOnlineMyQuestions(String url, String methodName,
			String studentId){
		LinkedList<OnlineQuestion> ret =new LinkedList<OnlineQuestion>();
		List<OnlineQuestion> questions =new ArrayList<OnlineQuestion>();
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		request.addProperty("studentId", studentId);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		try {
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				Log.v("getOnlineMyQuestions---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				questions = JSON.parseArray(name, OnlineQuestion.class);
				for (OnlineQuestion onlineQuestion : questions) {
					Log.v("com.dxt", onlineQuestion.toString());
				}
				ret.addAll(0, questions);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public static int getOnlineQuestionSize(String url, String methodName,String searchBean){
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
				return Integer.parseInt(name);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
	
	public static int saveOnlineQuestionAnswer(String url,String methodName,String answer){
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
				//SoapObject result = (SoapObject) envelope.bodyIn;
				return 1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public static boolean uploadImage(String methodName,String fileName, String imageBuffer,String SERVICE_URL) {  
		// TODO Auto-generated method stub
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("filename", fileName); // 参数1 图片名
		soapObject.addProperty("image", imageBuffer); // 参数2 图片字符串
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		envelope.bodyOut = soapObject;
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		try {
			httpTranstation.call(null, envelope); // 这一步内存溢出
			if(envelope.getResponse() != null){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
