package com.codecrafter.repository;

import com.codecrafter.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMyUserRepository extends JpaRepository<MyUser,Long> {
    MyUser findByEmail(String mail);
}
