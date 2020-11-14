package fi.lipp.greatheart.directory.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EntityDto {
    private Map<String, Object> json;
    private String title;
    private String name;
}
