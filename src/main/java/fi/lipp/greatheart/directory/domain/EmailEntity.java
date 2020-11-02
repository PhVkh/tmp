package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "email", schema = "catalog")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private UserEntity owner;

    @Column
    private String email;

    @Override
    public String toString() {
        return "EmailEntity{" +
                "email='" + email + '\'' +
                '}';
    }
}
