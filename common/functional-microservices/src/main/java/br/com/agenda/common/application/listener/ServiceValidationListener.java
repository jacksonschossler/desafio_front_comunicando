package br.com.agenda.common.application.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.aop.TargetClassAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eduardo
 */
//@Component
public class ServiceValidationListener implements ApplicationListener<ContextRefreshedEvent>
{
	/**
	 * 
	 */
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( ServiceValidationListener.class );
 
	/*-------------------------------------------------------------------
	 * 		 					ATTRIBUTES
	 *-------------------------------------------------------------------*/
    /**
     * 
     */
    @Autowired
    private ApplicationContext applicationContext;

	/*-------------------------------------------------------------------
	 * 		 					BEHAVIORS
	 *-------------------------------------------------------------------*/
    /**
     * 
     */
    @Override
    public void onApplicationEvent( ContextRefreshedEvent event )
    {
        Map<String, Object> services = this.applicationContext.getBeansWithAnnotation( Service.class );
        ArrayList<String> errors = new ArrayList<>();
        services.forEach( ( name, service ) ->
        {
            LOG.debug( "Verificando serviço " + name );
            
            Class<?> serviceClass = service instanceof TargetClassAware ? ((TargetClassAware) service).getTargetClass() : service.getClass();
            for ( Method method : serviceClass.getMethods() )
            {
                LOG.debug( "Verificando método " + method.getName() );
                
                if ( method.getName().matches( "^find.+ById$" ) )
                {
                    String cacheRegionName = method.getReturnType().getSimpleName().toLowerCase();
                    // find*ById deve ser @Transactional(readOnly = true)
                    // e ter @Cacheable com cacheName == cacheRegionName
                    checkTransactionalReadOnly( errors, name, method );
                    Cacheable cacheableAnnotation = method.getAnnotation( Cacheable.class );
                    if ( !(cacheableAnnotation != null &&
                           cacheRegionName.equals( cacheableAnnotation.value()[0] )) )
                    {
                        errors.add( "O método " + name + "." + method.getName() + " não possui a anotação @Cacheable(\"" + cacheRegionName + "\")" );
                    }
                }
                else if ( method.getName().matches( "^list.+ByFilters$" ) )
                {
                    checkTransactionalReadOnly( errors, name, method );
                }
                else if ( method.getName().startsWith( "update" ) || method.getName().startsWith( "insert" ) )
                {
                    final String cacheRegionName = method.getReturnType().getSimpleName().toLowerCase();
                    final CachePut cachePutAnnotation = method.getAnnotation( CachePut.class );
                    
                    if ( !(cachePutAnnotation != null && cacheRegionName.equals( cachePutAnnotation.value()[0] ) &&
                           "#result.id".equals( cachePutAnnotation.key() )) )
                    {
                        errors.add( "O método " + name + "." + method.getName() + " não possui a anotação @CachePut(value = \"" + cacheRegionName +"\", key = \"#result.id\")" );
                    }
                }
                else if ( method.getName().startsWith( "remove" ) )
                {
                    if ( method.getName().endsWith( "ById" ) )
                    {
                        errors.add( "O nome do método " + name + "." + method.getName() + " não deve terminar com \"ById\"." );
                    }
                    
                    final String cacheRegionName = method.getName().replaceFirst( "^remove", "" ).toLowerCase();
                    final CacheEvict cacheEvictAnnotation = method.getAnnotation( CacheEvict.class );
                    
                    if ( cacheEvictAnnotation == null || !cacheRegionName.equals(cacheEvictAnnotation.value()[0]) )
                    {
                        errors.add( "O método " + name + "." + method.getName() + " não possui a anotação @CacheEvict(\"" + cacheRegionName +"\")" );
                    }
                }
            }
        } );
        if ( !errors.isEmpty() )
        {
            LOG.error( " " );
            LOG.error( " " );
            LOG.error( " " );
            LOG.error( "Os seguintes erros de validação dos serviços foram encontrados:" );
            errors.forEach( error -> LOG.error( "* " + error ) );
            LOG.error( " " );
            LOG.error( " " );
            LOG.error( " " );
//            throw new IllegalStateException( "Houveram erros de validação dos serviços." );
        }
    }

    /**
     * 
     */
    private void checkTransactionalReadOnly( ArrayList<String> errors, String name, Method method )
    {
        Transactional transactionAnnotation = method.getAnnotation( Transactional.class );
        if ( !(transactionAnnotation != null && transactionAnnotation.readOnly()) )
        {
            errors.add( "O método " + name + "." + method.getName() + " não possui a anotação @Transactional(readOnly = true)" );
        }
    }
}
