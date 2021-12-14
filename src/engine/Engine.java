package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;


public class Engine {

    List<Doc> docs;

    public Engine() {
        docs = new ArrayList<>();
    }

    public int loadDocs(String directory) {
        File folder = new File(directory);

        if (!folder.exists()) return 0;

        File[] listFiles = folder.listFiles();

        try {
            
            for (File file : listFiles) {
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

                String line = br.readLine();
                String rawText = "";
                
                while (line != null) {
                    rawText += line.trim();
                    rawText += "\n";
                    line = br.readLine();
                }

                Doc doc = new Doc(rawText);
                docs.add(doc);

                br.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return listFiles.length;
    }

    public Doc[] getDocs() {
        return this.docs.toArray(Doc[] :: new);
    }

    public List<Result> search(Query q) {

        NavigableSet<Result> results = new TreeSet<>();

        for (Doc doc : docs) {
            List<Match> matches = q.matchAgainst(doc);
            if (matches.size() != 0) {
                results.add(new Result(doc, matches));
            }
        }

        return new ArrayList<>(results.descendingSet());
    }

    public String htmlResult(List<Result> results) {
        StringBuilder finalResult = new StringBuilder();

        for (Result result : results) {
            finalResult.append(result.htmlHighlight());
        }

        return finalResult.toString();
    }
}
