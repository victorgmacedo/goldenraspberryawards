package br.com.goldenraspberryawards.api.infra.producer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "producer")
@Getter
@Setter
public class ProducerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "`year`")
    private int year;
    private String title;
    private String studio;
    private String producer;
    private Boolean winner;

}
