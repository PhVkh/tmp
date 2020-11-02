package fi.lipp.greatheart.directory._old.model_old;

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
