package br.com.agenda.common.application.configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

import br.com.agenda.common.application.restful.feign.JAXRSExpanderContract;
import br.com.agenda.common.application.restful.jackson.PageRequestParamConverterProvider;
import br.com.agenda.common.application.restful.jackson.mixins.OrderMixin;
import br.com.agenda.common.application.restful.jackson.mixins.PageImplMixin;
import br.com.agenda.common.application.restful.jackson.mixins.PageMixin;
import br.com.agenda.common.application.restful.jackson.mixins.PageRequestMixin;
import br.com.agenda.common.application.restful.jackson.mixins.PageableMixin;
import br.com.agenda.common.application.restful.jackson.mixins.SortMixin;
import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Contract;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author rodrigo.p.fraga
 */
@Component
@ApplicationPath("/api")
public class CommonRestfulConfiguration extends ResourceConfig implements ResourceConfigCustomizer, Jackson2ObjectMapperBuilderCustomizer
{
	/**
	 * 
	 */
	@Autowired
    private ApplicationContext applicationContext;
	
	/*-------------------------------------------------------------------
	 * 		 						OVERRIDES
	 *-------------------------------------------------------------------*/
    /* (non-Javadoc)
	 * @see org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer#customize(org.glassfish.jersey.server.ResourceConfig)
	 */
	@Override
	public void customize( ResourceConfig resourceConfig )
	{
		resourceConfig.register( PageRequestParamConverterProvider.class );
		
		this.applicationContext.getBeansWithAnnotation(Path.class)
			.forEach( (name, resource) -> {
				resourceConfig.register(resource);
			}
		);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer#customize(org.springframework.http.converter.json.Jackson2ObjectMapperBuilder)
	 */
	@Override
	public void customize( Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder )
	{
		jacksonObjectMapperBuilder
			.mixIn( Page.class, PageMixin.class )
			.mixIn( PageImpl.class, PageImplMixin.class )
			.mixIn( Pageable.class, PageableMixin.class )
			.mixIn( PageRequest.class, PageRequestMixin.class )
			.mixIn( Sort.class, SortMixin.class )
			.mixIn( Order.class, OrderMixin.class )
			.featuresToDisable( 
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				SerializationFeature.WRITE_DATES_AS_TIMESTAMPS 
			)
			.defaultTyping(
				new DefaultTypeResolverBuilder( ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT )
					.init( JsonTypeInfo.Id.CLASS, null )
					.inclusion( JsonTypeInfo.As.PROPERTY )
					.typeProperty( "@type" )
			)
			.modulesToInstall( 
				new Hibernate5Module(), 
				new Jdk8Module(), 
				new JavaTimeModule(),
				new JtsModule()
			);
	}
	
	/**
	 * 
	 * @author rodrigo.p.fraga
	 */
	@Configuration
	public static class FeignConfiguration 
	{
		/**
		 * 
		 * @return
		 */
	    @Bean
	    public Contract feignContract() 
	    {
	        return new JAXRSExpanderContract();
	    }
	    
	    /**
	     * 
	     * @return
	     */
	    @Bean
	    public RequestInterceptor rootURLRequestInterceptor()
	    {
	    	return template -> {
				template.insert( 0, "/api" );//concat "/api" for all url clients
			};
	    }
	}
}
