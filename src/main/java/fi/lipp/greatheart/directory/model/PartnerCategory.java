package fi.lipp.greatheart.directory.model;

public enum PartnerCategory {
    VIP("Вип"), BASIC("Обычный");

    private final String value;

    PartnerCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
