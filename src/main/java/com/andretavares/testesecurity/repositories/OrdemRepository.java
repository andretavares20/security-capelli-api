package com.andretavares.testesecurity.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.andretavares.testesecurity.entities.Ordem;

public interface OrdemRepository extends JpaRepository<Ordem,Long>{

    List<Ordem> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT o FROM Ordem o WHERE LOWER(o.number) LIKE %:filterText% OR LOWER(o.user.name) LIKE %:filterText%")
    List<Ordem> search(@Param("filterText") String filterText, Pageable pageable);
    
}
