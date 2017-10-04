package br.com.agenda.common.infrastructure.hibernate.functions;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

/**
 * 
 * @author rodrigo@eits.com.br
 */
public class PostgreSQLFilterFunction implements SQLFunction 
{
	/**
	 * 
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException 
	{
		try 
		{
			final String field = (String) arguments.get(0);
			final String value = (String) arguments.get(1);
			
			final String regex = "[*?|@#$%¨&()\\[\\]+=\\-\\.\\,/\\\\]";
			
			final String fragment = "lower(unaccent(cast("+field+" as text))) "
					   			  + "similar to lower( '%'|| array_to_string( regexp_split_to_array( coalesce( regexp_replace(unaccent(cast("+value+" as text)), '"+regex+"', '\\\\\\&', 'g') , ''), ','), '%|%') ||'%' )";
			return fragment;
		} 
		catch (IndexOutOfBoundsException e) 
		{
			throw new IllegalArgumentException("The function must be passed 2 arguments");
		}
	}

	/**
	 * 
	 */
	@Override
	public Type getReturnType(Type columnType, Mapping mapping) throws QueryException 
	{
		return new BooleanType();
	}

	/**
	 * 
	 */
	@Override
	public boolean hasArguments() 
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	public boolean hasParenthesesIfNoArguments() 
	{
		return false;
	}
}