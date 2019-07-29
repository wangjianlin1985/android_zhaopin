package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SpecialInfo;
public class SpecialInfoListHandler extends DefaultHandler {
	private List<SpecialInfo> specialInfoList = null;
	private SpecialInfo specialInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (specialInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("specialId".equals(tempString)) 
            	specialInfo.setSpecialId(new Integer(valueString).intValue());
            else if ("specialName".equals(tempString)) 
            	specialInfo.setSpecialName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SpecialInfo".equals(localName)&&specialInfo!=null){
			specialInfoList.add(specialInfo);
			specialInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		specialInfoList = new ArrayList<SpecialInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SpecialInfo".equals(localName)) {
            specialInfo = new SpecialInfo(); 
        }
        tempString = localName; 
	}

	public List<SpecialInfo> getSpecialInfoList() {
		return this.specialInfoList;
	}
}
