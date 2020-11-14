package fi.lipp.greatheart.directory.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EnumTypeDto {
    @NotNull
    String name;
    String description;
    Long id;

}
