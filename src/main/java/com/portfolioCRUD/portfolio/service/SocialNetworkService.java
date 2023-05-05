package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.SocialNetwork;
import com.portfolioCRUD.portfolio.repository.SocialNetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialNetworkService implements ISocialNetworkService {

    @Autowired
    private SocialNetworkRepository socialNetworkRepository;

    public SocialNetworkService(){
    }

    @Override
    public List<SocialNetwork> getSocialNetworks() {
        return (List<SocialNetwork>) socialNetworkRepository.findAll();
    }

    @Override
    public List<SocialNetwork> getSocialNetworksByOrderByPositionAsc() {
        return (List<SocialNetwork>) socialNetworkRepository.findAllByOrderByPositionAsc();
    }

    @Override
    public void saveSocialNetwork(SocialNetwork socialNetwork) {
        socialNetworkRepository.save(socialNetwork);
    }

    @Override
    public void deleteSocialNetwork(Long id) {
        socialNetworkRepository.deleteById(id);
    }

    @Override
    public SocialNetwork findSocialNetwork(Long id) {
        return socialNetworkRepository.findById(id).orElse(null);
    }


    public boolean existsById(Long id) {
        return socialNetworkRepository.existsById(id);
    }
}
