package br.com.goldenraspberryawards.api.application.producer.dto;

public record ProducerRankDTO(String producer,
                              Integer interval,
                              Integer previousWin,
                              Integer followingWin) {
}
