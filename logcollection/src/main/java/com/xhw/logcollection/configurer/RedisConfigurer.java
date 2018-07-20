package com.xhw.logcollection.configurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis缓存配置类
 * @Author rentie
 * @Date 2017/10/14 10:11
 * 使用说明
 * 在Mapper对应方法上添加如下注释即可
    @Cacheable将查询结果缓存到redis中，（key="#p0"）指定传入的第一个参数作为redis的key。

　　@CachePut，指定key，将更新的结果同步到redis中

　　@CacheEvict，指定key，删除缓存数据，allEntries=true,方法调用后将立即清除缓存
 */
@Configuration
@EnableCaching
public class RedisConfigurer extends CachingConfigurerSupport {

    Logger logger = LoggerFactory.getLogger(RedisConfigurer.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    //注入JedisPool连接池
    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);

        return jedisPool;
    }


    //自定义缓存key生成策略
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator(){
           @Override
            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
               StringBuffer sb = new StringBuffer();
               sb.append(target.getClass().getName());
               sb.append(method.getName());
               for(Object obj:params){
                   sb.append(obj.toString());
               }
               return sb.toString();
           }
       };
   }
        //缓存管理器
        @Bean
        public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
            RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
            //设置缓存过期时间3天  单位秒
            cacheManager.setDefaultExpiration(259200);
            return cacheManager;
        }

        //缓存模板
        @Bean
        public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
            StringRedisTemplate template = new StringRedisTemplate(factory);
            setSerializer(template);//设置序列化工具
            template.afterPropertiesSet();
            return template;
        }

        //设置序列化工具，用JacksonJsonRedisSerializer序列化的话，被序列化的对象不用实现Serializable接口
        private void setSerializer(StringRedisTemplate template){
            @SuppressWarnings({ "rawtypes", "unchecked" })
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(om);
            template.setValueSerializer(jackson2JsonRedisSerializer);
        }
    }

