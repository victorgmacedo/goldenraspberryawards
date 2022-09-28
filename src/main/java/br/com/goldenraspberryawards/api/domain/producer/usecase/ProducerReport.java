package br.com.goldenraspberryawards.api.domain.producer.usecase;

import br.com.goldenraspberryawards.api.domain.producer.data.ProducerRank;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerRankPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerReportPort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ProducerReport implements ProducerReportPort {

    private List<ProducerRank> ratings;

    public ProducerReport(ProducerRankPort producerRankPort) {
        this.ratings = producerRankPort.getProducerWithMoreThanOneAward();
    }

    public List<ProducerRank> higherIntervalBetweenAward() {
        var max = getIntervals().max(Integer::compareTo).orElse(0);
        return filterByInterval(max);
    }

    public List<ProducerRank> lowerIntervalBetweenAward() {
        var min = getIntervals().min(Integer::compareTo).orElse(0);
        return filterByInterval(min);
    }

    private List<ProducerRank> filterByInterval(Integer interval) {
        return ratings.stream()
                .filter(producerRanking -> Objects.equals(producerRanking.interval(), interval))
                .toList();
    }

    private Stream<Integer> getIntervals() {
        return ratings.stream().map(ProducerRank::interval);
    }

}
