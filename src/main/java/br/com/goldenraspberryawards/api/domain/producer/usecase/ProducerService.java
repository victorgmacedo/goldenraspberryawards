package br.com.goldenraspberryawards.api.domain.producer.usecase;

import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerRankPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerReportPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerServicePort;

public class ProducerService implements ProducerServicePort {

    private final ProducerRankPort producerRankPort;

    public ProducerService(ProducerRankPort producerRankPort) {
        this.producerRankPort = producerRankPort;
    }

    public ProducerReportPort report() {
        return new ProducerReport(producerRankPort);
    }
}
