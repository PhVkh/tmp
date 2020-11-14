package fi.lipp.greatheart.directory.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditDto {

    private Long id;
    private LocalDateTime time;
    private String path;
    private String requestType;
    private String requestBody;
    private String user;
}
