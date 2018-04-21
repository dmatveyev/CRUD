package com.denis.model;

import com.denis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User WHERE login = :name ")
    User findByLogin(@Param("name") String name);

}
