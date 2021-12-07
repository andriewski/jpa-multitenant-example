package by.mark.multitenancy.service;

import by.mark.multitenancy.filter.TenantContext;
import by.mark.multitenancy.model.Role;
import by.mark.multitenancy.model.User;
import by.mark.multitenancy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements ApplicationRunner {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public List<User> listUsersWithRoles() {
        return userRepository.findAllWithRolesBy();
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> findAllByName(String name) {
        return userRepository.findAllByFirstName(name);
    }

    public List<User> findAllByName2(String name) {
        return userRepository.findAllByFirstName2(name);
    }

    public Page<User> findAllPaged(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

    @Override
    @Transactional
    public void run(ApplicationArguments applicationArguments) {
        Role role1 = new Role("role1");
        Role role2 = new Role("role2");
        Role role3 = new Role("role3");
        Role role4 = new Role("role4");

        User user1 = new User("user1", "Test1", "User1");
        User user2 = new User("user2", "Test2", "User2");
        User user3 = new User("user3", "Test3", "User3");
        User user4 = new User("user4", "Test4", "User4");

        user1.addRole(role1);
        user1.addRole(role2);
        user2.addRole(role2);

        TenantContext.setCurrentTenant("999");

        userRepository.save(user1);
        userRepository.save(user2);

        TenantContext.setCurrentTenant("998");

        user3.addRole(role3);
        user3.addRole(role4);

        userRepository.save(user3);
        userRepository.save(user4);
    }
}
