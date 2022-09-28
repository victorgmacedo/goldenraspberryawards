package br.com.goldenraspberryawards.api.domain.producer.data;

public record ProducerRank(String producer,
                           Integer interval,
                           Integer previousWin,
                           Integer followingWin) { }
