package br.com.agenda.common.application.restful.feign;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;

import feign.Param.Expander;

/**
 * 
 * @author rodrigo.p.fraga
 */
public class PageRequestExpander implements Expander
{
	/**
	 * 
	 */
	private String QUERY_STRING = "{\"pageNumber\":%d, \"pageSize\":%d, \"sort\":null}";
	
	/**
	 * 
	 */
	private String QUERY_WITH_SORT_STRING = "{\"pageNumber\":%d, \"pageSize\":%d, \"sort\":{\"orders\":[{\"direction\":\"%s\", \"property\":\"%s\", \"nullHandlingHint\":null}]}}";

	/**
	 * 
	 */
	@Override
	public String expand( Object value )
	{
		if ( value == null ) return null;
		if ( !(value instanceof PageRequest) ) return value.toString();
			
		final PageRequest pageRequest = (PageRequest) value;
		
		if ( pageRequest.getSort() != null )
		{
			final Order order = pageRequest.getSort().iterator().next();
			
			return String.format( QUERY_WITH_SORT_STRING, 
					pageRequest.getPageNumber(), 
					pageRequest.getPageSize(), 
					order.getDirection(), order.getProperty() 
				);
		}
		else
		{
			return String.format( QUERY_STRING, pageRequest.getPageNumber(), pageRequest.getPageSize());
		}
	}

}