package br.com.goldenraspberryawards.api.domain.producer.port.out;

import br.com.goldenraspberryawards.api.domain.producer.data.ProducerRank;

import java.util.List;

public interface ProducerReportPort {

    List<ProducerRank> higherIntervalBetweenAward();
    List<ProducerRank> lowerIntervalBetweenAward();

}
