package fi.lipp.greatheart.directory.model;

public enum ParthnershipSphere {
    EVENT_TERRITORY("Площадка для мероприятий"),
    CATERING("Кейтеринг"),
    PRESENTS("Подарки и сувениры"),
    PRINTING("Полиграфия"),
    MEDICINE_SERVICE("Медицинские услуги"),
    LEGAL_SERVICE("Юридические услуги"),
    PSYCHOLOGY("Психологи");


    private final String value;

    ParthnershipSphere(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
