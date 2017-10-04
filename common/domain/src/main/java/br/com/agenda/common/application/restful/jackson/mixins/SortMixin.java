package br.com.agenda.common.application.restful.jackson.mixins;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author rodrigo
 */
public abstract class SortMixin
{
	/**
	 * 
	 */
	@JsonCreator
	public SortMixin( @JsonProperty("orders") List<Order> orders )
	{
	}
}