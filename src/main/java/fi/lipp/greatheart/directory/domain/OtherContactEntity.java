package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "catalog", name = "other_user_contacts")
public class OtherContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Column(name = "type")
    private String type;

    @Column(name = "contact")
    private String contact;





}
