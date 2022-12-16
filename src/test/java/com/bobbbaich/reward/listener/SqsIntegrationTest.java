package com.bobbbaich.reward.listener;

import com.bobbbaich.reward.configuration.LocalstackEnvironmentInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = LocalstackEnvironmentInitializer.class)
class SqsIntegrationTest {

}
