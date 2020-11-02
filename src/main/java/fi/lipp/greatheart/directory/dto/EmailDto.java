package fi.lipp.greatheart.directory.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String email;

    @Override
    public String toString() {
        return "EmailDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
