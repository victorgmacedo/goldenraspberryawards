package br.com.goldenraspberryawards.api.domain.producer.port.in;

import br.com.goldenraspberryawards.api.domain.producer.data.Producer;

import java.util.List;

public interface ProducerDataSourcePort {

    List<Producer> readData();

}
