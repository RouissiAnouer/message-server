package com.app.config.socketIo;

import java.util.Optional;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.app.config.yaml.YamlConfigurationProperties;
import com.corundumstudio.socketio.store.MemoryStoreFactory;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.StoreFactory;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedissonConfig {

	@Autowired
	private YamlConfigurationProperties env;

	@Bean
	public RedissonClient redissonClient() {
		RedissonClient redissonClient = null;
		if (env.getApplication().getSocketIo().isRedisEnabled()) {
			log.debug("RedissonBuild attempt :{}");
			Config config = new Config();
			config.useSingleServer().setAddress("redis://127.0.0.1:6379").setConnectTimeout(10000)
					.setPingConnectionInterval(1000).setConnectTimeout(10000).setTimeout(3000).setRetryAttempts(3)
					.setRetryInterval(1500);
			redissonClient = Redisson.create(config);
		}
		return redissonClient;

	}

	@Bean
	public StoreFactory storeFactory(Optional<RedissonClient> client) {
		if (client.isPresent() && client != null) {
			Redisson redisson = (Redisson) client.get();
			return new RedissonStoreFactory(redisson);
		} else
			return new MemoryStoreFactory();
	}

}
