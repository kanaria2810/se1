package engine;

public class Match implements Comparable<Match> {
    //Doc contains a word => all document that are related to a Query
    //When matching a document against a word, the title and body should be combined into a single list of words
    Doc doc;
    private Word word;
    private int freq;
    private int firstIndex;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.doc = d;
        this.word = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    public int getFreq() {
        return this.freq;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    public Word getWord() {
        return this.word;
    }

    public void increaseFreq() {
        this.freq += 1;
    }
    
    @Override
    public int compareTo(Match o) {
        return this.getFirstIndex() - o.getFirstIndex();
    }

}
