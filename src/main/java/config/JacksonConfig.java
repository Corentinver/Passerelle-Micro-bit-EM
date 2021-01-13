package config;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
	
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
