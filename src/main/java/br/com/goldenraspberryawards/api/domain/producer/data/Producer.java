package br.com.goldenraspberryawards.api.domain.producer.data;

public record Producer(int year,
                       String title,
                       String studio,
                       String producer,
                       boolean winner){}