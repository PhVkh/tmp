package fi.lipp.greatheart.directory.domain;


public enum Availability {
    ANYDAY_ANYTIME("В любое время"), WORKDAY_ANYTIME(""), WORKDAY_EVENING(""), WEEKDAY_ANYTIME("Выходные, любое время"), WEEKDAY_HOURS(""), OTHER("");

    private String value;

    Availability(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "value='" + value + '\'' +
                '}';
    }
}
