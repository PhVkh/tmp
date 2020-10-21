package fi.lipp.greatheart.directory.model;

public enum Condition {

    RARE("Ремиссия"),
    MEDIUM_RARE("Диагностирована болезнь"),
    MEDIUM("Реабилитация"),
    MEDIUM_WELL("На лечении"),
    WELL_DONE("Здоров");

    private String value;

    Condition(String value) {
        this.value = value;
    }
}
