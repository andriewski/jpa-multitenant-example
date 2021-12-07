package by.mark.multitenancy.repository;

import by.mark.multitenancy.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = "roles")
    List<User> findAllWithRolesBy();

    @EntityGraph(attributePaths = "roles")
    Optional<User> findWithRolesByUserId(UUID id);

    @QueryHints(@QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
    @Query("select distinct u from User u" +
        " left join fetch u.roles" +
        " where u.firstName = :name")
    List<User> findAllWithQueryWithJoinByFirstName(String name);

    /**
     * NOTE: It causes filtration in memory
     */
    @EntityGraph(attributePaths = "roles")
    Page<User> findAllWithRolesBy(Pageable pageable);
}
