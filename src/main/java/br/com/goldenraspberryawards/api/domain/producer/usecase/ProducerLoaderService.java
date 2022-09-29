package br.com.goldenraspberryawards.api.domain.producer.usecase;

import br.com.goldenraspberryawards.api.domain.producer.port.in.ProducerDataSourcePort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerLoaderPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerPersistencePort;

public class ProducerLoaderService implements ProducerLoaderPort {

    private final ProducerDataSourcePort dataLoader;
    private final ProducerPersistencePort producerPersistencePort;

    public ProducerLoaderService(ProducerDataSourcePort dataLoader, ProducerPersistencePort producerPersistencePort) {
        this.dataLoader = dataLoader;
        this.producerPersistencePort = producerPersistencePort;
    }

    public void load() {
        dataLoader.readData().forEach(producerPersistencePort::save);
    }

}
