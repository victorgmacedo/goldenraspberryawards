package br.com.goldenraspberryawards.api.infra.producer.repository;

import br.com.goldenraspberryawards.api.infra.producer.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, Integer> {
    @Query("""
            select a 
              from ProducerEntity a 
             where a.producer in (select p.producer 
                                    from ProducerEntity p 
                                   where p.winner = true 
                                   group by p.producer 
                                  having count(1) > 1)
               and a.winner = true order by year asc
            """)
    List<ProducerEntity> findAllWinnerTrue();
}
