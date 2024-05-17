package controller;

import entities.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.APIService;

import java.util.List;

@RestController
@RequestMapping("/apis")
public class APIController {

    @Autowired
    private APIService apiService;

    @GetMapping
    public List<API> getAllAPIs() {
        return apiService.getAllAPIs();
    }

    @GetMapping("/{apiId}")
    public API getAPIById(@PathVariable Long apiId) {
        return apiService.getAPIById(apiId);
    }

    @PostMapping
    public API createAPI(@RequestBody API api) {
        return apiService.saveAPI(api);
    }

    @DeleteMapping("/{apiId}")
    public void deleteAPI(@PathVariable Long apiId) {
        apiService.deleteAPI(apiId);
    }
}
