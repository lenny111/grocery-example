package uk.co.exesys.grocery.rest;


import org.glassfish.jersey.server.ResourceConfig;

import uk.co.exesys.grocery.rest.errorhandling.AppExceptionMapper;
import uk.co.exesys.grocery.rest.errorhandling.GenericExceptionMapper;
import uk.co.exesys.grocery.rest.errorhandling.NotFoundExceptionMapper;
import uk.co.exesys.grocery.rest.filters.LoggingResponseFilter;
import uk.co.exesys.grocery.rest.resource.fruit.FruitsResource;

/**
 * Registers the components to be used by the JAX-RS application
 * 
 * @author rl
 * 
 */
public class GroceryJaxRsApplication extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public GroceryJaxRsApplication() {
		
        packages("uk.co.exesys.grocery.rest");
        
//		// register application resources
		register(FruitsResource.class);
//
//		// register filters

		register(LoggingResponseFilter.class);
//
//		// register exception mappers
		register(GenericExceptionMapper.class);
		register(AppExceptionMapper.class);
		register(NotFoundExceptionMapper.class);
	}
}
