package com.github.tomj0101.ebankv1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Ebankv 1.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
