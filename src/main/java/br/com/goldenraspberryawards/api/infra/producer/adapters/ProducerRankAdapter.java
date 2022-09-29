package br.com.goldenraspberryawards.api.infra.producer.adapters;

import br.com.goldenraspberryawards.api.domain.producer.data.ProducerRank;
import br.com.goldenraspberryawards.api.domain.producer.port.out.ProducerRankPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProducerRankAdapter implements ProducerRankPort {

    @Autowired
    EntityManager em;

    @Override
    public List<ProducerRank> getProducerWithMoreThanOneAward() {
        return em.createQuery("""
                select new br.com.goldenraspberryawards.api.domain.producer.data.ProducerRank(a.producer,
                       max(a.year) - min(a.year),
                       min(a.year),
                       max(a.year))
                       FROM ProducerEntity a
                       WHERE a.winner is true
                       GROUP BY a.producer
                       HAVING count(1) > 1
                       ORDER BY 4 DESC
                """, ProducerRank.class).getResultList();
    }
}
