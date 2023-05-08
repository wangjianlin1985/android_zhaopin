package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SchoolRecord;
public class SchoolRecordListHandler extends DefaultHandler {
	private List<SchoolRecord> schoolRecordList = null;
	private SchoolRecord schoolRecord;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (schoolRecord != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	schoolRecord.setId(new Integer(valueString).intValue());
            else if ("schooRecordName".equals(tempString)) 
            	schoolRecord.setSchooRecordName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SchoolRecord".equals(localName)&&schoolRecord!=null){
			schoolRecordList.add(schoolRecord);
			schoolRecord = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		schoolRecordList = new ArrayList<SchoolRecord>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SchoolRecord".equals(localName)) {
            schoolRecord = new SchoolRecord(); 
        }
        tempString = localName; 
	}

	public List<SchoolRecord> getSchoolRecordList() {
		return this.schoolRecordList;
	}
}
