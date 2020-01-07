package com.michaelcgood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.michaelcgood.queue.MessagePublisher;
import com.michaelcgood.queue.MessagePublisherImpl;
import com.michaelcgood.queue.MessageSubscriber;

@Configuration
@ComponentScan("com.michaelcgood")
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
    	JedisConnectionFactory connection1 = new JedisConnectionFactory();
    	connection1.setHostName("redis-12288.c99.us-east-1-4.ec2.cloud.redislabs.com");
    	connection1.setPort(12288);
    	connection1.setPassword("KjolpHb3qqFycAThFw34gvmaDEjjRuiT");
        return connection1;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
}