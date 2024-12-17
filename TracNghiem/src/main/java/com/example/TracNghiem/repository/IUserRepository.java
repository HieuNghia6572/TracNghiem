package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

    @Query(value = "SELECT r.name FROM  Role  r INNER JOIN user_role ur "+ "ON r.id = ur.role_id " +
            "WHERE ur.user_id = ?1 ", nativeQuery = true)
    String[] getRoleOfUser(Long userId);

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email);
    @Query("SELECT c FROM User c WHERE c.resetPasswordToken = ?1")
    public User findByResetPasswordToken(String token);

}
