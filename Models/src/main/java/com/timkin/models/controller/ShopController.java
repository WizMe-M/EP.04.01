package com.timkin.models.controller;

import com.timkin.models.entity.Motorcycle;
import com.timkin.models.entity.Client;
import com.timkin.models.repo.MotorcycleRepository;
import com.timkin.models.repo.ClientRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/shop")
@PreAuthorize("hasAnyAuthority('Seller', 'UnverifiedUser')")
public class ShopController {
    private final MotorcycleRepository motorcycleRepository;
    private final ClientRepository clientRepository;

    public ShopController(MotorcycleRepository motorcycleRepository, ClientRepository clientRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.clientRepository = clientRepository;
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

    @PreAuthorize("hasAnyAuthority('Seller')")
    @GetMapping("/purchase/{motorcycle_id}")
    public String openPurchasePage(
            @PathVariable("motorcycle_id") int id,
            Model model
    ) {
        assignForPurchasePage(model, id);
        return "shop/purchase";
    }

    @PreAuthorize("hasAnyAuthority('Seller')")
    @PostMapping("/purchase/{motorcycle_id}")
    public String purchase(
            @PathVariable("motorcycle_id") int id,
            @RequestParam("customer_id") UUID customer_id,
            Model model
    ) {
        Optional<Client> foundProfile = clientRepository.findById(customer_id);
        Optional<Motorcycle> foundBike = motorcycleRepository.findById(id);
        if (foundProfile.isEmpty() || foundBike.isEmpty()) {
            assignForPurchasePage(model, id);
            return "shop/purchase";
        }

        Client customer = foundProfile.get();
        Motorcycle bike = foundBike.get();
        customer.getPurchases().add(bike);
        clientRepository.save(customer);
        return "redirect:/shop";
    }

    private void assignForPurchasePage(Model model, int id) {
        Motorcycle motorcycle = motorcycleRepository.findById(id).orElseThrow();
        List<Client> alreadyBoughtCustomers = motorcycle.getCustomers();
        List<Client> allCustomers = clientRepository.findAll();
        List<Client> customers = exceptCustomers(allCustomers, alreadyBoughtCustomers);

        model.addAttribute("customers", customers);
        model.addAttribute("bike", motorcycle);
    }

    private static List<Client> exceptCustomers(List<Client> total, List<Client> exceptable) {
        List<Client> customers = new ArrayList<>();

        for (Client profile : total) {
            for (Client sub : exceptable) {
                if (profile.getId() == sub.getId()) {
                    break;
                }
            }
            customers.add(profile);
        }

        return customers;
    }
}
