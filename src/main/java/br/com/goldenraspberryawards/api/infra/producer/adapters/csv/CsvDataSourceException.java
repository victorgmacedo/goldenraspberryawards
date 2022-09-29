package br.com.goldenraspberryawards.api.infra.producer.adapters.csv;

public class CsvDataSourceException extends RuntimeException{

    public CsvDataSourceException(String message) {
        super(message, null, true, false);
    }

}
