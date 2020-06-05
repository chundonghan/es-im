package com.es.es_im.common.config.mongodb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

@Configuration
@PropertySource(value = "classpath:mongodb.properties")
public class MongoDBConfig {
	
    @Value("${mongo.host}")
    private String host;
    @Value("${mongo.port}")
    private int port;
    @Value("${mongo.dbname}")
    private String dbname;
    @Value("${mongo.connectionsPerHost}")
    private int connectionsPerHost;
    @Value("${mongo.threadsAllowedToBlockForConnectionMultiplier}")
    private int multiplier;
    @Value("${mongo.connectTimeout}")
    private int connectTimeout;
    @Value("${mongo.maxWaitTime}")
    private int maxWaitTime;
    @Value("${mongo.socketKeepAlive}")
    private boolean socketKeepAlive;
    @Value("${mongo.socketTimeout}")
    private int socketTimeout;
	
    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClientOptions options = MongoClientOptions.builder()
                .threadsAllowedToBlockForConnectionMultiplier(this.multiplier)
                .connectionsPerHost(this.connectionsPerHost)
                .connectTimeout(this.connectTimeout)
                .maxWaitTime(this.maxWaitTime)
                .socketTimeout(this.socketTimeout)
                .build();
        MongoClient client = new MongoClient(new ServerAddress(this.host, this.port), options);
        return new SimpleMongoDbFactory(client, this.dbname);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
