package com.client.repository;

import com.client.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);

    @Query("SELECT r FROM Role r JOIN r.user u WHERE u.id = :userid")
    Role getRoleByUser(@Param("userid")long id);
}
