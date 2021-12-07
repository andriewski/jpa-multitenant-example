package by.mark.multitenancy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
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
@Table(name = "user_info")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    private UUID userId;

    private String username;

    private String firstName;

    private String lastName;

    private String tenantId;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }
}
