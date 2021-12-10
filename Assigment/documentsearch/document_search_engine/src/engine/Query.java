package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Query {
    
    List<Word> keywords;

    public Query(String searchPhrase) {

        keywords = new ArrayList<>();

        String[] content = searchPhrase.split("\\s+");

        for (String text : content) {
            Word word = Word.createWord(text);
            if (word.isKeyword()) {
                keywords.add(word);
            }
        }
    }

    public List<Word> getKeywords() {
        return this.keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        Map<String, Match> matchesWithWords = new HashMap<>();

        List<Word> docWords = new ArrayList<>();
        docWords.addAll(d.getTitle());
        docWords.addAll(d.getBody());

        for (int i = 0; i < docWords.size(); i++) {
            Word word = docWords.get(i);
            if (keywords.contains(word)) {
                String simpleWord = word.getText().toLowerCase();
                Match match = matchesWithWords.get(simpleWord);

                if (match == null) {
                    matchesWithWords.put(simpleWord, new Match(d, word, 1, i));
                } else {
                    matchesWithWords.get(simpleWord).increaseFreq();
                }
            }
        }
        
        //Sort matches by the position where keyword fist appears in the document
        SortedSet<Match> set = new TreeSet<>(matchesWithWords.values());
        return new ArrayList<>(set);
    }

}
