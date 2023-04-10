package com.portfolioCRUD.portfolio.repository;

import com.portfolioCRUD.portfolio.model.ImageOfProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ImageOfProjectRepository extends JpaRepository<ImageOfProject, Long> {

    List<ImageOfProject> findAllByProjectId(Long id);

    @Modifying
    void deleteByProjectId(Long id);

}
