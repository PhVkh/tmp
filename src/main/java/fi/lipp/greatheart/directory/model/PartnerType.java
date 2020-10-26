package fi.lipp.greatheart.directory.model;

import javax.servlet.http.Part;

public enum PartnerType {
    LEGAL_ENTITY("Юридическое лицо"),
    INDIVIDUAL("Физическое лицо");

    private final String value;

    PartnerType(String value) {
        this.value = value;
    }

}
