package com.timkin.models.controller;

import com.timkin.models.entity.Motorcycle;
import com.timkin.models.entity.Profile;
import com.timkin.models.repo.MotorcycleRepository;
import com.timkin.models.repo.ProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final MotorcycleRepository motorcycleRepository;
    private final ProfileRepository profileRepository;

    public ShopController(MotorcycleRepository motorcycleRepository, ProfileRepository profileRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public String index() {
        return "redirect:/shop/page";
    }

    @GetMapping("/page")
    public String shopPage(Model model) {
        List<Motorcycle> all = motorcycleRepository.findAll();
        model.addAttribute("bikes", all);
        return "shop/shop";
    }

    @GetMapping("/purchase/{motorcycle_id}")
    public String openPurchasePage(
            @PathVariable("motorcycle_id") int id,
            Model model
    ) {
        Motorcycle motorcycle = motorcycleRepository.findById(id).orElseThrow();
        List<Profile> alreadyBoughtCustomers = motorcycle.getCustomers();
        List<Profile> allCustomers = profileRepository.findAll();
        List<Profile> customers = exceptCustomers(allCustomers, alreadyBoughtCustomers);
        model.addAttribute("customers", customers);
        return "shop/purchase";
    }

    private static List<Profile> exceptCustomers(List<Profile> total, List<Profile> exceptable) {
        List<Profile> customers = new ArrayList<>();

        for (Profile profile : total) {
            for (Profile sub : exceptable) {
                if (profile.getId().equals(sub.getId())) {
                    break;
                }
            }
            customers.add(profile);
        }

        return customers;
    }
}
