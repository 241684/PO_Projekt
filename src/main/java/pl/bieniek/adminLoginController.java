package pl.bieniek;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bieniek.database.DatabaseHandler;

public class adminLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button btLogin;

    @FXML
    void initialize() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        btLogin.setOnAction(event -> {
            String loginText = tfUsername.getText().trim();
            String loginPwd = pfPassword.getText().trim();
            int counter = 0;

            Admin admin = new Admin(loginText, loginPwd);

            ResultSet adminRow = databaseHandler.getAdmin(admin);

            try {
                while (adminRow.next()) {
                    counter++;
                }
                if (counter == 1) {
                    System.out.println("Login successful!");
                    showAdminManagementScreen();
                } else {
                    Shaker shUsername = new Shaker(tfUsername);
                    shUsername.shake();
                    Shaker shPassword = new Shaker(pfPassword);
                    shPassword.shake();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void showAdminManagementScreen() {
        try {
            Parent root = FXMLLoader.load(new File("src/main/java/pl/bieniek/adminManagement.fxml").toURI().toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Client info");
            stage.show();
            // Hide this current window
            btLogin.getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}