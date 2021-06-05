package pl.bieniek.database;

import pl.bieniek.Admin;
import pl.bieniek.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);

        return dbConnection;
    }

    public void sendInfo(){
    }

    public Employee getInfo(Employee employee){
        String query = "SELECT * FROM " + Const.INFO_TABLE + " WHERE " + Const.INFO_NAME + "=?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                employee.setOs(result.getString(Const.OS));
                employee.setCpu(result.getString(Const.CPU));
                employee.setGpu(result.getString(Const.GPU));
                employee.setRam(result.getString(Const.RAM));
            }

        }catch(SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
        return employee;
    }

    public List<Employee> getAllInfo(){
        String query = "SELECT * FROM " + Const.INFO_TABLE;
        List<Employee> allEmployees = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                allEmployees.add(new Employee(result.getString(Const.INFO_NAME),
                        result.getString(Const.OS),
                        result.getString(Const.CPU),
                        result.getString(Const.GPU),
                        result.getString(Const.RAM)
                        ));
            }

        }catch(SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
        return allEmployees;
    }

    public void clearDatabase() throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + Const.INFO_TABLE;
        Statement st = getDbConnection().createStatement();
        st.executeQuery(query);
    }

    public void deleteUser(Employee employee){
        String query = "DELETE FROM " + Const.INFO_TABLE + " WHERE " + Const.INFO_NAME + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString( 1, employee.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getAdmin(Admin admin) {
        ResultSet resultSet = null;

        if(!admin.getUsername().equals("") || !admin.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.ADMIN_TABLE + " WHERE " + Const.USERNAME
                    + "=?" + " AND " + Const.PASSWORD + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString( 1, admin.getUsername());
                preparedStatement.setString( 2, admin.getPassword());
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        } else {
            System.out.println("Please enter username and password");
        }
        return resultSet;
    }

    //Returns row with specified name from INFO_TABLE. Null if there isn't any.
    public ResultSet checkName (Employee employee){
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + Const.INFO_TABLE + " WHERE " + Const.INFO_NAME + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString( 1, employee.getName());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    //Adds new record to table
    public void addNewInfo(Employee employee){
        try{
            String query = "INSERT INTO " + Const.INFO_TABLE + " ("+Const.INFO_ID+","+Const.INFO_NAME+","+Const.OS+","+Const.CPU+","+Const.GPU+","+Const.RAM+") values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getOs());
            preparedStatement.setString(4, employee.getCpu());
            preparedStatement.setString(5, employee.getGpu());
            preparedStatement.setString(6, employee.getRam());
            preparedStatement.execute();
        }catch(SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
    }

    //Update row passed in parameter.
    public void updateInfo(Employee employee){
        try {
            String query = "UPDATE " + Const.INFO_TABLE + " SET "  +
                    Const.OS + " =?, " +
                    Const.CPU + " =?, " +
                    Const.GPU + " =?, " +
                    Const.RAM + " =? WHERE " +
                    Const.INFO_NAME + " =?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, employee.getOs());
            preparedStatement.setString(2, employee.getCpu());
            preparedStatement.setString(3, employee.getGpu());
            preparedStatement.setString(4, employee.getRam());
            preparedStatement.setString(5, employee.getName());
            preparedStatement.execute();
        }catch(SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
    }
}
