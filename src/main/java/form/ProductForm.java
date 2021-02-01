package form;

import entity.Product;
import entity.ProductDetails;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

public class ProductForm {

    private String code;
    private String name;
    private double price;
    private String productCategory;
    private String description;
    private String specification;

    private boolean newProduct = false;

    // Upload file.
    private MultipartFile fileData;

    public ProductForm() {
        this.newProduct = true;
    }

    public ProductForm(Product product, ProductDetails productDetails) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.productCategory = product.getProductCategory();
        this.description = productDetails.getDescription();
        this.specification = productDetails.getSpecification();
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
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

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
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
