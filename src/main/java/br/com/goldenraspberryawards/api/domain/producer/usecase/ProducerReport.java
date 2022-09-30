package br.com.goldenraspberryawards.api.domain.producer.usecase;

import br.com.goldenraspberryawards.api.domain.producer.data.Producer;
import br.com.goldenraspberryawards.api.domain.producer.data.ProducerRank;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerPersistencePort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerReportPort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProducerReport implements ProducerReportPort {

    private List<Producer> producersWinners;
    private List<ProducerRank> producerRanks;

    public ProducerReport(ProducerPersistencePort producerPersistencePort) {
        this.producersWinners = producerPersistencePort.findAllWinnersWithMoreThanOneAward();
        producerRanks = buildProducersRank();
    }

    public List<ProducerRank> higherIntervalBetweenAward() {
        var max = getIntervals().max(Integer::compareTo).orElse(0);
        return filterByInterval(max);
    }

    public List<ProducerRank> lowerIntervalBetweenAward() {
        var min = getIntervals().min(Integer::compareTo).orElse(0);
        return filterByInterval(min);
    }

    private List<ProducerRank> buildProducersRank() {
        return groupByProducers()
                .entrySet()
                .stream()
                .map(this::mapEntrySetToProducerRank)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<ProducerRank> mapEntrySetToProducerRank(Map.Entry<String, List<Producer>> entry) {
        int size = entry.getValue().size();
        var ratings = new ArrayList<ProducerRank>(size);
        for (int i = 0; i < entry.getValue().size() - 1; i++) {
            var current = entry.getValue().get(i);
            var next = entry.getValue().get(i + 1);
            ratings.add(new ProducerRank(entry.getKey(), next.year() - current.year(), current.year(), next.year()));
        }
        return ratings;
    }

    private Map<String, List<Producer>> groupByProducers() {
        return this.producersWinners.stream().collect(Collectors.groupingBy(Producer::producer));
    }

    private List<ProducerRank> filterByInterval(Integer interval) {
        return this.producerRanks.stream()
                .filter(producerRanking -> Objects.equals(producerRanking.interval(), interval))
                .toList();
    }

    private Stream<Integer> getIntervals() {
        return this.producerRanks.stream().map(ProducerRank::interval);
    }

}
