package br.com.agenda.common.application.restful.jackson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author rodrigo
 */
@Provider
public class PageRequestParamConverterProvider implements ParamConverterProvider
{
	/**
	 * 
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter( final Class<T> rawType, final Type genericType, final Annotation[] annotations )
	{
		if ( rawType.getName().equals( PageRequest.class.getName() ) )
		{
			return ( ParamConverter<T> ) new PageRequestParamConverter();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @author rodrigo
	 */
	private static class PageRequestParamConverter implements ParamConverter<PageRequest>
	{
		/**
		 * 
		 */
		@Override
		@SuppressWarnings("unchecked")
		public PageRequest fromString( String value )
		{
			if ( value == null ) return null;

			try
			{
				//{"sort":null,"pageNumber":0,"pageSize":100}
				final Map<String, Object> pageable = MAPPER.readValue( value, Map.class );
				
				//just page and size?
				if ( pageable.containsKey("sort") && pageable.get("sort") != null )
				{
					final Map<String, Object> sortMap = ( Map<String, Object> ) pageable.get("sort");
					final List<Map<String, Object>> ordersMap = ( List<Map<String, Object>> ) sortMap.get( "orders" );
					final Map<String, Object> orderMap = ordersMap.get(0);//apenas o primeiro oders;
					
					
					final String direction = ( String ) orderMap.get( "direction" );
					final String property = ( String ) orderMap.get( "property" );
					
					final Sort sort = new Sort( new Order(Direction.fromString(direction), property) );
					return new PageRequest( (Integer) pageable.get("pageNumber"), (Integer) pageable.get("pageSize"), sort );
				}
				else if ( pageable.containsKey("pageNumber") && pageable.containsKey("pageSize") )
				{
					return new PageRequest( (Integer) pageable.get("pageNumber"), (Integer) pageable.get("pageSize") );
				}
				else
				{
					throw new IllegalArgumentException("The current pageable converter supports only page and size and/or with sort. Using null for now.");
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * 
		 */
		@Override
		public String toString( PageRequest value )
		{
			if ( value == null ) return null;
			
			try
			{
				return MAPPER.writeValueAsString( value );
			}
			catch ( JsonProcessingException e )
			{
				e.printStackTrace();
				return null;
			}
		}
	}

}