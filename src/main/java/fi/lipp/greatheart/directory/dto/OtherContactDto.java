package fi.lipp.greatheart.directory.dto;

import lombok.Data;

@Data
public class OtherContactDto {
    private String type;
    private String contact;

    @Override
    public String toString() {
        return "OtherContactDto{" +
                "type='" + type + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
