package com.github.tomj0101.ebankv1.ordersystem;

import com.github.tomj0101.ebankv1.ordersystem.OrdersystemApp;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = OrdersystemApp.class)
public @interface IntegrationTest {
}
