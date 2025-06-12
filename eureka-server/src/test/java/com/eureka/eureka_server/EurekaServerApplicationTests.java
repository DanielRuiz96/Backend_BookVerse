package com.eureka.eureka_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration;

@SpringBootTest(properties = {
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false",
    "eureka.client.enabled=false",
    "spring.cloud.discovery.enabled=false"
})
@ImportAutoConfiguration(exclude = EurekaServerAutoConfiguration.class)
class EurekaServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
