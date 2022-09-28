package br.com.goldenraspberryawards.api;

import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerLoaderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

@SpringBootApplication
public class ApiApplication {

	@Autowired
	private ProducerLoaderPort producerLoaderPort;
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@PostConstruct
	public void init() {
		if(asList(env.getActiveProfiles()).contains("it")) return;
		producerLoaderPort.load();
	}
}
