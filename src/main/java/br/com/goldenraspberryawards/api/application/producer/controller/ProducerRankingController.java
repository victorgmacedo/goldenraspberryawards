package br.com.goldenraspberryawards.api.application.producer.controller;

import br.com.goldenraspberryawards.api.application.producer.dto.ProducerRankDTO;
import br.com.goldenraspberryawards.api.application.producer.dto.ProducerRankDetailDTO;
import br.com.goldenraspberryawards.api.domain.producer.data.ProducerRank;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerReportPort;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer-rank")
public class ProducerRankingController {

    @Autowired
    private ProducerServicePort producerServicePort;

    @GetMapping
    public ProducerRankDetailDTO find() {
        ProducerReportPort report = producerServicePort.report();
        return new ProducerRankDetailDTO(report.lowerIntervalBetweenAward().stream().map(this::mapToDTO).toList(),
                report.higherIntervalBetweenAward().stream().map(this::mapToDTO).toList());
    }

    private ProducerRankDTO mapToDTO(ProducerRank producerRank) {
        return new ProducerRankDTO(producerRank.producer(),
                producerRank.interval(),
                producerRank.previousWin(),
                producerRank.followingWin());
    }
}
