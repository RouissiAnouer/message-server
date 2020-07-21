package com.app.config.socketIo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.app.config.yaml.YamlConfigurationProperties;
import com.corundumstudio.socketio.store.MemoryStoreFactory;
import com.corundumstudio.socketio.store.StoreFactory;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedissonConfig {

	@Autowired
	private YamlConfigurationProperties env;

//	@Bean
//	public RedissonClient redissonClient() throws IOException {
//		RedissonClient redissonClient = null;
////		log.debug("FilePath redisConf : {}", env.getApplication().getSocketIo().getRedisConfigFilePath());
////		File file = new File(env.getApplication().getSocketIo().getRedisConfigFilePath());
////		if (file.exists()) {
////			try {
////				log.debug("RedissonBuild attempt :{}");
////				log.info(file.getPath());
//////				Config config = Config.fromJSON(file);
//////				redissonClient = Redisson.create(config);
////			} catch (Exception e) {
////				log.error("Exception RedissonCreate {}", e);
////			}
////			log.debug("redisson create success");
////		}
//		return redissonClient;
//
//	}

	@Bean
	public StoreFactory storeFactory() {
//		if (client != null) {
//			Redisson redisson = (Redisson) client;
//			return new RedissonStoreFactory(redisson);
//		} else
			return new MemoryStoreFactory();
	}

}
