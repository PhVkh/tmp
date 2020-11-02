package fi.lipp.greatheart.directory._old.model_old;

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
