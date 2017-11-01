package com.reverse.velocity.test;

import java.util.List;

public class Response {
	String message;
	List<Product> products;

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
			"message='" + message + '\'' +
			", products=" + products +
			'}';
	}
}
