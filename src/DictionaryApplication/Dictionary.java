package DictionaryApplication;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList<>();

    public void addWord(Word newword) {
        this.words.add(newword);
    }

    public void addWord(ArrayList<Word> words) {
        this.words.addAll(words);
    }

    public ArrayList<Word> getWords() {
        return this.words;
    }
}
