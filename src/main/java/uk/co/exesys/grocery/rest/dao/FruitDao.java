package uk.co.exesys.grocery.rest.dao;

import java.util.List;

/**
 * 
 * @author rl
 * 
 */
public interface FruitDao {
	
	public List<FruitEntity> getFruits(String orderByUpdateDate);
	
	/**
	 * Returns a fruit given its id
	 * 
	 * @param id
	 * @return
	 */
	public FruitEntity getFruitById(Long id);
	
	/**
	 * Find fruit by name
	 * 
	 * @param name
	 * @return the fruit with the specified name or null if not existent 
	 */
	public FruitEntity getFruitByName(String name);	

	public void deleteFruitById(Long id);

	public Long createFruit(FruitEntity fruit);

	public void updateFruit(FruitEntity fruit);

	/** removes all fruits */
	public void deleteFruits();

	
}
