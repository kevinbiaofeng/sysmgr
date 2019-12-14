package com.snake.mcf.common.cache.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	
	@Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWaitMillis;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.jedis.pool.max-total}")
    private int maxTotal;

    @Value("${spring.redis.jedis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.redis.jedis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    @Value("${spring.redis.timeout}")
    private int timeout;
	
    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setLifo(true);

        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        jedisPoolConfig.setBlockWhenExhausted(false);
        jedisPoolConfig.setJmxEnabled(true);

        // 检查连接是否可用
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        log.info("jedisPool配置完成");
        return jedisPoolConfig;
    }
    
    @Bean
	public JedisPool getJedisPool() {
		JedisPool jedisPool=new JedisPool(getJedisPoolConfig(), host, port, timeout, password);
		log.info("jedisPool配置完成");
		return jedisPool;
	}

    /**
     * 连接工厂
     * @return
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        jpcb.poolConfig(getJedisPoolConfig());
        jpcb.build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        log.info("jedis连接工厂加载");
        return jedisConnectionFactory;
    }

    /**
     * 自定义key生成器
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target,method,params)->{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(target.getClass().getName());
            stringBuilder.append(":");
            stringBuilder.append(method.getName());
            for(Object object : params){
                stringBuilder.append(":"+String.valueOf(object));
            }
            String result = String.valueOf(stringBuilder);
            log.info("自定义的key为：" + result);
            return result;
        };
    }

    /**
     * 自定义序列化
     * @param
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
    	RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        log.info("redisTemplate配置完成");
        return redisTemplate;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Bean
    public Jackson2JsonRedisSerializer<String> jackson2JsonRedisSerializer() {
        final Jackson2JsonRedisSerializer<String> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
                Object.class);
        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
    
    /**
     * 缓存错误处理器
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
                log.info("redis 缓存 查找 失败 ! 错误原因："+e+"key："+o);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
                log.info("redis 缓存 更新 失败 ! 错误原因："+e+"key："+o);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
                log.info("redis 缓存 删除 失败 ! 错误原因："+e+"key："+o);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.info("redis 缓存 清除 失败 ! 错误原因："+e);
            }
        };
        return cacheErrorHandler;
    }

    /**
     * 缓存管理器
     * @return
     */
    @Bean
    @Override
    public RedisCacheManager cacheManager() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        RedisCacheManager cacheManager = RedisCacheManager.builder(getJedisConnectionFactory())
        		.cacheDefaults(redisCacheConfiguration)
                .build();
        log.info("redis缓存管理器加载");
        return cacheManager;
    }

}
