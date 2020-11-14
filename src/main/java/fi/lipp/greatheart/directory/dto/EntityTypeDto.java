package fi.lipp.greatheart.directory.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EntityTypeDto {
    Long id;

    String name;

    String description;

    Map<String, Object> necessaryFields;

    Boolean main;

    String titleField;
}
