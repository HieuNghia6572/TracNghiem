package com.example.TracNghiem.repository;

import com.example.TracNghiem.entity.DeThi;
import com.example.TracNghiem.entity.User;
import com.example.TracNghiem.entity.UserDeThi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class UserDeThiRepositoryImpl implements IUserDeThiRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDeThiRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserDeThi> findByUserAndDeThi(User user, DeThi deThi) {
        String jpql = "SELECT ud FROM UserDeThi ud WHERE ud.user = :user AND ud.deThi = :deThi";
        return entityManager.createQuery(jpql, UserDeThi.class)
                .setParameter("user", user)
                .setParameter("deThi", deThi)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<UserDeThi> findByUserUsernameAndDeThiId(String username, Long deThiId) {
        String jpql = "SELECT ud FROM UserDeThi ud WHERE ud.user.username = :username AND ud.deThi.id = :deThiId";
        return entityManager.createQuery(jpql, UserDeThi.class)
                .setParameter("username", username)
                .setParameter("deThiId", deThiId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<UserDeThi> findByUserUsername(String username) {
        String jpql = "SELECT ud FROM UserDeThi ud WHERE ud.user.username = :username";
        return entityManager.createQuery(jpql, UserDeThi.class)
                .setParameter("username", username)
                .getResultList();
    }

    // Phương thức saveScore sẽ được định nghĩa ở đây
    public void saveScore(Long deThiId, String username, Integer score) {
        String sql = "UPDATE UserDeThi ud SET ud.diem = :score WHERE ud.deThi.id = :deThiId AND ud.user.username = :username";
        entityManager.createQuery(sql)
                .setParameter("score", score)
                .setParameter("deThiId", deThiId)
                .setParameter("username", username)
                .executeUpdate();
    }
}