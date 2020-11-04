package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CatalogRoleEntity {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;


}
