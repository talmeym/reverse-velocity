package com.reverse.velocity.test;

import java.util.Date;
import java.util.List;

public class Response {
	Date timestamp;
	String message;
	List<Product> products;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Response{" +
			"timestamp=" + timestamp +
			", message='" + message + '\'' +
			", products=" + products +
			'}';
	}
}
