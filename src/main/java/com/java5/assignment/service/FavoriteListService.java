package com.java5.assignment.service;

import com.java5.assignment.entity.FavoriteList;
import org.springframework.stereotype.Service;

@Service
public interface FavoriteListService {
    Long findByProductIdAndCustomerId(Long productId, Long customerId);

    boolean existsByProductIdAndCustomerId(Long productId, Long customerId);

    void add(FavoriteList favoriteList);

    void delete(Long idFavorite);
}
