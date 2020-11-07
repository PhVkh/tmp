package fi.lipp.greatheart.directory.dto;

import lombok.Data;

@Data
public class PhoneDto {
    private String phone;
    private String type;
    private String ownerType;

    @Override
    public String toString() {
        return "PhoneDto{" +
                "phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
