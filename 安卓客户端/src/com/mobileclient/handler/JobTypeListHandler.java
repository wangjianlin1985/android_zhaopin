package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.JobType;
public class JobTypeListHandler extends DefaultHandler {
	private List<JobType> jobTypeList = null;
	private JobType jobType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (jobType != null) { 
            String valueString = new String(ch, start, length); 
            if ("jobTypeId".equals(tempString)) 
            	jobType.setJobTypeId(new Integer(valueString).intValue());
            else if ("typeName".equals(tempString)) 
            	jobType.setTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("JobType".equals(localName)&&jobType!=null){
			jobTypeList.add(jobType);
			jobType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		jobTypeList = new ArrayList<JobType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("JobType".equals(localName)) {
            jobType = new JobType(); 
        }
        tempString = localName; 
	}

	public List<JobType> getJobTypeList() {
		return this.jobTypeList;
	}
}
