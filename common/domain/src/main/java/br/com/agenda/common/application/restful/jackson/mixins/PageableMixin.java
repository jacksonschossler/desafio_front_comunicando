package br.com.agenda.common.application.restful.jackson.mixins;

import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 * @author rodrigo
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "@type"
)
@JsonSubTypes({
	@Type(value = PageRequest.class, name = "PageRequest") 
})
public abstract class PageableMixin
{
}