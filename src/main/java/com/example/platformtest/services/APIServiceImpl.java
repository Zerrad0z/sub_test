package com.example.platformtest.services;

import com.example.platformtest.entities.API;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class APIServiceImpl implements APIService {
    @Override
    public List<API> getAllAPIs() {
        return List.of();
    }

    @Override
    public API getAPIById(Long apiId) {
        return null;
    }

    @Override
    public API saveAPI(API api) {
        return null;
    }

    @Override
    public void deleteAPI(Long apiId) {

    }

    @Override
    public List<API> findAllApisPaginated(int page, int size) {
        return List.of();
    }
}
