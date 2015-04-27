package uk.co.exesys.grocery.rest.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class FruitDaoJPA2Impl implements FruitDao {

	@PersistenceContext(unitName="groceryPersistence")
	private EntityManager entityManager;
	
	public List<FruitEntity> getFruits(String orderByUpdateDate) {
		String sqlString = null;
		if(orderByUpdateDate != null){
			sqlString = "SELECT p FROM FruitEntity p" + " ORDER BY p.updated " + orderByUpdateDate;
		} else {
			sqlString = "SELECT p FROM FruitEntity p";
		}		 
		TypedQuery<FruitEntity> query = entityManager.createQuery(sqlString, FruitEntity.class);		

		return query.getResultList();
	}


	
	public FruitEntity getFruitById(Long id) {
		
		try {
			String qlString = "SELECT p FROM FruitEntity p WHERE p.id = ?1";
			TypedQuery<FruitEntity> query = entityManager.createQuery(qlString, FruitEntity.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public FruitEntity getFruitByName(String name) {
		
		try {
			String qlString = "SELECT p FROM FruitEntity p WHERE p.name = ?1";
			TypedQuery<FruitEntity> query = entityManager.createQuery(qlString, FruitEntity.class);		
			query.setParameter(1, name);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	

	public void deleteFruitById(Long id) {
		
		FruitEntity fruit = entityManager.find(FruitEntity.class, id);
		entityManager.remove(fruit);
		
	}

	public Long createFruit(FruitEntity fruit) {
		
		fruit.setUpdated(new Date());
		entityManager.merge(fruit);
		entityManager.flush();//force insert to receive the id of the fruit
		
		return fruit.getId();
	}

	public void updateFruit(FruitEntity fruit) {
		//TODO think about partial update and full update 
		fruit.setUpdated(new Date());
		entityManager.merge(fruit);		
	}
	
	public void deleteFruits() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE fruit");		
		query.executeUpdate();
	}


}
