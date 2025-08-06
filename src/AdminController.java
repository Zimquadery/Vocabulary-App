import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminController {
    @FXML
    private VBox loginSection;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private VBox dashboardSection;
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
    @FXML
    private Button closeButton;

    private VocabularyManager vocabularyManager;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123";

    @FXML
    private void initialize() {
        loginSection.setVisible(true);
        dashboardSection.setVisible(false);

        passwordField.setOnAction(event -> handleLogin());

        usernameField.textProperty().addListener((obs, oldText, newText) -> loginErrorLabel.setText(""));
        passwordField.textProperty().addListener((obs, oldText, newText) -> loginErrorLabel.setText(""));
    }

    public void setVocabularyManager(VocabularyManager vocabularyManager) {
        this.vocabularyManager = vocabularyManager;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.trim().isEmpty()) {
            loginErrorLabel.setText("Please enter username");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            loginErrorLabel.setText("Please enter password");
            return;
        }
        if (ADMIN_USERNAME.equals(username.trim()) && ADMIN_PASSWORD.equals(password)) {
            loginSection.setVisible(false);
            dashboardSection.setVisible(true);
            clearAllFields();
        } else {
            loginErrorLabel.setText("Invalid username or password");
            passwordField.clear();
        }
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
        loginSection.setVisible(true);
        dashboardSection.setVisible(false);
        clearAllFields();
        usernameField.clear();
        passwordField.clear();
        loginErrorLabel.setText("");
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void clearAllFields() {
        addWordField.clear();
        addDefinitionArea.clear();
        updateWordField.clear();
        updateDefinitionArea.clear();
        statusLabel.setText("");
    }
}
