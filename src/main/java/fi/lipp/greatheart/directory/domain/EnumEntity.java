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

    @NotNull
    String name;

    @NotNull
    Long enumTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnumEntity)) return false;

        EnumEntity that = (EnumEntity) o;

        if (getName() != null ?
                !getName().toLowerCase().strip()
                        .equals(
                                that.getName().toLowerCase().strip()) : that.getName() != null)
            return false;
        return getEnumTypeId() != null ?
                getEnumTypeId()
                        .equals(
                                that.getEnumTypeId()) : that.getEnumTypeId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEnumTypeId() != null ? getEnumTypeId().hashCode() : 0);
        return result;
    }
}
