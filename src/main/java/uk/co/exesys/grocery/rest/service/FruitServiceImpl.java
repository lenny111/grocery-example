package uk.co.exesys.grocery.rest.service;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.co.exesys.grocery.rest.dao.FruitDao;
import uk.co.exesys.grocery.rest.dao.FruitEntity;
import uk.co.exesys.grocery.rest.errorhandling.AppException;
import uk.co.exesys.grocery.rest.helpers.NullAwareBeanUtilsBean;
import uk.co.exesys.grocery.rest.resource.fruit.Fruit;

public class FruitServiceImpl implements FruitService {

	@Autowired
	FruitDao fruitDao;
		
	/********************* Create related methods implementation ***********************/
	@Transactional("transactionManager")
	public Long createFruit(Fruit fruit) throws AppException {
		
		validateInputForCreation(fruit);
		
		//verify existence of resource in the db (feed must be unique)
		FruitEntity fruitByName = fruitDao.getFruitByName(fruit.getName());
		if(fruitByName != null){
			throw new AppException(Response.Status.CONFLICT.getStatusCode(), 409, "Fruit with name already existing in the database with the id " + fruitByName.getId(),
					"Please verify that the names are properly generated");
		}
		
		return fruitDao.createFruit(new FruitEntity(fruit));
	}

	private void validateInputForCreation(Fruit fruit) throws AppException {
		if(fruit.getName() == null){
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the name is properly generated/set");
		}
		//if(fruit.getStock() == null){
		//	throw new
		//}
		//TODO
		//etc...
	}
	
	@Transactional("transactionManager")
	public void createFruits(List<Fruit> fruits) throws AppException {
		for (Fruit fruit : fruits) {
			createFruit(fruit);
		}		
	}	
	
	
	 // ******************** Read related methods implementation **********************		
	public List<Fruit> getFruits(String orderByUpdateDate) throws AppException {
		
		if(isOrderByUpdateDateParameterValid(orderByUpdateDate)){
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Please set either ASC or DESC for the orderByUpdateDate parameter", null);
		}			
		List<FruitEntity> fruits = fruitDao.getFruits(orderByUpdateDate);
		
		return getFruitsFromEntities(fruits);
	}

	private boolean isOrderByUpdateDateParameterValid(
			String orderByUpdateDate) {
		return orderByUpdateDate!=null 
				&& !("ASC".equalsIgnoreCase(orderByUpdateDate) || "DESC".equalsIgnoreCase(orderByUpdateDate));
	}
	
	public Fruit getFruitByName(String name) throws AppException {
		
		//verify optional parameter numberDaysToLookBack first 
		if(name!=null){
			FruitEntity fruitByName = fruitDao.getFruitByName(name);
			if(fruitByName == null){
				throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
						404, 
						"The fruit you requested with name " + name + " was not found in the database",
						"Verify the existence of the fruit with the name " + name + " in the database");			
			}
			return new Fruit(fruitByName);			
		}
		else
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Please provide name", null);
		
	}
	
	public Fruit getFruitById(Long id) throws AppException {		
		FruitEntity fruitById = fruitDao.getFruitById(id);
		if(fruitById == null){
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
					404, 
					"The fruit you requested with id " + id + " was not found in the database",
					"Verify the existence of the fruit with the id " + id + " in the database");			
		}
		
		return new Fruit(fruitDao.getFruitById(id));
	}	

	private List<Fruit> getFruitsFromEntities(List<FruitEntity> fruitEntities) {
		List<Fruit> response = new ArrayList<Fruit>();
		for(FruitEntity fruitEntity : fruitEntities){
			response.add(new Fruit(fruitEntity));					
		}
		
		return response;
	}

	
	
	/********************* UPDATE-related methods implementation ***********************/	
	@Transactional("transactionManager")
	public void updateFullyFruit(Fruit fruit) throws AppException {
		//do a validation to verify FULL update with PUT
		if(isFullUpdate(fruit)){
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 
					400, 
					"Please specify all properties for Full UPDATE",
					"required properties - id, name, stock, price" );			
		}
		
		Fruit verifyFruitExistenceById = verifyFruitExistenceById(fruit.getId());
		if(verifyFruitExistenceById == null){
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
					404, 
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - " + fruit.getId());				
		}
				
		fruitDao.updateFruit(new FruitEntity(fruit));
	}

	/**
	 * Verifies the "completeness" of fruit resource sent over the wire
	 * 
	 * @param fruit
	 * @return
	 */
	private boolean isFullUpdate(Fruit fruit) {
		return fruit.getId() == null
				|| fruit.getName() == null;
	}
	
	/********************* DELETE-related methods implementation ***********************/
	@Transactional("transactionManager")
	public void deleteFruitById(Long id) {
		fruitDao.deleteFruitById(id);
	}
	
	@Transactional("transactionManager")	
	public void deleteFruits() {
		fruitDao.deleteFruits();		
	}

	public Fruit verifyFruitExistenceById(Long id) {
		FruitEntity fruitById = fruitDao.getFruitById(id);
		if(fruitById == null){
			return null;
		} else {
			return new Fruit(fruitById);			
		}
	}

	@Transactional("transactionManager")
	public void updatePartiallyFruit(Fruit fruit) throws AppException {
		//do a validation to verify existence of the resource		
		Fruit verifyFruitExistenceById = verifyFruitExistenceById(fruit.getId());
		if(verifyFruitExistenceById == null){
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
					404, 
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - " + fruit.getId());				
		}
		copyPartialProperties(verifyFruitExistenceById, fruit);		
		fruitDao.updateFruit(new FruitEntity(verifyFruitExistenceById));
		
	}

	
	
	private void copyPartialProperties(Fruit verifyFruitExistenceById,
						Fruit fruit) {
		
		BeanUtilsBean notNull=new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyFruitExistenceById, fruit);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	

	public void setFruitDao(FruitDao fruitDao) {
		this.fruitDao = fruitDao;
	}
		
}
