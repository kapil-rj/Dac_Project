/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

//@Data
@Entity
@Table(name = "Product_Details")
public class ProductDetails implements Serializable {

    @Id
    @Column(name = "ID", length = 20, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "SPECIFICATION", nullable = false)
    private String specification;

    @Column(name = "PRODUCT_ID", length = 20, nullable = false)
    private String product;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PRODUCT_ID", nullable = false, //
//            foreignKey = @ForeignKey(name = "Product_Details_PROD_FK"))
//    private Product product;

    public ProductDetails() {
    }

    public ProductDetails(String id, String description, String specification, String product) {
        this.id = id;
        this.description = description;
        this.specification = specification;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductDetails{" + "id=" + id + ", description=" + description + ", specification=" + specification + ", product=" + product + '}';
    }

}
