package com.cintvv.springbootv2.dao;

import com.cintvv.springbootv2.configs.SecurityConfig;
import com.cintvv.springbootv2.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveUser(User user) {
        User userSave = getUserByUsername(user.getUsername());
        User userSaveWithFilter = new User();
        if (userSave == null) {
            entityManager.persist(user);
        } else {
            userSaveWithFilter.setUsername(generateUniqueUsername(user.getUsername()));
            userSaveWithFilter.setPassword(user.getPassword());
            userSaveWithFilter.setEmail(user.getEmail());
            userSaveWithFilter.setPhone(user.getPhone());
            userSaveWithFilter.setAvatar(user.getAvatar());
            userSaveWithFilter.setRoles(user.getRoles());
            entityManager.persist(userSaveWithFilter);
        }
    }

    //Генератор уникальных имен от задвоения одинаковых ников :)
    private String generateUniqueUsername(String username) {
        int count = 1;
        String newUsername = username + count;
        while (getUserByUsername(newUsername) != null) {
            count++;
            newUsername = username + count; // Увеличиваем счетчик для создания уникального имени
        }
        return newUsername;
    }

    @Override
    @Transactional
    public void removeUserById(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    @Transactional
    public void removeUserByUsername(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

//    public User getUserByUsername(String username) {
//        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
//        query.setParameter("username", username);
//        return query.getSingleResult();
//    } СТАРЫЙ МЕТОД

    public User getUserByUsername(String username) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public String getRolesByUsername(String username) {
        User user = entityManager.find(User.class, username);
        String roles = user.getRole();
        return roles;
    }
}
