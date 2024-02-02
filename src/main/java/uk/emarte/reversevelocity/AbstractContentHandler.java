package uk.emarte.reversevelocity;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

abstract class AbstractContentHandler extends DefaultHandler {
	private final StringBuffer buffer = new StringBuffer();
	private final Stack<String> path = new Stack<>();
	
	AbstractContentHandler() {
		// can't peek an empty stack
		path.push("");
	}
	
	protected abstract void processElement(String path, Attributes attributes);

	protected abstract void processText(String path, String text);
	
	@Override
	public void characters(char[] ch, int start, int length) {
		buffer.append(ch, start, length);
    }

	@Override
	public void endElement(String namespaceURI, String localName, String qname) {
		String text = buffer.toString().trim();
		buffer.setLength(0);

		if (text.length() > 0) {
			processText(path.peek(), text);
		}

		path.pop();
    }

	@Override
	public void startElement(String namespaceURI, String localName, String qname, Attributes attributes) {
		boolean namespaced = namespaceURI != null && namespaceURI.trim().length() > 0;
		String elementName = namespaced ? namespaceURI + ":" + localName : localName;
		path.push(path.peek() + "/" + elementName);
		processElement(path.peek(), attributes);
    }
}
