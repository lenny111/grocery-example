package uk.co.exesys.grocery.rest.resource.fruit;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.exesys.grocery.rest.errorhandling.AppException;
import uk.co.exesys.grocery.rest.service.FruitService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author rl
 * 
 */
@Component
@Path("/fruit")
public class FruitsResource {

	@Autowired
	private FruitService fruitService;

	/*
	 * *********************************** CREATE ***********************************
	 */

	/**
	 * Adds a new resource (fruit) from the given json format 
	 * 
	 * @param fruit
	 * @return
	 * @throws AppException
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createFruit(Fruit fruit) throws AppException {
		Long createFruitId = fruitService.createFruit(fruit);
		return Response.status(Response.Status.CREATED)// 201
				.entity("A new fruit has been created")
				.header("Location",
						"http://localhost:8888/grocery/fruits/"
								+ String.valueOf(createFruitId)).build();
	}



	/**
	 * A list of resources (here fruits) provided in json format will be added
	 * to the database.
	 * 
	 * @param fruits
	 * @return
	 * @throws AppException
	 */
	@POST
	@Path("load")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createFruits(List<Fruit> fruits) throws AppException {
		//first delete all fruit
		fruitService.deleteFruits();
		//then add fruits
		fruitService.createFruits(fruits);
		return Response.status(Response.Status.CREATED) // 201
				.entity("Fruits were successfully created").build();
	}

	/*
	 * *********************************** READ ***********************************
	 */
	/**
	 * Returns all resources (fruits) from the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws AppException
	 */
	@GET 
	@Produces({ MediaType.APPLICATION_JSON }) //, MediaType.APPLICATION_XML })
	public List<Fruit> getFruits(
			@QueryParam("orderByUpdateDate") String orderByUpdateDate)
			throws IOException,	AppException {
		List<Fruit> fruits = fruitService.getFruits(
				orderByUpdateDate);
		return fruits;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON }) //, MediaType.APPLICATION_XML })
	public Response getFruitById(@PathParam("id") Long id, @QueryParam("detailed") boolean detailed)
			throws IOException,	AppException {
		Fruit fruitById = fruitService.getFruitById(id);
		return Response.status(200)
				.entity(fruitById, detailed ? new Annotation[]{FruitDetailedView.Factory.get()} : new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}
	
	@GET
	@Path("/fruitByName")
	@Produces({ MediaType.APPLICATION_JSON }) 
	public Response getFruitByName(@QueryParam("name") String name, @QueryParam("detailed") boolean detailed)
			throws IOException,	AppException {
		Fruit fruitByName = fruitService.getFruitByName(name);
		return Response.status(200)
				.entity(fruitByName, detailed ? new Annotation[]{FruitDetailedView.Factory.get()} : new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}
	
	/*
	 * *********************************** UPDATE ***********************************
	 */

	/**
	 * The method offers both Creation and Update resource functionality. If
	 * there is no resource yet at the specified location, then a fruit
	 * creation is executed and if there is then the resource will be full
	 * updated.
	 * 
	 * @param id
	 * @param fruit
	 * @return
	 * @throws AppException
	 */
	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putFruitById(@PathParam("id") Long id, Fruit fruit)
			throws AppException {

		Fruit fruitById = fruitService.verifyFruitExistenceById(id);

		if (fruitById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createFruitId = fruitService.createFruit(fruit);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new fruit has been created AT THE LOCATION you specified")
					.header("Location",
							"http://localhost:8888/grocery/fruits/"
									+ String.valueOf(createFruitId)).build();
		} else {
			// resource is existent and a full update should occur
			fruitService.updateFullyFruit(fruit);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The fruit you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://localhost:8888/grocery/fruits/"
									+ String.valueOf(id)).build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateFruit(@PathParam("id") Long id,
			Fruit fruit) throws AppException {
		fruit.setId(id);
		fruitService.updatePartiallyFruit(fruit);
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("The fruit you specified has been successfully updated")
				.build();
	}


	
	
	/*
	 * *********************************** DELETE ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteFruitById(@PathParam("id") Long id) {
		fruitService.deleteFruitById(id);
		return Response.status(Response.Status.NO_CONTENT)// 204
				.entity("Fruit successfully removed from database").build();
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteFruits() {
		fruitService.deleteFruits();
		return Response.status(Response.Status.NO_CONTENT)// 204
				.entity("All fruits have been successfully removed").build();
	}

	public void setfruitService(FruitService fruitService) {
		this.fruitService = fruitService;
	}

}
