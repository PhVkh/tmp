package fi.lipp.greatheart.directory.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(schema = "storage", name = "entities")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EntityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "entity_type")
    Long entityType;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "entity")
    private Map<String, Object> json = new HashMap<>();

    private String creatorLogin;
    private LocalDateTime creationTime;
    private String lastModifiedByLogin;
    private LocalDateTime lastModifiedTime;
}
