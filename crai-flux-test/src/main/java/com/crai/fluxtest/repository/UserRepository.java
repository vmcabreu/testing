package com.crai.fluxtest.repository;

import com.crai.fluxtest.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends ReactiveCrudRepository<User,Integer> {
}
