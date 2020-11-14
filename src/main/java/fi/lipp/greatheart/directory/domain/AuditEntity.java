package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "storage", name = "audit")
public class AuditEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "request_time")
    private LocalDateTime time;
    @Column(name = "path")
    private String path;
    @Column(name = "request_type")
    private String requestType;
    @Column(name = "request_body")
    private String requestBody;
    @Column(name = "user_login")
    private String user;
}
