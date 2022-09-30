package br.com.goldenraspberryawards.api.domain.producer.port.out;

import br.com.goldenraspberryawards.api.domain.producer.data.Producer;

import java.util.List;

public interface ProducerPersistencePort {

    void save(Producer producer);

    List<Producer> findAllWinnersWithMoreThanOneAward();

}
