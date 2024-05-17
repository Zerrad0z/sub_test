package service;

import entities.API;

import java.util.List;

public interface APIService {
    List<API> getAllAPIs();
    API getAPIById(Long apiId);
    API saveAPI(API api);
    void deleteAPI(Long apiId);
}
