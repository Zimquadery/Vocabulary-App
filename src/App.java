import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/MainView.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);
            scene.getStylesheets().add(getClass().getResource("ui/styles.css").toExternalForm());

            primaryStage.setTitle("Vocabulary App");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
