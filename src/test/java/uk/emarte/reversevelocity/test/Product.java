package uk.emarte.reversevelocity.test;

public class Product {
	private int id;
	private String name;
	private String description;
	private ProductType productType;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ProductType getType() {
		return productType;
	}
	
	public void setType(ProductType productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "Product(" + id + ", " + name + ", " + description + ", " + productType + ")";
	}
}
