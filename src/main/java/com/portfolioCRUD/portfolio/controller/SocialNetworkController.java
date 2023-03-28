package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
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
@RequestMapping("/socialNetworks")
public class SocialNetworkController {

    private SocialNetworkService socialNetworkService;

    @Autowired
    public SocialNetworkController(SocialNetworkService socialNetworkService) {
        this.socialNetworkService = socialNetworkService;
    }

    ///socialNetworks?_sort=position&_order=asc
    @GetMapping("")
    public ResponseEntity<List<SocialNetwork>> getSocialNetworks(@RequestParam(required = false) String _sort,
                                                                @RequestParam(required = false) String _order) {
        if (_sort != null && _order != null) {
            if (_sort.equals("position") && _order.equals("asc")) {
                return ResponseEntity.ok(socialNetworkService.getSocialNetworksByOrderByPositionAsc());
            }
        }
        return ResponseEntity.ok(socialNetworkService.getSocialNetworks());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<SocialNetwork> saveSocialNetwork(@RequestBody SocialNetwork socialNetwork) {
        socialNetworkService.saveSocialNetwork(socialNetwork);
        return ResponseEntity.ok(socialNetwork);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteSocialNetwork(@PathVariable Long id) {
        socialNetworkService.deleteSocialNetwork(id);
        return ResponseEntity.ok(new Message("SocialNetwork deleted"));
    }

    @GetMapping("/{id}")
    public SocialNetwork findSocialNetwork(@PathVariable Long id) {
        return socialNetworkService.findSocialNetwork(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<SocialNetwork> editSocialNetwork(@PathVariable Long id, @RequestBody SocialNetwork socialNetwork) {
        SocialNetwork socialNetworkToUpdate = socialNetworkService.findSocialNetwork(id);
        socialNetworkToUpdate.setName(socialNetwork.getName());
        socialNetworkToUpdate.setLink(socialNetwork.getLink());
        socialNetworkToUpdate.setPosition(socialNetwork.getPosition());
        socialNetworkService.saveSocialNetwork(socialNetworkToUpdate);
        return ResponseEntity.ok(socialNetworkToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
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
        if (socialNetwork.getIcon() != null) {
            socialNetworkToUpdate.setIcon(socialNetwork.getIcon());
        }
        socialNetworkService.saveSocialNetwork(socialNetworkToUpdate);
        return ResponseEntity.ok(socialNetworkToUpdate);
    }

}
