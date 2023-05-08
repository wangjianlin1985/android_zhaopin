package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.JobWant;
public class JobWantListHandler extends DefaultHandler {
	private List<JobWant> jobWantList = null;
	private JobWant jobWant;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (jobWant != null) { 
            String valueString = new String(ch, start, length); 
            if ("wantId".equals(tempString)) 
            	jobWant.setWantId(new Integer(valueString).intValue());
            else if ("jobTypeObj".equals(tempString)) 
            	jobWant.setJobTypeObj(new Integer(valueString).intValue());
            else if ("specialObj".equals(tempString)) 
            	jobWant.setSpecialObj(new Integer(valueString).intValue());
            else if ("positionName".equals(tempString)) 
            	jobWant.setPositionName(valueString); 
            else if ("salary".equals(tempString)) 
            	jobWant.setSalary(valueString); 
            else if ("workCity".equals(tempString)) 
            	jobWant.setWorkCity(valueString); 
            else if ("wantMemo".equals(tempString)) 
            	jobWant.setWantMemo(valueString); 
            else if ("userObj".equals(tempString)) 
            	jobWant.setUserObj(valueString); 
            else if ("addTime".equals(tempString)) 
            	jobWant.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("JobWant".equals(localName)&&jobWant!=null){
			jobWantList.add(jobWant);
			jobWant = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		jobWantList = new ArrayList<JobWant>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("JobWant".equals(localName)) {
            jobWant = new JobWant(); 
        }
        tempString = localName; 
	}

	public List<JobWant> getJobWantList() {
		return this.jobWantList;
	}
}
