package br.com.goldenraspberryawards.api.infra.producer.repository;

import br.com.goldenraspberryawards.api.infra.producer.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, Integer> {
}
