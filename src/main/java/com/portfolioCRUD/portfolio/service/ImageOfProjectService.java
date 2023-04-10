package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.ImageOfProject;
import com.portfolioCRUD.portfolio.repository.ImageOfProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageOfProjectService implements IImageOfProjectService {

    @Autowired
    private ImageOfProjectRepository imageOfProjectRepository;

    @Override
    public void saveImageOfProject(ImageOfProject imageOfProject) {
        this.imageOfProjectRepository.save(imageOfProject);
    }

    @Override
    public void deleteImageOfProject(Long id) {
        this.imageOfProjectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteImagesOfProjectByProjectId(Long id) {
        this.imageOfProjectRepository.deleteByProjectId(id);
    }

    @Override
    public ImageOfProject findImageOfProject(Long id) {
        return this.imageOfProjectRepository.findById(id).get();
    }

    @Override
    public List<ImageOfProject> getImagesOfProjectByProjectId(Long id) {
        return this.imageOfProjectRepository.findAllByProjectId(id);
    }

}
