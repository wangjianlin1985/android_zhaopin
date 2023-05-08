package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.NewsClass;
public class NewsClassListHandler extends DefaultHandler {
	private List<NewsClass> newsClassList = null;
	private NewsClass newsClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (newsClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("newsClassId".equals(tempString)) 
            	newsClass.setNewsClassId(new Integer(valueString).intValue());
            else if ("newsClassName".equals(tempString)) 
            	newsClass.setNewsClassName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("NewsClass".equals(localName)&&newsClass!=null){
			newsClassList.add(newsClass);
			newsClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		newsClassList = new ArrayList<NewsClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("NewsClass".equals(localName)) {
            newsClass = new NewsClass(); 
        }
        tempString = localName; 
	}

	public List<NewsClass> getNewsClassList() {
		return this.newsClassList;
	}
}
