package DictionaryApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();
    private static ArrayList<String> addup =new ArrayList<>();
    private static List<String> stringListWord = new ArrayList<>();

    public Dictionary getDictionary() {
        return dictionary;
    }

    public static ArrayList<String> getAddup() {
        return addup;
    }

    /**public static ArrayList<String> getRemoveout() {
     return removeout;
     }*/

    public static List<String> getStringListWord() {
        return stringListWord;
    }

    public void insertFromCommandline() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < n; i++) {
            String word_target = scan.nextLine();
            String word_explain = scan.nextLine();
            Word newword = new Word(word_target, word_explain);
            dictionary.addWord(newword);
        }
    }

    public void insertFromFile()  {
        String fileAddress = "src\\dictionaries.txt";
        Scanner scan;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileAddress);
            scan = new Scanner(fileInputStream);
            while (scan.hasNextLine()) {
                String[] tu = scan.nextLine().split("\t", 2);
                String word_target = tu[0];
                String word_explain = tu[1];
                Word newword = new Word(word_target, word_explain);
                dictionary.addWord(newword);
            }
            for (Word i : dictionary.getWords()) {
                stringListWord.add(i.getWord_target());
            }
        } catch (IOException ex) {
            System.out.println("ERROR!");
        }
    }

    public String dictionaryLookup(String wordNeed) {

        for (Word i : dictionary.getWords()) {
            if (i.getWord_target().equalsIgnoreCase(wordNeed)) {
                return i.getWord_explain();
            } else if (i.getWord_explain().equalsIgnoreCase(wordNeed)) {
                return i.getWord_target();
            }
        }
        return "";
    }

    public ArrayList<String> addNewWord(String word_target, String word_explain) {
        addup.clear();
        Word newword = new Word(word_target, word_explain);
        dictionary.getWords().add(newword);
        for (Word i : dictionary.getWords()) {
            addup.add(i.getWord_target());
        }
        return addup;
    }

    public ArrayList<String> deleteWord(String word) {
        int m = 0;
        for (int i = 0; i < dictionary.getWords().size(); i++) {
            Word words = dictionary.getWords().get(i);
            if (words.getWord_target().toLowerCase().equals(word.toLowerCase())) {
                dictionary.getWords().remove(i);
            }
            else
            {
                addup.add(dictionary.getWords().get(i).getWord_target());
            }
        }
        return addup;
    }

    public boolean wordCorrection(String word_target, String word_explain) throws IOException {
        boolean flag = false;
        for (Word i : dictionary.getWords()) {
            if (i.getWord_target().equalsIgnoreCase(word_target)) {
                i.setWord_explain(word_explain);
                flag = true;
                break;
            }
        }
        dictionaryExportToFile();
        return flag;
    }

    public ArrayList<String> wordSearcher(String word) {
        for (Word i : dictionary.getWords()) {
            if (i.getWord_target().startsWith(word.toLowerCase())) {
                addup.add(i.getWord_target());
            }
        }
        return addup;
    }

    public void dictionaryExportToFile() throws IOException {
        String fileAddress = "src\\dictionaries.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileAddress);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        for (Word i : dictionary.getWords()) {
            outputStreamWriter.write(i.getWord_target().toLowerCase() + "\t");
            outputStreamWriter.write(i.getWord_explain().toLowerCase() + "\n");
        }
        outputStreamWriter.flush();
    }


}
