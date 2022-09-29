package br.com.goldenraspberryawards.api.application.producer.controller;

import br.com.goldenraspberryawards.api.application.producer.dto.ProducerRankDTO;
import br.com.goldenraspberryawards.api.application.producer.dto.ProducerRankDetailDTO;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerLoaderPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerPersistencePort;
import br.com.goldenraspberryawards.api.domain.producer.usecase.ProducerLoaderService;
import br.com.goldenraspberryawards.api.infra.producer.adapters.csv.ProducerCsvDataSourceAdapter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("it")
class ProducerRankingControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProducerPersistencePort producerRepositoryPort;

    @Test
    public void whenImportTheOriginalListThenReturnTheLowerAndTheHigherProducerInterval() {
        producerLoaderPort("csv/original-movies-test.csv").load();

        ResponseEntity<ProducerRankDetailDTO> response = this.testRestTemplate
                .exchange("/producer-rank", HttpMethod.GET, null, ProducerRankDetailDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().max().size(), 1);
        assertEquals(response.getBody().min().size(), 1);

        ProducerRankDTO max = new ProducerRankDTO("MATTHEW VAUGHN", 13, 2002, 2015);
        ProducerRankDTO min = new ProducerRankDTO("JOEL SILVER", 1, 1990, 1991);

        MatcherAssert.assertThat(response.getBody().max(), Matchers.hasItem(max));
        MatcherAssert.assertThat(response.getBody().min(), Matchers.hasItem(min));
    }

    @Test
    public void whenHasNoDifferenceBetweenTheLowerAndTheHigherThanReturnTheSameResult() {
        producerLoaderPort("csv/same-diff-min-max-movies-test.csv").load();

        ResponseEntity<ProducerRankDetailDTO> response = this.testRestTemplate
                .exchange("/producer-rank", HttpMethod.GET, null, ProducerRankDetailDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().max().size(), 2);
        assertEquals(response.getBody().min().size(), 2);

        ProducerRankDTO max = new ProducerRankDTO("PRODUCER 1", 10, 1980, 1990);
        ProducerRankDTO min = new ProducerRankDTO("PRODUCER 2", 10, 1990, 2000);

        MatcherAssert.assertThat(response.getBody().max(), Matchers.containsInAnyOrder(max, min));
        MatcherAssert.assertThat(response.getBody().min(), Matchers.containsInAnyOrder(max, min));
    }

    private ProducerLoaderPort producerLoaderPort(String path) {
        return new ProducerLoaderService(new ProducerCsvDataSourceAdapter(path), producerRepositoryPort);
    }

}