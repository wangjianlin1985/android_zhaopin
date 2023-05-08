package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Job;
public class JobListHandler extends DefaultHandler {
	private List<Job> jobList = null;
	private Job job;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (job != null) { 
            String valueString = new String(ch, start, length); 
            if ("jobId".equals(tempString)) 
            	job.setJobId(new Integer(valueString).intValue());
            else if ("qiyeObj".equals(tempString)) 
            	job.setQiyeObj(valueString); 
            else if ("positionName".equals(tempString)) 
            	job.setPositionName(valueString); 
            else if ("jobTypeObj".equals(tempString)) 
            	job.setJobTypeObj(new Integer(valueString).intValue());
            else if ("specialObj".equals(tempString)) 
            	job.setSpecialObj(new Integer(valueString).intValue());
            else if ("personNum".equals(tempString)) 
            	job.setPersonNum(valueString); 
            else if ("city".equals(tempString)) 
            	job.setCity(valueString); 
            else if ("salary".equals(tempString)) 
            	job.setSalary(valueString); 
            else if ("schoolRecordObj".equals(tempString)) 
            	job.setSchoolRecordObj(new Integer(valueString).intValue());
            else if ("workYears".equals(tempString)) 
            	job.setWorkYears(valueString); 
            else if ("workAddress".equals(tempString)) 
            	job.setWorkAddress(valueString); 
            else if ("welfare".equals(tempString)) 
            	job.setWelfare(valueString); 
            else if ("positionDesc".equals(tempString)) 
            	job.setPositionDesc(valueString); 
            else if ("connectPerson".equals(tempString)) 
            	job.setConnectPerson(valueString); 
            else if ("telephone".equals(tempString)) 
            	job.setTelephone(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Job".equals(localName)&&job!=null){
			jobList.add(job);
			job = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		jobList = new ArrayList<Job>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Job".equals(localName)) {
            job = new Job(); 
        }
        tempString = localName; 
	}

	public List<Job> getJobList() {
		return this.jobList;
	}
}
