package ru.kata.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void save(User user) {
        if(user.getPassword().isEmpty())
            user.setPassword(userRepository.getById(user.getId()).getPassword());
        else
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles() == null)
            user.setRoles(userRepository.getById(user.getId()).getRoles());
        else {
            Set<Role> roles = user.getRoles().stream().map(role -> roleService.findByRole(role.getRole()).get())
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.get();
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
