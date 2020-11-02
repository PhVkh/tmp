package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user", schema = "catalog")
public class UserEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<EmailEntity> emails;

    @OneToMany(mappedBy = "owner")
    private List<PhoneEntity> phones;

    //  private Integer globalRoleId;

    //to change
    //   private Integer catalogRoleIds;

    // to change
    //  private Integer trackerRoleIds;

    //  @Column(name = "additional_info", columnDefinition = "json")
    //  private String additionalInfo;


    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
