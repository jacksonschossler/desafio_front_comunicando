package br.com.agenda.common.application.restful.jackson.mixins;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author rodrigo
 */
public abstract class PageImplMixin
{
	/**
	 * 
	 */
	@JsonCreator
	public PageImplMixin( @JsonProperty("content") List<?> content, @JsonProperty(value="pageable") Pageable pageable, @JsonProperty("totalElements") long total )
	{
	}
}