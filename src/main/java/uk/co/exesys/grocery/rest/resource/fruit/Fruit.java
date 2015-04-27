package uk.co.exesys.grocery.rest.resource.fruit;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.beanutils.BeanUtils;

import uk.co.exesys.grocery.rest.dao.FruitEntity;
import uk.co.exesys.grocery.rest.helpers.DateISO8601Adapter;

/**
 * Fruit resource placeholder for json/xml representation 
 * 
 * @author rl
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Fruit implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the fruit */
	@XmlElement(name = "id")	
	private Long id;
	
	/** name of the fruit */
	@XmlElement(name = "name")	
	private String name;
		
	/** price of the fruit */
	@XmlElement(name = "price")	
	private double price;
	
	/** stock qty of the fruit */
	@XmlElement(name = "stock")	
	private int stock;
		
	/** updated date in the database */
	@XmlElement(name = "updated")
	@XmlJavaTypeAdapter(DateISO8601Adapter.class)	
	@FruitDetailedView
	private Date updated;

	public Fruit(FruitEntity fruitEntity){
		try {
			BeanUtils.copyProperties(this, fruitEntity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Fruit(String name, double price, int stock ) {
		
		this.name = name;
		this.price = price;
		this.stock = stock;
		
	}
	
	public Fruit(){}
		
	
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
