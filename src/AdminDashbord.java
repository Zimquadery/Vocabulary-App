import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminDashbord {

    @FXML
    private TextField addWordField;
    @FXML
    private TextArea addDefinitionArea;
    @FXML
    private Button addButton;
    @FXML
    private TextField updateWordField;
    @FXML
    private TextArea updateDefinitionArea;
    @FXML
    private Button searchWordButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Button logoutButton;

    private VocabularyManager vocabularyManager;

    public void setVocabularyManager(VocabularyManager vocabularyManager) {
        this.vocabularyManager = vocabularyManager;
    }

    @FXML
    private void handleAddWord() {
        String word = addWordField.getText();
        String definition = addDefinitionArea.getText();

        if (word == null || word.trim().isEmpty()) {
            statusLabel.setText("Please enter a word");
            return;
        }

        if (definition == null || definition.trim().isEmpty()) {
            statusLabel.setText("Please enter a definition");
            return;
        }

        if (vocabularyManager.addWord(word, definition)) {
            statusLabel.setText("Word added successfully: " + word.toLowerCase());
            addWordField.clear();
            addDefinitionArea.clear();
        } else {
            statusLabel.setText("Failed to add word. Word may already exist.");
        }
    }

    @FXML
    private void handleSearchWordForUpdate() {
        String word = updateWordField.getText();

        if (word == null || word.trim().isEmpty()) {
            statusLabel.setText("Please enter a word to search");
            return;
        }

        String definition = vocabularyManager.searchWord(word);

        if (definition != null) {
            updateDefinitionArea.setText(definition);
            statusLabel.setText("Word found. You can now edit the definition.");
        } else {
            updateDefinitionArea.clear();
            statusLabel.setText("Word not found: " + word);
        }
    }

    @FXML
    private void handleUpdateWord() {
        String word = updateWordField.getText();
        String newDefinition = updateDefinitionArea.getText();

        if (word == null || word.trim().isEmpty()) {
            statusLabel.setText("Please enter a word");
            return;
        }

        if (newDefinition == null || newDefinition.trim().isEmpty()) {
            statusLabel.setText("Please enter a definition");
            return;
        }

        if (vocabularyManager.updateWord(word, newDefinition)) {
            statusLabel.setText("Word updated successfully: " + word.toLowerCase());
        } else {
            statusLabel.setText("Failed to update word. Word may not exist.");
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }
}
