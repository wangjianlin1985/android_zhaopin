package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Delivery;
public class DeliveryListHandler extends DefaultHandler {
	private List<Delivery> deliveryList = null;
	private Delivery delivery;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (delivery != null) { 
            String valueString = new String(ch, start, length); 
            if ("deliveryId".equals(tempString)) 
            	delivery.setDeliveryId(new Integer(valueString).intValue());
            else if ("jobObj".equals(tempString)) 
            	delivery.setJobObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	delivery.setUserObj(valueString); 
            else if ("deliveryTime".equals(tempString)) 
            	delivery.setDeliveryTime(valueString); 
            else if ("stateObj".equals(tempString)) 
            	delivery.setStateObj(new Integer(valueString).intValue());
            else if ("deliveryDemo".equals(tempString)) 
            	delivery.setDeliveryDemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Delivery".equals(localName)&&delivery!=null){
			deliveryList.add(delivery);
			delivery = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		deliveryList = new ArrayList<Delivery>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Delivery".equals(localName)) {
            delivery = new Delivery(); 
        }
        tempString = localName; 
	}

	public List<Delivery> getDeliveryList() {
		return this.deliveryList;
	}
}
