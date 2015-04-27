package uk.co.exesys.grocery.rest.service;


import java.util.List;

import uk.co.exesys.grocery.rest.errorhandling.AppException;
import uk.co.exesys.grocery.rest.resource.fruit.Fruit;


/**
 * 
 * @author rl
 * 
 */
public interface FruitService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	public Long createFruit(Fruit fruit) throws AppException;
	public void createFruits(List<Fruit> fruits) throws AppException;

		
	/*
	 ******************** Read related methods ********************
	  */ 	
	/**
	 * 
	 * @param orderByUpdateDate - if set, it represents the order by criteria (ASC or DESC) for displaying fruits
	 * @return list with fruits corresponding to search criterias
	 * @throws AppException
	 */
	public List<Fruit> getFruits(String orderByUpdateDate) throws AppException;
	
	/**
	 * Returns a fruit given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public Fruit getFruitById(Long id) throws AppException;
	
	
	public Fruit getFruitByName(String name) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 * */	
	public void updateFullyFruit(Fruit fruit) throws AppException;
	public void updatePartiallyFruit(Fruit fruit) throws AppException;	
	
	
	/*
	 * ******************** Delete related methods **********************
	 * */
	public void deleteFruitById(Long id);
	
	/** removes all fruits */
	public void deleteFruits();

	/*
	 * ******************** Helper methods **********************
	 * */
	public Fruit verifyFruitExistenceById(Long id);
	

}
