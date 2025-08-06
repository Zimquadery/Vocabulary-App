import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button adminButton;
    @FXML
    private TextArea resultArea;
    @FXML
    private Label statusLabel;

    private VocabularyManager vocabularyManager;

    @FXML
    private void initialize() {
        vocabularyManager = new VocabularyManager();

        searchField.setOnAction(event -> handleSearch());

        searchField.textProperty().addListener((obs, oldText, newText) -> {
            if (statusLabel != null) {
                statusLabel.setText("");
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText();

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            statusLabel.setText("Please enter a word to search");
            resultArea.clear();
            return;
        }

        String definition = vocabularyManager.searchWord(searchTerm);

        if (definition != null) {
            resultArea.setText("Word: " + searchTerm.toLowerCase() + "\n\nDefinition: " + definition);
            statusLabel.setText("");
        } else {
            resultArea.clear();
            statusLabel.setText("No results found for: " + searchTerm);
        }
    }

    @FXML
    private void handleAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/AdminLogin.fxml"));
            Scene adminScene = new Scene(loader.load(), 400, 350);
            adminScene.getStylesheets().add(getClass().getResource("ui/styles.css").toExternalForm());

            Stage adminStage = new Stage();
            adminStage.setTitle("Admin Login - Vocabulary Manager");
            adminStage.setScene(adminScene);
            adminStage.setResizable(false);
            adminStage.initModality(Modality.APPLICATION_MODAL);

            AdminLogin adminLoginController = loader.getController();
            adminLoginController.setVocabularyManager(vocabularyManager);

            adminStage.showAndWait();

        } catch (Exception e) {
            statusLabel.setText("Error opening admin panel: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
