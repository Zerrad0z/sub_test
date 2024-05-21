package com.example.platformtest.controllers;

import com.example.platformtest.repositories.APIRepository;
import com.example.platformtest.entities.API;
import com.example.platformtest.services.APIService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class APIController {

    @Autowired
    private APIService apiService;
    @Autowired
    private APIRepository apiRepository;

    @GetMapping("index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "5") int s,
                        @RequestParam(name = "mc", defaultValue = "") String mc) {
        Page<API> pageAPI = apiRepository.chercher("%" + mc + "%", PageRequest.of(p, s));
        model.addAttribute("listAPI", pageAPI.getContent());
        int[] pages = new int[pageAPI.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pageCourante", p);
        model.addAttribute("motCle", mc);
        // Check the user's roles
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Add the user's role to the model
        model.addAttribute("isAdmin", isAdmin);
        return "api/apis";
    }
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(Long id,String motCle,int page,int size){
        apiRepository.deleteById(id);
        return "redirect:/index?page="+page+"&size="+size+"&motCle="+motCle;
    }
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String formApi(Model model){
        model.addAttribute("api",new API());
        return "api/apis";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, Long id) {
        API api = apiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("API not found"));
        model.addAttribute("api", api);
        return "api/editAPI"; // Make sure this is the correct name of your form view
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @Valid API api, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editAPI"; // Return the form view with error messages
        }
        API savedApi = apiService.saveAPI(api);
        model.addAttribute("api", savedApi);
        return "api/confirmation"; // Redirect to the confirmation page
    }
}
