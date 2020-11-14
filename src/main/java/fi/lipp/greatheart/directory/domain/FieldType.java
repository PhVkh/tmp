package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(schema = "storage", name = "field_types")
public class FieldType {
    @Id
    String type;
}
