package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "phone", schema = "catalog")
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Column(name = "phone_number")
    private String phone;

    @Column
    private String type;

    @Override
    public String toString() {
        return "PhoneEntity{" +
                "phoneNumber='" + phone + '\'' +
                '}';
    }


}
