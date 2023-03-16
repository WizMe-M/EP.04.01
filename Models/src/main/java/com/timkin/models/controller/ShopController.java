package com.timkin.models.controller;

import com.timkin.models.entity.Client;
import com.timkin.models.entity.Motorcycle;
import com.timkin.models.entity.Purchase;
import com.timkin.models.repo.PurchaseRepository;
import com.timkin.models.repo.ClientRepository;
import com.timkin.models.repo.MotorcycleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
@PreAuthorize("hasAnyAuthority('Seller', 'UnverifiedUser')")
public class ShopController {
    private final MotorcycleRepository motorcycleRepository;
    private final ClientRepository clientRepository;
    private final PurchaseRepository purchaseRepository;

    public ShopController(MotorcycleRepository motorcycleRepository, ClientRepository clientRepository,
                          PurchaseRepository purchaseRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.clientRepository = clientRepository;
        this.purchaseRepository = purchaseRepository;
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
            @PathVariable("motorcycle_id") int motorcycleId,
            @RequestParam("customer_id") int customerId,
            Model model
    ) {
        Optional<Client> foundProfile = clientRepository.findById(customerId);
        Optional<Motorcycle> foundBike = motorcycleRepository.findById(motorcycleId);
        if (foundProfile.isEmpty() || foundBike.isEmpty()) {
            assignForPurchasePage(model, motorcycleId);
            return "shop/purchase";
        }

        Client client = foundProfile.get();
        Motorcycle bike = foundBike.get();

        Purchase purchase = new Purchase();
        purchase.setPurchaseSum(bike.getPrice());
        purchase.setClient(client);
        purchase.setMotorcycle(bike);
        purchaseRepository.save(purchase);

        return "redirect:/shop";
    }

    @GetMapping("/return/{purchase_id}")
    public String returnPurchase(@PathVariable("purchase_id") int id) {
        Optional<Purchase> found = purchaseRepository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/shop";
        }

        Purchase purchase = found.get();
        if (purchase.isReturned()) {
            return "redirect:/shop";
        }

        purchase.setReturnDate(Date.from(Instant.now()));
        purchaseRepository.save(purchase);
        return "redirect:/shop";
    }

    private void assignForPurchasePage(Model model, int id) {
        Motorcycle motorcycle = motorcycleRepository.findById(id).orElseThrow();
        List<Client> clients = clientRepository.findAll();

        model.addAttribute("customers", clients);
        model.addAttribute("bike", motorcycle);
    }
}
