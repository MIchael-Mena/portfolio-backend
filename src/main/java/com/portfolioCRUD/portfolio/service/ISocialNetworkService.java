package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.SocialNetwork;

import java.util.List;

public interface ISocialNetworkService {

    public List<SocialNetwork> getSocialNetworks();

    public List<SocialNetwork> getSocialNetworksByOrderByPositionAsc();

    public void saveSocialNetwork(SocialNetwork socialNetwork);

    public void deleteSocialNetwork(Long id);

    public SocialNetwork findSocialNetwork(Long id);
}
