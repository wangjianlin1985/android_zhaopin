package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Qiye;
public class QiyeListHandler extends DefaultHandler {
	private List<Qiye> qiyeList = null;
	private Qiye qiye;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (qiye != null) { 
            String valueString = new String(ch, start, length); 
            if ("qiyeUserName".equals(tempString)) 
            	qiye.setQiyeUserName(valueString); 
            else if ("password".equals(tempString)) 
            	qiye.setPassword(valueString); 
            else if ("qiyeName".equals(tempString)) 
            	qiye.setQiyeName(valueString); 
            else if ("qiyeQualify".equals(tempString)) 
            	qiye.setQiyeQualify(valueString); 
            else if ("qiyePropertyObj".equals(tempString)) 
            	qiye.setQiyePropertyObj(new Integer(valueString).intValue());
            else if ("qiyeProfessionObj".equals(tempString)) 
            	qiye.setQiyeProfessionObj(new Integer(valueString).intValue());
            else if ("qiyeScale".equals(tempString)) 
            	qiye.setQiyeScale(valueString); 
            else if ("connectPerson".equals(tempString)) 
            	qiye.setConnectPerson(valueString); 
            else if ("telephone".equals(tempString)) 
            	qiye.setTelephone(valueString); 
            else if ("email".equals(tempString)) 
            	qiye.setEmail(valueString); 
            else if ("address".equals(tempString)) 
            	qiye.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Qiye".equals(localName)&&qiye!=null){
			qiyeList.add(qiye);
			qiye = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		qiyeList = new ArrayList<Qiye>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Qiye".equals(localName)) {
            qiye = new Qiye(); 
        }
        tempString = localName; 
	}

	public List<Qiye> getQiyeList() {
		return this.qiyeList;
	}
}
