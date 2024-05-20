package com.example.platformtest.repositories;

import com.example.platformtest.entities.API;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface APIRepository extends JpaRepository<API, Long> {
    @Query("select a from API a where a.nom like :x") //hql
    Page<API> chercher(@Param("x") String mc, Pageable pageable);

}