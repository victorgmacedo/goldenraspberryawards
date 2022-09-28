package br.com.goldenraspberryawards.api.domain.producer.port.out;

import br.com.goldenraspberryawards.api.domain.producer.data.Producer;

public interface ProducerPersistencePort {

    void save(Producer producer);

}
