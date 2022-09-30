package br.com.goldenraspberryawards.api.infra.producer.adapters;

import br.com.goldenraspberryawards.api.domain.producer.data.Producer;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerPersistencePort;
import br.com.goldenraspberryawards.api.infra.producer.entity.ProducerEntity;
import br.com.goldenraspberryawards.api.infra.producer.repository.ProducerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProducerRepositoryAdapter implements ProducerPersistencePort {

    private final ProducerRepository repository;

    @Override
    public void save(Producer producer) {
        ProducerEntity entity = new ProducerEntity();
        BeanUtils.copyProperties(producer, entity);
        repository.save(entity);
    }

    public List<Producer> findAllWinnersWithMoreThanOneAward() {
        return repository.findAllWinnerTrue().stream().map(this::mapProducerEntityToProducer).toList();
    }

    private Producer mapProducerEntityToProducer(ProducerEntity producerEntity){
        return new Producer(
                producerEntity.getYear(),
                producerEntity.getTitle(),
                producerEntity.getStudio(),
                producerEntity.getProducer(),
                producerEntity.getWinner()
        );
    }
}
