package fi.lipp.greatheart.directory.dto;

import lombok.Data;

import java.util.List;

@Data
public class EntityTypeDto {
    Long id;

    String name;

    String rus_name;

    String description;

    List<String> necessaryFields;

    Boolean main;

    String titleField;
}
