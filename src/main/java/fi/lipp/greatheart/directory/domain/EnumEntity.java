package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(schema = "storage", name = "enum")
public class EnumEntity {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String engName;

    @NotNull
    String rusName;

    @NotNull
    Long enumTypeId;
}
