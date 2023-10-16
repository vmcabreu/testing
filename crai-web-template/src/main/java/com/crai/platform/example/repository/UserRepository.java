package com.crai.platform.example.repository;

import com.crai.platform.example.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> , JpaSpecificationExecutor<User> {

    List<User> findByUserName(String userName);

    List<User> findAllByOrderByAgeDesc();

    @Query("Select u from User u WHERE u.userName = :userName AND u.age = :age")
    List<User> findByUserNameAge(String userName, Integer age);

}
