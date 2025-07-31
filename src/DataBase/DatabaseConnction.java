/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
public class DatabaseConnction {
    public static Connection con = null;
    private DatabaseConnction(){}
    public static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Driver not Found : " + ex.getMessage());
        }
    }
    public static Connection getInstance() {
        if (con == null) {
            try {
                loadDriver();
                con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/library", "root", "");
                JOptionPane.showMessageDialog(null, "Connection Establish:");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Connection Failed : " + ex.getMessage());
                System.exit(0);
            }
        }
        return con;
    }
}
