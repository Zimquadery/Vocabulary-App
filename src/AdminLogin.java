import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLogin {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Button closeButton;

    private VocabularyManager vocabularyManager;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123";

    @FXML
    private void initialize() {
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
            openAdminDashboard();
        } else {
            loginErrorLabel.setText("Invalid username or password");
            passwordField.clear();
        }
    }

    private void openAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/AdminDashboard.fxml"));
            Scene dashboardScene = new Scene(loader.load(), 600, 650);
            dashboardScene.getStylesheets().add(getClass().getResource("ui/styles.css").toExternalForm());

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Admin Dashboard - Vocabulary Manager");
            dashboardStage.setScene(dashboardScene);
            dashboardStage.setResizable(true);
            dashboardStage.setMinWidth(550);
            dashboardStage.setMinHeight(450);
            AdminDashbord dashboardController = loader.getController();
            dashboardController.setVocabularyManager(vocabularyManager);

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();

            dashboardStage.show();

        } catch (Exception e) {
            loginErrorLabel.setText("Error opening admin dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
