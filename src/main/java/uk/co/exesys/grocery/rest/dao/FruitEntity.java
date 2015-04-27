package uk.co.exesys.grocery.rest.dao;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import uk.co.exesys.grocery.rest.resource.fruit.Fruit;

/**
 * Fruit entity 
 * 
 * @author rl
 *
 */
@Entity
@Table(name="fruit")
public class FruitEntity implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the fruit */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	/** name of the fruit */
	@Column(name="name")
	private String name;
		
	/** price of the fruit */
	@Column(name="price")
	private double price;
	
	/** stock qty of the fruit */
	@Column(name="stock")
	private int stock;
			
	/** updated date in the database */
	@Column(name="updated")
	private Date updated;

	public FruitEntity(){}
	
	public FruitEntity(String name, double price, int stock ) {
		
		this.name = name;
		this.price = price;
		this.stock = stock;
		
	}
	
	public FruitEntity(Fruit fruit){
		try {
			BeanUtils.copyProperties(this, fruit);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


		
}
