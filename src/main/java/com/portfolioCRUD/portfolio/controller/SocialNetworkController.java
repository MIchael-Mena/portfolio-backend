package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.model.SocialNetwork;
import com.portfolioCRUD.portfolio.service.SocialNetworkService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
public class SocialNetworkController {

    @Autowired
    private SocialNetworkService socialNetworkService;

    ///socialNetworks?order=position&direction=asc
    @GetMapping("/socialNetworks")
    public ResponseEntity<List<SocialNetwork>> getSocialNetworks(@RequestParam(required = false) String order,
                                                                @RequestParam(required = false) String direction) {
        if (order != null && direction != null) {
            if (order.equals("position") && direction.equals("asc")) {
                return ResponseEntity.ok(socialNetworkService.getSocialNetworksByOrderByPositionAsc());
            }
        }
        return ResponseEntity.ok(socialNetworkService.getSocialNetworks());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/socialNetworks/create")
    public ResponseEntity<SocialNetwork> saveSocialNetwork(@RequestBody SocialNetwork socialNetwork) {
        try{
            socialNetworkService.saveSocialNetwork(socialNetwork);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
//        socialNetworkService.saveSocialNetwork(socialNetwork);
        return ResponseEntity.ok(socialNetwork);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/socialNetworks/delete/{id}")
    public ResponseEntity<String> deleteSocialNetwork(@PathVariable Long id) {

        socialNetworkService.deleteSocialNetwork(id);
        return ResponseEntity.ok("Social Network deleted");
    }

    @GetMapping("/socialNetworks/{id}")
    public SocialNetwork findSocialNetwork(@PathVariable Long id) {
        return socialNetworkService.findSocialNetwork(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/socialNetworks/edit/{id}")
    public ResponseEntity<SocialNetwork> editSocialNetwork(@PathVariable Long id, @RequestBody SocialNetwork socialNetwork) {
        SocialNetwork socialNetworkToUpdate = socialNetworkService.findSocialNetwork(id);
        socialNetworkToUpdate.setName(socialNetwork.getName());
        socialNetworkToUpdate.setLink(socialNetwork.getLink());
        socialNetworkToUpdate.setPosition(socialNetwork.getPosition());
        socialNetworkService.saveSocialNetwork(socialNetworkToUpdate);
        return ResponseEntity.ok(socialNetworkToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/socialNetworks/update/{id}")
    public ResponseEntity<SocialNetwork> updateSocialNetwork(@PathVariable Long id, @RequestBody SocialNetwork socialNetwork) {
        SocialNetwork socialNetworkToUpdate = socialNetworkService.findSocialNetwork(id);
        if (socialNetwork.getName() != null) {
            socialNetworkToUpdate.setName(socialNetwork.getName());
        }
        if (socialNetwork.getLink() != null) {
            socialNetworkToUpdate.setLink(socialNetwork.getLink());
        }
        if (socialNetwork.getPosition() != null) {
            socialNetworkToUpdate.setPosition(socialNetwork.getPosition());
        }
        socialNetworkService.saveSocialNetwork(socialNetworkToUpdate);
        return ResponseEntity.ok(socialNetworkToUpdate);
    }

}
