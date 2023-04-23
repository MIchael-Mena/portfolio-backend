package com.portfolioCRUD.portfolio.repository;

import com.portfolioCRUD.portfolio.model.ImageOfProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageOfProjectRepository extends JpaRepository<ImageOfProject, Long> {

    List<ImageOfProject> findAllByProjectId(Long id);

    @Modifying
    void deleteByProjectId(Long id);

}
