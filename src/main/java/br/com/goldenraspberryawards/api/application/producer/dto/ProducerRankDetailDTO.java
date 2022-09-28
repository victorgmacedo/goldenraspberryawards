package br.com.goldenraspberryawards.api.application.producer.dto;

import java.util.List;

public record ProducerRankDetailDTO(List<ProducerRankDTO> min, List<ProducerRankDTO> max) {
}
