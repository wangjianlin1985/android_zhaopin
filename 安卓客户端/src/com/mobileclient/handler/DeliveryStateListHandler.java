package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.DeliveryState;
public class DeliveryStateListHandler extends DefaultHandler {
	private List<DeliveryState> deliveryStateList = null;
	private DeliveryState deliveryState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (deliveryState != null) { 
            String valueString = new String(ch, start, length); 
            if ("stateId".equals(tempString)) 
            	deliveryState.setStateId(new Integer(valueString).intValue());
            else if ("stateName".equals(tempString)) 
            	deliveryState.setStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("DeliveryState".equals(localName)&&deliveryState!=null){
			deliveryStateList.add(deliveryState);
			deliveryState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		deliveryStateList = new ArrayList<DeliveryState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("DeliveryState".equals(localName)) {
            deliveryState = new DeliveryState(); 
        }
        tempString = localName; 
	}

	public List<DeliveryState> getDeliveryStateList() {
		return this.deliveryStateList;
	}
}
