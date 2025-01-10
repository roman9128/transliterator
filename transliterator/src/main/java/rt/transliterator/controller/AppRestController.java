package rt.transliterator.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rt.transliterator.service.AppService;

@RestController
@AllArgsConstructor
public class AppRestController {

    private AppService service;

    @GetMapping("/rest")
    public String makeTranslit(@RequestBody String cyrillicText) {
        return service.makeTranslit(cyrillicText);
    }
}