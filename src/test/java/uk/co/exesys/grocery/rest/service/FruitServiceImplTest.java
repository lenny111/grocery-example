package uk.co.exesys.grocery.rest.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.exesys.grocery.rest.dao.FruitDao;
import uk.co.exesys.grocery.rest.dao.FruitEntity;
import uk.co.exesys.grocery.rest.errorhandling.AppException;
import uk.co.exesys.grocery.rest.resource.fruit.Fruit;
import uk.co.exesys.grocery.rest.service.FruitServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FruitServiceImplTest {

	private static final Long CREATED_FRUIT_RESOURCE_ID = Long.valueOf(1);
	private static final String SOME_NAME = "some_name";
	private static final double SOME_PRICE = 23.4;
	private static final int SOME_STOCK = 5;
	private static final String EXISTING_NAME = "banana";
	private static final Long SOME_ID = 13L;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();	

	FruitServiceImpl sut;//system under test
	
	@Mock
	FruitDao fruitDao;
	
	@Before
	public void setUp() throws Exception {		
		sut = new FruitServiceImpl();
		sut.setFruitDao(fruitDao);
	}

	@Test
	public void testCreateFruit_successful() throws AppException {
		
		when(fruitDao.getFruitByName(SOME_NAME)).thenReturn(null);		
		when(fruitDao.createFruit(any(FruitEntity.class))).thenReturn(CREATED_FRUIT_RESOURCE_ID);
		
		Fruit fruit = new Fruit();
		fruit.setName(SOME_NAME);
		fruit.setPrice(SOME_PRICE);
		fruit.setStock(SOME_STOCK);
		Long createFruit = sut.createFruit(fruit);
		
		verify(fruitDao).getFruitByName(SOME_NAME);//verifies if the method fruitDao.getFruitByName has been called exactly once with that exact input parameter
		verify(fruitDao, times(1)).getFruitByName(SOME_NAME);//same as above
		verify(fruitDao, times(1)).getFruitByName(eq(SOME_NAME));//same as above
		verify(fruitDao, times(1)).getFruitByName(anyString());//verifies if the method fruitDao.getFruitByName has been called exactly once with any string as input
		verify(fruitDao, atLeastOnce()).getFruitByName(SOME_NAME);//verifies if the method fruitDao.getFruitByName has been called at least once with that exact input parameter		
		verify(fruitDao, atLeast(1)).getFruitByName(SOME_NAME);//verifies if the method fruitDao.getFruitByName has been called at least once with that exact input parameter
		verify(fruitDao, times(1)).createFruit(any(FruitEntity.class));
		
		
		Assert.assertTrue(createFruit == CREATED_FRUIT_RESOURCE_ID);
	}

	@Test(expected=AppException.class)	
	public void testCreateFruit_error() throws AppException {
		
		FruitEntity existingFruit = new FruitEntity();
		when(fruitDao.getFruitByName(EXISTING_NAME)).thenReturn(existingFruit);			
		
		Fruit fruit = new Fruit();
		fruit.setName(EXISTING_NAME);
		fruit.setPrice(SOME_PRICE);
		fruit.setStock(SOME_STOCK);
		sut.createFruit(fruit);

	}
	
	@Test	
	public void testCreateFruit_validation_missingName() throws AppException {
		
		exception.expect(AppException.class);
		exception.expectMessage("Provided data not sufficient for insertion");
						
		sut.createFruit(new Fruit());

	}		
	
	

	@Test
	public void testUpdatePartiallyFruit_successful() throws AppException {
		
		FruitEntity fruitEntity = new FruitEntity();
		fruitEntity.setId(SOME_ID);
		when(fruitDao.getFruitById(SOME_ID)).thenReturn(fruitEntity);		
		doNothing().when(fruitDao).updateFruit(any(FruitEntity.class));
		
		Fruit fruit = new Fruit(fruitEntity);
		fruit.setName(SOME_NAME);
		fruit.setPrice(SOME_PRICE);
		fruit.setStock(SOME_STOCK);
		sut.updatePartiallyFruit(fruit);
		
		verify(fruitDao).getFruitById(SOME_ID);//verifies if the method fruitDao.getFruitById has been called exactly once with that exact input parameter
		verify(fruitDao).updateFruit(any(FruitEntity.class));		
		
		Assert.assertTrue(fruit.getName() == SOME_NAME);
		Assert.assertTrue(fruit.getPrice() == SOME_PRICE);
		Assert.assertTrue(fruit.getStock() == SOME_STOCK);
	}
	
	@Test
	public void testUpdatePartiallyFruit_not_existing_fruit() {
		
		when(fruitDao.getFruitById(SOME_ID)).thenReturn(null);
		
		Fruit fruit = new Fruit();
		fruit.setId(SOME_ID);
		try {
			sut.updatePartiallyFruit(fruit);
			Assert.fail("Should have thrown an exception"); 
		} catch (AppException e) {
			verify(fruitDao).getFruitById(SOME_ID);//verifies if the method fruitDao.getFruitById has been called exactly once with that exact input parameter
			Assert.assertEquals(e.getCode(), 404);
		}
		
	}	
	
}
