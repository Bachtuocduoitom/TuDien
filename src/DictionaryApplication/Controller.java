package DictionaryApplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.Dictionary;

public class Controller implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private TextField addWordField;
    @FXML
    private TextField addMeanField;
    @FXML
    private TextField deleteField;
    @FXML
    private TextField changeWordField;
    @FXML
    private TextField changeMeanField;
    @FXML
    private TextArea textArea;
    @FXML
    Button buttonAddWord;
    @FXML
    Button buttonDeleteWord;
    @FXML
    Button buttonChange;

    public ListView listView = new ListView();
    public MenuBar menuBar;
    public DictionaryManagement dictionaryManagement = new DictionaryManagement();


    public void dictionaryLookup(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String word = searchField.getText().toString();
            textArea.setText(dictionaryManagement.dictionaryLookup(word));
        }
        if (searchField.getText().toString().equals("")) {
            textArea.clear();
        }
    }

    public void addNewWord(ActionEvent event) throws IOException {
        String word_target = addWordField.getText().toString();
        String word_explain = addMeanField.getText().toString();
        if (word_target.length() > 0 && word_explain.length() > 0) {
            List<String> words = dictionaryManagement.addNewWord(word_target, word_explain);
            dictionaryManagement.dictionaryExportToFile();
            ObservableList<String> newStringList = FXCollections.observableArrayList(words);
            Collections.sort(newStringList);
            listView.setItems(newStringList);
            addWordField.clear();
            addMeanField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo!!!");
            alert.setContentText("Từ đã được thêm.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo!!!");
            alert.setContentText("Bạn chưa điền từ cần thêm.");
            alert.showAndWait();
        }
    }


    public void deleteWord(ActionEvent event) throws IOException {
        //String selectedWord = dictionaryManagement.dictionaryLookup(listView.getSelectionModel().getSelectedItems().toString());
        String word_target = deleteField.getText().toString();
        if (word_target.length() > 0) {
            List<String> words = dictionaryManagement.deleteWord(word_target);
            dictionaryManagement.dictionaryExportToFile();
            ObservableList<String> newStringList = FXCollections.observableArrayList(words);
            Collections.sort(newStringList);
            listView.setItems(newStringList);
            deleteField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo!!!");
            alert.setContentText("Từ đã được xoá.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo!!!");
            alert.setContentText("Bạn chưa điền từ cần xoá.");
            alert.showAndWait();
        }
    }

    public void correctWord(ActionEvent event) throws IOException {
        String word_target = changeWordField.getText().toString();
        String word_explain = changeMeanField.getText().toString();
        if (dictionaryManagement.wordCorrection(word_target, word_explain)) {
            changeWordField.clear();
            changeMeanField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo!!!");
            alert.setContentText("Từ đã được sửa.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo!!!");
            alert.setContentText("Từ không tồn tại.");
            alert.showAndWait();
        }
    }

    public void wordSearcher(KeyEvent event) {
        String word = searchField.getText().toString();
        ArrayList<String> wordListView = dictionaryManagement.wordSearcher(word);
        ObservableList<String> theseWords = FXCollections.observableArrayList(wordListView);
        Collections.sort(theseWords);
        listView.setItems(theseWords);
        dictionaryManagement.getAddup().clear();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionaryManagement.insertFromFile();
        Collections.sort(dictionaryManagement.getStringListWord());
        ObservableList<String> stringObservableList = FXCollections.observableArrayList(dictionaryManagement.getStringListWord());
        listView.setItems(stringObservableList);

    }
}
