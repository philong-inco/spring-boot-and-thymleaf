package com.java5.assignment.service.impl;

import com.java5.assignment.entity.FavoriteList;
import com.java5.assignment.repository.FavoriteListRepository;
import com.java5.assignment.service.FavoriteListService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FavoriteListServiceImpl implements FavoriteListService {
    private FavoriteListRepository repository;

    public FavoriteListServiceImpl(FavoriteListRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long findByProductIdAndCustomerId(Long productId, Long customerId) {
        return repository.findByProductIdAndCustomerId(productId, customerId);
    }

    @Override
    public boolean existsByProductIdAndCustomerId(Long productId, Long customerId) {
        if (repository.existsByProductIdAndCustomerId(productId, customerId).size() > 0)
            return true;
        return false;
    }

    @Override
    public void add(FavoriteList favoriteList) {
        repository.save(favoriteList);
    }

    @Override
    public void delete(Long idFavorite) {
        repository.deleteById(idFavorite);
    }
}
