package com.microservices.service;

import com.microservices.exception.LikeApiException;
import com.microservices.pojo.Post;
import com.microservices.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService implements IPostService {
    @Value("${restTemplate.post-service.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Post getPostById(int id) {
        String url = "http://" + host + "/api/posts/" + id;
        try{
            return restTemplate.getForObject(url, Post.class);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void update(Post post) {
        String url = "http://" + host + "/api/posts/" + post.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Post> entity = new HttpEntity<>(post, headers);

        try{
            restTemplate.exchange(url, HttpMethod.PATCH, entity, Post.class);
        }catch (Exception e){
            throw new LikeApiException(HttpStatus.BAD_REQUEST, "Call post api error.");
        }
    }
}
