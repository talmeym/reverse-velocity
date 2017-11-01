package com.reverse.velocity.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.reverse.velocity.ParseException;
import com.reverse.velocity.Parser;
import com.reverse.velocity.ParserFactory;

public class Test {
	public static void main(String[] args) {
		try {
			Parser parser = ParserFactory.createParserFromTemplateFile(new File(args[0]));
			Map<String, Object> result = parser.parse(IOUtils.toString(new FileInputStream(args[1])));
			System.out.println(result);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
