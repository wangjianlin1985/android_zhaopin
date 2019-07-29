package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.QiyeProfession;
public class QiyeProfessionListHandler extends DefaultHandler {
	private List<QiyeProfession> qiyeProfessionList = null;
	private QiyeProfession qiyeProfession;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (qiyeProfession != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	qiyeProfession.setId(new Integer(valueString).intValue());
            else if ("professionName".equals(tempString)) 
            	qiyeProfession.setProfessionName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("QiyeProfession".equals(localName)&&qiyeProfession!=null){
			qiyeProfessionList.add(qiyeProfession);
			qiyeProfession = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		qiyeProfessionList = new ArrayList<QiyeProfession>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("QiyeProfession".equals(localName)) {
            qiyeProfession = new QiyeProfession(); 
        }
        tempString = localName; 
	}

	public List<QiyeProfession> getQiyeProfessionList() {
		return this.qiyeProfessionList;
	}
}
