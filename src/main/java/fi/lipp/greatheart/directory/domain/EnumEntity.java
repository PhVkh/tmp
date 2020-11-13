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

    String name;

    @NotNull
    Long enumTypeId;
}
