package by.mark.multitenancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString(exclude = "users")
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private UUID roleId;

    private String role;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Role(String role) {
        this.role = role;
    }
}
