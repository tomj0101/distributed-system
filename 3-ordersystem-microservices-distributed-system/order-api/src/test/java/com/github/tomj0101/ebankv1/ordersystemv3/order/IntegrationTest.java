package com.github.tomj0101.ebankv1.ordersystemv3.order;

import com.github.tomj0101.ebankv1.ordersystemv3.order.OrderapiApp;
import com.github.tomj0101.ebankv1.ordersystemv3.order.RedisTestContainerExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = OrderapiApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public @interface IntegrationTest {
}
