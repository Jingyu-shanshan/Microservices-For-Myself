package com.microservices.controller;

import com.microservices.service.interfaces.ILikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> AccountLikePost(@PathVariable int accountId, @PathVariable int postId) {
        likeService.likePost(accountId, postId);
        return new ResponseEntity<>("Liked!", HttpStatus.OK);
    }

    @GetMapping("/unlike/{accountId}/{postId}")
    public ResponseEntity<String> AccountUnlikePost(@PathVariable int accountId, @PathVariable int postId) {
        likeService.unlikePost(accountId, postId);
        return new ResponseEntity<>("Unliked!", HttpStatus.OK);
    }
}
