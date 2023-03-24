package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.SocialNetwork;

import java.util.List;

public interface ISocialNetworkService {

    List<SocialNetwork> getSocialNetworks();

    List<SocialNetwork> getSocialNetworksByOrderByPositionAsc();

    void saveSocialNetwork(SocialNetwork socialNetwork);

    void deleteSocialNetwork(Long id);

    SocialNetwork findSocialNetwork(Long id);
}
