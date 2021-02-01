/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;


@Entity
@Table(name = "Product_Category")
public class ProductCategory implements Serializable {

    @Id
    @Column(name = "ID", length = 20, nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "NAME", length = 255, nullable = false)
    private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}
