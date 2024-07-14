package com.microservices.controller;

import com.microservices.service.interfaces.ILikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILikeService likeService;

    @GetMapping("/{accountId}/{postId}")
    public void AccountLikePost(@PathVariable String accountId, @PathVariable String postId) {
        likeService.likePost(accountId, postId);
    }
}
