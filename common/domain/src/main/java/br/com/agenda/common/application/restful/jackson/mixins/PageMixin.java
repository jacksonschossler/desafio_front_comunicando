package br.com.agenda.common.application.restful.jackson.mixins;

import org.springframework.data.domain.PageImpl;

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
	@Type(value = PageImpl.class, name = "PageImpl") 
})
public abstract class PageMixin
{
}