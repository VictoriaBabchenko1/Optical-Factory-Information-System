package org.example.controller;

import org.example.model.Client;
import org.example.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String listClients(Model model,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "contact", required = false) String contact,
                             @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "withProcessingOrders", required = false) String withProcessingOrdersParam,
                             @RequestParam(value = "groupBy", required = false) String groupByParam,
                             @RequestParam(value = "havingCount", required = false) String havingCount) {
        boolean withProcessingOrders = withProcessingOrdersParam != null;
        boolean groupBy = groupByParam != null;
        List<java.util.Map<String, Object>> clients = clientService.filterClients(name, contact, address, withProcessingOrders, groupBy, havingCount);
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("name", name);
        param.put("contact", contact);
        param.put("address", address);
        param.put("withProcessingOrders", withProcessingOrdersParam);
        param.put("groupBy", groupByParam);
        param.put("havingCount", havingCount);
        model.addAttribute("param", param);
        model.addAttribute("clients", clients);
        model.addAttribute("groupBy", groupBy);
        return "clients";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "client_form";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute Client client) {
        clientService.addClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "client_form";
    }

    @PostMapping("/edit/{id}")
    public String updateClient(@PathVariable int id, @ModelAttribute Client client) {
        client.setId(id);
        clientService.updateClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }
} 