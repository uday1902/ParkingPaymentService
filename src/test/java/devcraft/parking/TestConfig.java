package devcraft.parking;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import devcraft.parking.jpa.DbConfig;

@Configuration
@EnableAutoConfiguration
@Import(DbConfig.class)
public class TestConfig {

}