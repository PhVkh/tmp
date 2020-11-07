package fi.lipp.greatheart.directory.domain;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

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

    String rus_name;

    String description;

    @Type(type = "list-array")
    @Column(
            name = "necessary_fields",
            columnDefinition = "text[]"
    )
    List<String> necessaryFields;
}
