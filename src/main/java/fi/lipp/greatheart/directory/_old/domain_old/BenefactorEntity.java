package fi.lipp.greatheart.directory._old.domain_old;

import fi.lipp.greatheart.directory._old.model_old.PartnerCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "benefactor")
public class BenefactorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "social_networks")
    private String socialNetworks;
    @Column(name = "site")
    private String website;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "category_id")
    private PartnerCategory category;
    @Column(name = "comment")
    private String comment;
}
