package rt.transliterator.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rt.transliterator.service.AppService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private AppService service;
    private String cyrillicText = "";
    private String latinText = "";

    @GetMapping("/translit")
    public String showTranslit(Model model) {
        model.addAttribute("cyrillicText", cyrillicText);
        model.addAttribute("latinText", latinText);
        return "translit";
    }

    @PostMapping("/translit")
    @ResponseBody
    public Map<String,String> makeTranslit(@RequestBody Map<String,String> input) {
        cyrillicText = input.get("cyrillicText");
        latinText = service.makeTranslit(cyrillicText);
        Map<String, String> response = new HashMap<>();
        response.put("latinText", latinText);
        return response;
    }
}