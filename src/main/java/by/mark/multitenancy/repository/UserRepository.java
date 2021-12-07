package by.mark.multitenancy.repository;

import by.mark.multitenancy.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = "roles")
    List<User> findAllWithRolesBy();

    List<User> findAllByFirstName(String name);

    @Query("select u from User u where u.firstName = :name")
    List<User> findAllByFirstName2(String name);
}
