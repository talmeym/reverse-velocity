package com.reverse.velocity.test;

import java.text.MessageFormat;
import java.text.ParseException;

public class MessageFormatTest {
	
	public static void main(String[] args) {
		MessageFormat format = new MessageFormat("@{2}:{1}[{3}]");
		try {
			Object[] objs = format.parse(args[0]);
			System.out.println(objs);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}