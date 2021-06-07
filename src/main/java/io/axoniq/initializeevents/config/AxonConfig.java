package io.axoniq.initializeevents.config;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {


    @Bean
    @Primary
    public Serializer axonSerializer() {
        return JacksonSerializer.builder()
                .lenientDeserialization()
                .build();
    }

}
