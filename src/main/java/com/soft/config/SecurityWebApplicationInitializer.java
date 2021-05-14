package com.soft.config;

import com.soft.database.DatabaseConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebApplicationInitializer() {
	super(DatabaseConfig.class, SecurityConfig.class);
    }
}
