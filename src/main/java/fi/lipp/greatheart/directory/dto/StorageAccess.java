package fi.lipp.greatheart.directory.dto;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(schema = "auth", name = "storage_access")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class StorageAccess {
    @Id
    String login;

    @Type(type = "list-array")
    @Column(
            name = "entity_list_all_rights",
            columnDefinition = "integer[]"
    )
    List<Integer> entityListAll;

}
