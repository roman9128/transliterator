package rt;

public class Service {
    private final TransliteratorView view = new TransliteratorGUI();
    private final TransliteratorLogic logic = new TransliteratorLogic();

    public Service() {
        logic.setView(view);
        view.setTransliterator(logic);
    }
}
