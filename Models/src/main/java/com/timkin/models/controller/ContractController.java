package com.timkin.models.controller;

import com.timkin.models.entity.Consumer;
import com.timkin.models.entity.Contract;
import com.timkin.models.entity.Motorcycle;
import com.timkin.models.repo.ConsumerRepository;
import com.timkin.models.repo.ContractRepository;
import com.timkin.models.repo.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("contracts")
public class ContractController {
    private final ContractRepository repository;
    private final ConsumerRepository consumerRepository;
    private final MotorcycleRepository motorcycleRepository;

    public ContractController(ContractRepository contractRepository, ConsumerRepository consumerRepository,
                              MotorcycleRepository motorcycleRepository) {
        this.repository = contractRepository;
        this.consumerRepository = consumerRepository;
        this.motorcycleRepository = motorcycleRepository;
    }

    @GetMapping
    public String index() {
        return "redirect:/contracts/all";
    }

    @GetMapping("/all")
    public String openAll(Model model) {
        List<Contract> all = repository.findAll();
        model.addAttribute("contracts", all);
        return "contracts/all";
    }

    @GetMapping("{id}")
    public String open(
            @PathVariable int id,
            Model model
    ) {
        Optional<Contract> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/contracts/all";
        }
        model.addAttribute("contract", found.get());
        return "contracts/details";
    }

    @GetMapping("/add")
    public String openAdd(
            @ModelAttribute Contract contract,
            Model model
    ) {
        List<Consumer> consumers = consumerRepository.findAll();
        List<Motorcycle> motorcycles = motorcycleRepository.findAll();
        model.addAttribute("consumers", consumers);
        model.addAttribute("motorcycles", motorcycles);
        return "contracts/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute @Valid Contract contract,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<Consumer> consumers = consumerRepository.findAll();
            List<Motorcycle> motorcycles = motorcycleRepository.findAll();
            model.addAttribute("consumers", consumers);
            model.addAttribute("motorcycles", motorcycles);
            return "contracts/add";
        }

        repository.save(contract);
        return "redirect:/contracts/all";
    }

    @GetMapping("{id}/edit")
    public String openEdit(
            @PathVariable int id,
            Model model
    ) {
        Optional<Contract> found = repository.findById(id);
        if (found.isEmpty()) {
            List<Consumer> all = consumerRepository.findAll();
            model.addAttribute("consumers", all);
            return "redirect:/contracts/all";
        }
        model.addAttribute("contract", found.get());
        return "contracts/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(
            @PathVariable int id,
            @ModelAttribute @Valid Contract contract,
            BindingResult validationState
    ) {
        Optional<Contract> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/contracts";
        }

        if (validationState.hasFieldErrors("supplyDate")) {
            return "contracts/edit";
        }
        Contract edited = found.get();
        edited.setSupplyDate(contract.getSupplyDate());
        repository.save(edited);
        return "redirect:/contracts/all";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/contracts/all";
    }
}
