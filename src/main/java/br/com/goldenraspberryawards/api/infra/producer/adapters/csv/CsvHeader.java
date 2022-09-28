package br.com.goldenraspberryawards.api.infra.producer.adapters.csv;

public enum CsvHeader {

    YEAR( "year"),
    TITLE( "title"),
    STUDIO( "studio"),
    PRODUCER( "producer"),
    WINNER("winner");

    public final String name;

    CsvHeader(String name) {
        this.name = name;
    }
}
