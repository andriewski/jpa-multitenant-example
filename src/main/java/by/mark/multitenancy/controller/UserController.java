package by.mark.multitenancy.controller;

import by.mark.multitenancy.model.User;
import by.mark.multitenancy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * NOTE:
 * Returning domain entity it antipatern,
 * it was made only for demostraction purposes
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Should throw {@link org.hibernate.LazyInitializationException},
     * since {@link User#getRoles()} wasn't fetched
     */
    @GetMapping
    public List<User> listUsers() {
        List<User> users = userService.listUsers();
        return users;
    }

    @GetMapping("/with-roles")
    public List<User> listUsersWithRoles() {
        List<User> users = userService.listUsersWithRoles();
        System.out.println("1");
        return users;
    }

    @GetMapping("/paged")
    public Page<User> findAllPaged(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.findAllPaged(PageRequest.of(page, size));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") String userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/name/{name}")
    public List<User> findAllByName(@PathVariable("name") String name) {
        return userService.findAllByName(name);
    }

    @GetMapping("/name2/{name}")
    public List<User> findAllByName2(@PathVariable("name") String name) {
        return userService.findAllByName2(name);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);
    }
}
