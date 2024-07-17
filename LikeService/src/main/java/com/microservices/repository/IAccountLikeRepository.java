package com.microservices.repository;

import com.microservices.entity.AccountLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountLikeRepository extends JpaRepository<AccountLike, Long> {
    Optional<AccountLike> findByAccountIdAndLikedId(int accountId, int likedId);
}
