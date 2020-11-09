package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(schema = "storage", name = "enum_type")
public class EnumTypeEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String engName;

    String rusName;

    String description;

}
