package br.com.goldenraspberryawards.api.config;

import br.com.goldenraspberryawards.api.domain.producer.port.in.ProducerDataSourcePort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerLoaderPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerPersistencePort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerRankPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerServicePort;
import br.com.goldenraspberryawards.api.domain.producer.usecase.ProducerLoaderService;
import br.com.goldenraspberryawards.api.domain.producer.usecase.ProducerService;

import br.com.goldenraspberryawards.api.infra.producer.adapters.csv.ProducerCsvDataSourceAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class BeanConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public ProducerDataSourcePort producerDataSourcePort(@Value("${csv.movie-path}") String csvPath) {
        if(asList(env.getActiveProfiles()).contains("it")) {
            return List::of;
        }
        return new ProducerCsvDataSourceAdapter(csvPath);
    }

    @Bean
    public ProducerLoaderPort producerLoaderPort(ProducerDataSourcePort producerDataSourcePort,
                                                 ProducerPersistencePort producerRepositoryPort) {
        return new ProducerLoaderService(producerDataSourcePort, producerRepositoryPort);
    }

    @Bean
    public ProducerServicePort producerReportPort(ProducerRankPort producerRankPort) {
        return new ProducerService(producerRankPort);
    }

    @Bean
    public FlywayMigrationStrategy clean() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }

}
