package com.github.tomj0101.ebankv1.ordersystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Ordersystem.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
