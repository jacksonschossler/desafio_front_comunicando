package br.com.agenda.common.application.restful.jackson.mixins;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author rodrigo
 */
public abstract class OrderMixin
{
	/**
	 * 
	 */
	@JsonCreator
	public OrderMixin( @JsonProperty("direction") Direction direction, @JsonProperty("property") String property, @JsonProperty("nullHandlingHint") NullHandling nullHandlingHint )
	{
	}
}