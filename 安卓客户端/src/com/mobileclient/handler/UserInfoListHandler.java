package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.UserInfo;
public class UserInfoListHandler extends DefaultHandler {
	private List<UserInfo> userInfoList = null;
	private UserInfo userInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (userInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("user_name".equals(tempString)) 
            	userInfo.setUser_name(valueString); 
            else if ("password".equals(tempString)) 
            	userInfo.setPassword(valueString); 
            else if ("name".equals(tempString)) 
            	userInfo.setName(valueString); 
            else if ("gender".equals(tempString)) 
            	userInfo.setGender(valueString); 
            else if ("birthDate".equals(tempString)) 
            	userInfo.setBirthDate(Timestamp.valueOf(valueString));
            else if ("userPhoto".equals(tempString)) 
            	userInfo.setUserPhoto(valueString); 
            else if ("myShoolRecord".equals(tempString)) 
            	userInfo.setMyShoolRecord(new Integer(valueString).intValue());
            else if ("schoolName".equals(tempString)) 
            	userInfo.setSchoolName(valueString); 
            else if ("workYears".equals(tempString)) 
            	userInfo.setWorkYears(valueString); 
            else if ("telephone".equals(tempString)) 
            	userInfo.setTelephone(valueString); 
            else if ("email".equals(tempString)) 
            	userInfo.setEmail(valueString); 
            else if ("address".equals(tempString)) 
            	userInfo.setAddress(valueString); 
            else if ("qzyx".equals(tempString)) 
            	userInfo.setQzyx(valueString); 
            else if ("gzjl".equals(tempString)) 
            	userInfo.setGzjl(valueString); 
            else if ("jyjl".equals(tempString)) 
            	userInfo.setJyjl(valueString); 
            else if ("zwpj".equals(tempString)) 
            	userInfo.setZwpj(valueString); 
            else if ("regTime".equals(tempString)) 
            	userInfo.setRegTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("UserInfo".equals(localName)&&userInfo!=null){
			userInfoList.add(userInfo);
			userInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		userInfoList = new ArrayList<UserInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("UserInfo".equals(localName)) {
            userInfo = new UserInfo(); 
        }
        tempString = localName; 
	}

	public List<UserInfo> getUserInfoList() {
		return this.userInfoList;
	}
}
