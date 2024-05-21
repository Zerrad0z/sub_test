package com.example.platformtest.services;

import com.example.platformtest.entities.API;
import com.example.platformtest.repositories.APIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APIServiceImpl implements APIService {

    private final APIRepository apiRepository;

    @Autowired
    public APIServiceImpl(APIRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public List<API> getAllAPIs() {
        return apiRepository.findAll();
    }

    @Override
    public API getAPIById(Long apiId) {
        return apiRepository.findById(apiId).orElse(null);
    }

    @Override
    public API saveAPI(API api) {
        return apiRepository.save(api);
    }

    @Override
    public void deleteAPI(Long apiId) {
        apiRepository.deleteById(apiId);
    }

    @Override
    public List<API> findAllApisPaginated(int page, int size) {
        Page<API> apiPage = apiRepository.findAll(PageRequest.of(page, size));
        return apiPage.getContent();
    }
}