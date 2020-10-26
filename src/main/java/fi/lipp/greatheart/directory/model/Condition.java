package fi.lipp.greatheart.directory.model;

public enum Condition {

    REMISSION("Ремиссия"),
    DISEASE_DIAGNOSED("Диагностирована болезнь"),
    REHABILITATION("Реабилитация"),
    TREATMENT("На лечении"),
    HEALTH("Здоров");

    private final String value;

    Condition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
