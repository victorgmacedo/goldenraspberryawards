package br.com.goldenraspberryawards.api.domain.producer.usecase;

import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerPersistencePort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerReportPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerServicePort;

public class ProducerService implements ProducerServicePort {

    private final ProducerPersistencePort producerPersistencePort;

    public ProducerService(ProducerPersistencePort producerPersistencePort) {
        this.producerPersistencePort = producerPersistencePort;
    }

    public ProducerReportPort report() {
        return new ProducerReport(producerPersistencePort);
    }
}
