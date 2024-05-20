package com.example.platformtest.services;

import com.example.platformtest.entities.API;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface APIService {
    List<API> getAllAPIs();
    API getAPIById(Long apiId);
    API saveAPI(API api);
    void deleteAPI(Long apiId);

    List<API> findAllApisPaginated(int page, int size);
}
