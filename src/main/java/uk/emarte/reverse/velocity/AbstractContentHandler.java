package uk.emarte.reverse.velocity;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

abstract class AbstractContentHandler extends DefaultHandler {
	private StringBuffer buffer = new StringBuffer();
	private Stack<String> path = new Stack<String>();
	
	AbstractContentHandler() {
		// can't peek an empty stack
		path.push("");
	}
	
	protected abstract void processElement(String path, Attributes attributes);

	protected abstract void processText(String path, String text);
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		buffer.append(ch, start, length);
    }

	@Override
	public void endElement(String namespaceURI, String localName, String qname) throws SAXException {
		String text = buffer.toString().trim();
		buffer.setLength(0);

		if (text.length() > 0) {
			processText(path.peek(), text);
		}

		path.pop();
    }

	@Override
	public void startElement(String namespaceURI, String localName, String qname, Attributes attributes) throws SAXException {
		boolean namespaced = namespaceURI != null && namespaceURI.trim().length() > 0;
		String elementName = namespaced ? namespaceURI + ":" + localName : localName;
		path.push(path.peek() + "/" + elementName);
		processElement(path.peek(), attributes);
    }
}
