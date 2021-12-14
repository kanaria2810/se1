package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Word {

    public static Set<String> stopWords;
    
    static final int SINGLE_QUOTE_VALUE = 39;
    static final int HYPHEN_VALUE = 45;

    boolean isValidWord;
    String prefix;
    String textpart;
    String suffix;

    private Word (String prefix, String textpart, String suffix) {
        this.isValidWord = true;
        this.prefix = prefix;
        this.textpart = textpart;
        this.suffix = suffix;

        if (textpart.length() == 0) this.isValidWord = false;
        for (char c : textpart.toCharArray()) {
            if (!Character.isLetter(c) && (int) c != SINGLE_QUOTE_VALUE && (int) c != HYPHEN_VALUE) {
                this.isValidWord = false;
            }
        }
    }
    
    public boolean isKeyword() {
        //Is a valid word and not a stop words
        return isValidWord && (!stopWords.contains(this.textpart));
    }


    public static boolean loadStopWords(String docpath) {

        File stopwordFile = new File(docpath);
        if (!stopwordFile.exists()) {
            return false;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(docpath));

            String line = br.readLine();
            stopWords = new HashSet<>();

            while (line != null) {
                stopWords.add(line.trim());
                line = br.readLine();
            }

            br.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean equals(Object o) {

        if (o.getClass() != this.getClass()) return false;

        Word word = (Word) o;
        return this.getText().toLowerCase().equals(word.getText().toLowerCase());
    }

    @Override
    public String toString() {
        return prefix+textpart+suffix;
    }

    public static Word createWord(String rawText) {
        if (rawText.length() == 0) return new Word("", "", "");

        String prefix = "";
        String suffix = "";
        String textpart = "";

        for (char c : rawText.toCharArray()) {

            if (Character.isDigit(c)) {
                return new Word("", rawText, "");
            }

            if (!isCharInTextPart(c)) {
                if (textpart.length() == 0) {
                    prefix += String.valueOf(c);
                } else {
                    suffix += String.valueOf(c);
                }
                
            } else {
                if (suffix.length() != 0) {
                    suffix += String.valueOf(c);
                } else {
                    textpart += String.valueOf(c);
                }
            }
        }

        return new Word(prefix, textpart, suffix);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getText() {
        return this.textpart;
    }

    private static boolean isCharInTextPart(char c) {
        return Character.isLetter(c) || (int) c == HYPHEN_VALUE;
    }
}
