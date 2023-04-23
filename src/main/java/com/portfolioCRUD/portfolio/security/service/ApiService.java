package com.portfolioCRUD.portfolio.security.service;

import com.portfolioCRUD.portfolio.security.entity.Api;
import com.portfolioCRUD.portfolio.security.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    private ApiRepository userApiRepository;

    public List<Api> getApiKey(Long id) {
        return userApiRepository.findAllByUserId(id);
    }

    public void deleteByUser(Long id) {
        userApiRepository.deleteByUserId(id);
    }

}
