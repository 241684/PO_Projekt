package pl.bieniek;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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

        });
    }
}
