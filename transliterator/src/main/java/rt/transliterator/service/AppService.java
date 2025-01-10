package rt.transliterator.service;

import org.springframework.stereotype.Service;
import rt.transliterator.model.Transliterator;

@Service
public class AppService {

    private Transliterator transliterator;

    public AppService() {
        this.transliterator = new Transliterator();
    }

    public String makeTranslit(String cyrillicText) {
        return transliterator.translit(cyrillicText);
    }
}