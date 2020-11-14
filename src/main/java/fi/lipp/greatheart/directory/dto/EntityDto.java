package fi.lipp.greatheart.directory.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class EntityDto {
    private Map<String, Object> json;
    private String title;
    private String name;

    private String creatorLogin;
    private LocalDateTime creationTime;
    private String lastModifiedByLogin;
    private LocalDateTime lastModifiedTime;
}
