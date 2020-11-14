package fi.lipp.greatheart.directory.domain;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(schema = "storage", name = "entity_type")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class EntityTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String description;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "necessary_fields")
    Map<String, Object> necessaryFields = new HashMap<>();

    @Column(name = "is_main")
    Boolean main;

    @Column(name = "title_field")
    String titleField;
}
