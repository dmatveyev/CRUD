package com.denis.repository;

import com.denis.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("FROM Role WHERE role = :role ")
    Role findByLogin(@Param("role") String role);

    @Query("SELECT r FROM Role r  JOIN r.user u WHERE u.id = :userid")
    List<Role> getRoleByUser(@Param("userid")long id);
}
