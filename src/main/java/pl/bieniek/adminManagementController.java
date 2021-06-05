package pl.bieniek;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.bieniek.database.Const;
import pl.bieniek.database.DatabaseHandler;

public class adminManagementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btReport;

    @FXML
    private Button btDelete;

    @FXML
    private Button btGetInfo;

    @FXML
    private Button btClear;

    @FXML
    private TextField tfName;

    @FXML
    private Text lbDisplayInfo;

    @FXML
    private Button btReturn;

    @FXML
    void initialize() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        btDelete.setOnAction(event -> {
            String name = tfName.getText().trim().toLowerCase();
            if(!name.equals("")){
                Employee employee = new Employee(name);
                ResultSet set = databaseHandler.checkName(employee);
                try {
                    if(set.next()) {
                        databaseHandler.deleteUser(employee);
                        lbDisplayInfo.setText(employee.getName()+ " correctly deleted.");
                    }else{
                        lbDisplayInfo.setText(employee.getName()+ " don't exist in database.");
                        Shaker shDisplay = new Shaker(lbDisplayInfo);
                        shDisplay.shake();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }else{
                Shaker shName = new Shaker(tfName);
                shName.shake();
                tfName.setPromptText("Input correct name!");
            }
        });

        btClear.setOnAction(event -> {
            try {
                databaseHandler.clearDatabase();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        btGetInfo.setOnAction(event -> {
            String name = tfName.getText().trim().toLowerCase();
            if(!name.equals("")){
                Employee employee = new Employee(name);
                ResultSet set = databaseHandler.checkName(employee);
                try {
                    if(set.next()) {
                        employee = databaseHandler.getInfo(employee);
                        lbDisplayInfo.setText("Empolyee info: \n"+ "OS: " + employee.getOs() + "\n" + "CPU: " + employee.getCpu()+ "\n" + "GPU: " + employee.getGpu()+ "\n" + "RAM: " + employee.getRam());                    }else{
                        lbDisplayInfo.setText(employee.getName()+ " don't exist in database.");
                        Shaker shDisplay = new Shaker(lbDisplayInfo);
                        shDisplay.shake();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }else{
                Shaker shName = new Shaker(tfName);
                shName.shake();
                tfName.setPromptText("Input correct name!");
            }
        });

        btReport.setOnAction(event -> {
            FileChooser saveDialog = new FileChooser();
            Stage stage = new Stage();
            saveDialog.setTitle("Select location to save report");
            saveDialog.setInitialFileName("report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("_dd-MM-yyyy_HH-mm-ss")));
            saveDialog.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv"),
                    new FileChooser.ExtensionFilter("TXT", "*.txt"));
            saveDialog.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File file = saveDialog.showSaveDialog(stage);
            if (file != null) {
                if (generateCSV(file)) {
                    this.lbDisplayInfo.setText("Report sucessfully saved to: " + file.getAbsolutePath());
                } else {
                    this.lbDisplayInfo.setText("Something goes wrong with generating a raport.");
                }
            } else {
                this.lbDisplayInfo.setText("Filepath not selected! ");
            }
        });

        btReturn.setOnAction(event ->
                setBtReturn()
        );
    }

    private void setBtReturn() {
        try {
            Parent root = FXMLLoader.load(new File("src/main/java/pl/bieniek/adminLogin.fxml").toURI().toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Client info");
            stage.show();
            // Hide this current window
            btReturn.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean generateCSV(File file) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        List<Employee> all = databaseHandler.getAllInfo();

        try {
            FileWriter outputFile = new FileWriter(file);
            outputFile.append("Name,OS,CPU,GPU,RAM\n");

            for (Employee tempEmployee : all) {
                outputFile.append(tempEmployee.getName() + "," +
                        tempEmployee.getOs() + "," +
                        tempEmployee.getCpu() + "," +
                        tempEmployee.getGpu() + "," +
                        tempEmployee.getRam() + "\n");
            }

            outputFile.flush();
            outputFile.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
