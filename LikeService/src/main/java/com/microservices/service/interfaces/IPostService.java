package com.microservices.service.interfaces;

import com.microservices.pojo.Post;

public interface IPostService {
    Post getPostById(int id);
}
