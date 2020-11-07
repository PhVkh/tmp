package fi.lipp.greatheart.directory.domain;

import fi.lipp.greatheart.directory.enums.Category;
import fi.lipp.greatheart.directory.enums.LegalType;
import fi.lipp.greatheart.directory.enums.PartnershipType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(schema = "catalog", name = "partners")
public class PartnersEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    LegalType type;

    @Column(name = "registration_details")
    String registrationDetails;

    @Column(name = "payment_details")
    String paymentDetails;

//    @OneToOne(mappedBy = "companyId")
//    private ContactEntity contact;

//    @OneToMany(mappedBy = "userId")
//    private List<OtherContactEntity> contacts;
//
//    @OneToMany(mappedBy = "owner")
//    private List<EmailEntity> emails;
//
//    @OneToMany(mappedBy = "owner")
//    private List<PhoneEntity> phones;

    @Column(name = "partnership")
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "company_partnership", schema = "catalog", joinColumns = @JoinColumn(name = "company_id"))
    private List<PartnershipType> partnership;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "comment")
    private String comment;


}
