package rt;

public interface TransliteratorView {

    void makeTranslit(String cyrillicText);
    void setTransliterator(TransliteratorLogic transliterator);
}
