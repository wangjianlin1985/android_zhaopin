package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.QiyeProperty;
public class QiyePropertyListHandler extends DefaultHandler {
	private List<QiyeProperty> qiyePropertyList = null;
	private QiyeProperty qiyeProperty;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (qiyeProperty != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	qiyeProperty.setId(new Integer(valueString).intValue());
            else if ("propertyName".equals(tempString)) 
            	qiyeProperty.setPropertyName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("QiyeProperty".equals(localName)&&qiyeProperty!=null){
			qiyePropertyList.add(qiyeProperty);
			qiyeProperty = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		qiyePropertyList = new ArrayList<QiyeProperty>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("QiyeProperty".equals(localName)) {
            qiyeProperty = new QiyeProperty(); 
        }
        tempString = localName; 
	}

	public List<QiyeProperty> getQiyePropertyList() {
		return this.qiyePropertyList;
	}
}
