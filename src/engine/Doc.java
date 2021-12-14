package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {

    //Doc has a tilte and a boys

    List<Word> title;
    List<Word> body;
    
    public Doc (String content) {

        String[] parts = content.split("\n");

        title = new ArrayList<>();
        body = new ArrayList<>();

        if (parts.length == 2) {
            for (String rawtext : parts[0].split("\\s+")) {
                title.add(Word.createWord(rawtext));
            }

            for (String rawtext : parts[1].split("\\s+")) {
                body.add(Word.createWord(rawtext));
            }
        }

    }

    public List<Word> getTitle() {
        return this.title;
    }

    public List<Word> getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
    
        if (o.getClass() != this.getClass()) return false;

        Doc otherDoc = (Doc) o;
        return this.title.equals(otherDoc.getTitle()) && this.body.equals(otherDoc.getBody());
    }

}
