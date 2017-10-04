package br.com.agenda.common.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by eduardo 
 */
@Configuration
@EnableCaching
public class CommonCacheConfiguration
{
	/**
	 *
	 */
	@Configuration
	@Profile("!development")
	public static class DistributedCacheConfiguration extends CachingConfigurerSupport
	{
		/**
		 *
		 */
		@Autowired
		private ObjectMapper objectMapper;
		/**
		 * 
		 */
		@Value("${cache.redis.host}") 
		private String redisHost;
		/**
		 * 
		 */
		@Value("${cache.redis.port}") 
		private int redisPort;
		
		/*-------------------------------------------------------------------
		 * 		 						BEANS
		 *-------------------------------------------------------------------*/

		/**
		 * @return
		 */
		@Bean
		public RedisTemplate<String, String> redisTemplate()
		{
			final RedisTemplate<String, String> template = new RedisTemplate<>();
			template.setKeySerializer( new GenericJackson2JsonRedisSerializer( this.objectMapper ) );
			template.setValueSerializer( new JdkSerializationRedisSerializer() );
			template.setConnectionFactory( this.jedisConnectionFactory() );
			return template;
		}
		
		/***
		 * 
		 * @return
		 */
		@Bean
		public JedisConnectionFactory jedisConnectionFactory()
		{
			final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
			jedisConnectionFactory.setHostName( this.redisHost );
			jedisConnectionFactory.setPort( this.redisPort );
			return jedisConnectionFactory;
		}

		/*-------------------------------------------------------------------
		 * 		 						OVERRIDES
		 *-------------------------------------------------------------------*/
		/**
		 * 
		 */
		@Bean
		@Override
		public CacheManager cacheManager()
		{
			RedisCacheManager redisCacheManager = new RedisCacheManager( this.redisTemplate() );
			redisCacheManager.setUsePrefix( true );
			redisCacheManager.setCachePrefix( String::getBytes );
			return redisCacheManager;
		}
		
		/**
		 * 
		 */
		@Override
		public CacheErrorHandler errorHandler() 
		{
			return new CacheErrorHandler()
			{
				@Override
				public void handleCacheClearError(RuntimeException ex, Cache cache) 
				{
					ex.printStackTrace();
				}
				@Override
				public void handleCacheEvictError(RuntimeException ex, Cache cache, Object key) 
				{
					ex.printStackTrace();
				}
				@Override
				public void handleCacheGetError(RuntimeException ex, Cache cache, Object key) 
				{
					ex.printStackTrace();
				}
				@Override
				public void handleCachePutError(RuntimeException ex, Cache cache, Object arg1, Object arg2) 
				{
					ex.printStackTrace();
				}
			};
		}
	}

	/**
	 * 
	 */
	@Configuration
	@Profile("development")
	public static class LocalCacheConfiguration extends CachingConfigurerSupport
	{
		/*-------------------------------------------------------------------
		 * 		 						OVERRIDES
		 *-------------------------------------------------------------------*/
		/**
		 * 
		 */
		@Bean
		@Override
		public CacheManager cacheManager()
		{
			return new ConcurrentMapCacheManager();
		}
	}
}
