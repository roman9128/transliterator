package rt;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransliteratorLogic {

    private final List<Character> cyrillicSymbols = Arrays.asList(
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
            'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я');
    private final List<String> latinSymbols = Arrays.asList(
            "A", "B", "V", "G", "D", "Je", "Jo", "Ž", "Z", "I", "J",
            "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F",
            "H", "C", "Č", "Š", "Ç", "`", "Y", "J", "E", "Ju", "Ja",
            "a", "b", "v", "g", "d", "je", "jo", "ž", "z", "i", "j",
            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f",
            "h", "c", "č", "š", "ç", "`", "y", "j", "e", "ju", "ja");
    private final List<Character> latinSymbolsForSofteningSounds = Arrays.asList(
            'Ž', 'C', 'Č', 'Š', 'Ç', 'ž', 'c', 'č', 'š', 'ç');
    private final List<Character> latinSymbolsForSCH = Arrays.asList(
            'Č', 'Ç', 'č', 'ç');
    private final List<Character> latinSymbolsForRegularConsonants = Arrays.asList(
            'B', 'V', 'G', 'D', 'Z', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'F', 'H',
            'b', 'v', 'g', 'd', 'z', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'f', 'h');
    private final List<Character> latinSymbolsForVowels = Arrays.asList(
            'A', 'I', 'O', 'U', 'Y', 'E', 'a', 'i', 'o', 'u', 'y', 'e');
    private final List<Character> latinSymbolsForI = Arrays.asList(
            'I', 'i');
    private final List<Character> latinSymbolsForJ = Arrays.asList(
            'J', 'j');

    private TransliteratorView view;

    public String translit(String cyrillicText) {
        StringBuilder builder = new StringBuilder();
        char[] letters = cyrillicText.toCharArray();
        for (char symbol : letters) {
            builder.append(replaceSymbol(symbol));
        }
        char[] textInLatin = builder.toString().toCharArray();
        return removeUnnecessarySymbols(textInLatin);
    }

    private String replaceSymbol(char symbol) {
        int i = cyrillicSymbols.indexOf(symbol);
        if (i == -1) return String.valueOf(symbol);
        else return latinSymbols.get(i);
    }

    private String removeUnnecessarySymbols(char[] textInLatin) {
        for (int i = 0; i < textInLatin.length - 1; i++) {
            char letter1 = textInLatin[i];
            char letter2 = textInLatin[i + 1];
            char letter3;
            try {
                letter3 = textInLatin[i + 2];
            } catch (Exception e) {
                letter3 = Character.MIN_VALUE;
            }
            if (latinSymbolsForRegularConsonants.contains(letter1)
                    && latinSymbolsForJ.contains(letter2)
                    && latinSymbolsForVowels.contains(letter3)) {
                textInLatin[i + 1] = 'i';
                letter2 = 'i';
            }
            if (latinSymbolsForSofteningSounds.contains(letter1)
                    && latinSymbolsForJ.contains(letter2)) {
                textInLatin[i + 1] = Character.MIN_VALUE;
            }
            if (letter1 == '`' && latinSymbolsForJ.contains(letter2)) {
                textInLatin[i] = Character.MIN_VALUE;
            }
            if (latinSymbolsForSofteningSounds.contains(letter1)
                    && !latinSymbolsForSCH.contains(letter1)
                    && latinSymbolsForI.contains(letter2)) {
                textInLatin[i + 1] = 'y';
            }
        }
        return IntStream.range(0, textInLatin.length)
                .mapToObj(i -> textInLatin[i])
                .filter(ch -> !ch.equals(Character.MIN_VALUE))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public void setView(TransliteratorView view) {
        this.view = view;
    }
}