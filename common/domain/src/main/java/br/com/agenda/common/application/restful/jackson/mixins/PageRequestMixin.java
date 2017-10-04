package br.com.agenda.common.application.restful.jackson.mixins;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author rodrigo
 */
public abstract class PageRequestMixin
{
	/**
	 * 
	 */
	@JsonCreator
	public PageRequestMixin( @JsonProperty("page") int page, @JsonProperty(value="size") int size, @JsonProperty("sort") Sort sort )
	{
	}
}