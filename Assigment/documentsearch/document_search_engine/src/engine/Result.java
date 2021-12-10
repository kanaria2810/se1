package engine;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Result implements Comparable<Result>{
    
    Doc doc;
    List<Match> matches;

    public Result(Doc d, List<Match> matches) {
        this.doc = d;
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return this.matches;
    }
    
    public Doc getDoc() {
        return this.doc;
    }

    public int getMatchCount() {
        return matches.size();
    }

    public int getTotalFrequency() {
        if (getMatchCount() == 0) return 0;

        int totalFreq = 0;
        for (Match match : matches) {
            totalFreq += match.getFreq();
        }
        return totalFreq;
    }

    public double getAverageFirstIndex() {
        
        int count = getMatchCount();
        if (count == 0) return 0.0;

        int totalFirstIndex = 0;
        for (Match match : matches) {
            totalFirstIndex += match.getFirstIndex();
        }

        return totalFirstIndex / (double) count;

    }

    public String htmlHighlight() {

        StringBuilder htmlHighlightedResult = new StringBuilder();
        Set<String> keywords = getAllMatchedKeyWord();

        ArrayList<String> highlightedWordinTitle = new ArrayList<>();
        for (Word word : doc.getTitle()) {
            if (keywords.contains(word.getText().toLowerCase())) {
                highlightedWordinTitle.add(word.getPrefix()+"<u>"+word.getText()+"</u>"+word.getSuffix());
            } else {
                highlightedWordinTitle.add(word.toString());
            }
        }
        htmlHighlightedResult.append("<h3>").append(String.join(" ", highlightedWordinTitle)).append("</h3>");

        ArrayList<String> highlightedWordinBody = new ArrayList<>();
        for (Word word : doc.getBody()) {
            if (keywords.contains(word.getText().toLowerCase())) {
                highlightedWordinBody.add(word.getPrefix()+"<b>"+word.getText()+"</b>"+word.getSuffix());
            } else {
                highlightedWordinBody.add(word.toString());
            }
        }
        htmlHighlightedResult.append("<p>").append(String.join(" ", highlightedWordinBody)).append("</p>");


        return htmlHighlightedResult.toString();
    }


    @Override
    public int compareTo(Result o) {
        if (this.getMatchCount() != o.getMatchCount()) {
            return this.getMatchCount() - o.getMatchCount();
        } else if (this.getTotalFrequency() != o.getTotalFrequency()) {
            return this.getTotalFrequency() - o.getTotalFrequency();
        } else if (this.getAverageFirstIndex() != o.getAverageFirstIndex()) {
            return (this.getAverageFirstIndex() - o.getAverageFirstIndex() > 0) ? 1 : -1;
        }
        return (this.doc.equals(o.doc) ? 0 : -1);
    }

    private Set<String> getAllMatchedKeyWord() {
        Set<String> keywords = new LinkedHashSet<>();
        for (Match match : matches) {
            keywords.add(match.getWord().getText().toLowerCase());
        }
        return keywords;
    }

}
