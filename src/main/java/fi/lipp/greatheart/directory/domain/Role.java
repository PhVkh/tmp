package fi.lipp.greatheart.directory.domain;

public enum Role {
    MANAGER(1), RESP_HR(2), RESP_WARD(3), RESP_PARTNERS(4),
    RESP_SPONSORS(5), RESP_BENEFACTOR(6), RESP_EVENT(7);

    Integer id;

    Role(Integer id) {
        this.id = id;
    }
}
