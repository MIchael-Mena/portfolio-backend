package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.ImageOfProject;

import java.util.List;

public interface IImageOfProjectService {

    List<ImageOfProject> getImagesOfProjectByProjectId(Long id);

    void saveImageOfProject(ImageOfProject imageOfProject);

    void deleteImageOfProject(Long id);

    void deleteImagesOfProjectByProjectId(Long id);

    ImageOfProject findImageOfProject(Long id);

}
