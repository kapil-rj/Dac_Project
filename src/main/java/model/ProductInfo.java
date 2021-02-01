package model;

import entity.*;




public class ProductInfo {

    private String code;
    private String name;
    private double price;
    private String productCategory;
    private String description;
    private String specification;

    public ProductInfo() {
    }

    public ProductInfo(Product product, ProductDetails productDetails) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.productCategory = product.getProductCategory();
        this.description = productDetails.getDescription();
        this.specification = productDetails.getSpecification();
    }

    // Using in JPA/Hibernate query
    public ProductInfo(String code, String name, double price, String productCategory, String description, String specification) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;
        this.description = description;
        this.specification = specification;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
    
    
}
