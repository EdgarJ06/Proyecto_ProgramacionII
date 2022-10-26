package MODELO;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexionBd {
    
    Connection con = null;
    public Connection Conectar(){
        String db = "jdbc:mysql://localhost:3306/bd_ventas";
        String Usuario = "root";
        String Password = "722435";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(db, Usuario, Password);
            //JOptionPane.showMessageDialog(null, "Conexión Existosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error de Conexión"+e);
    }
        return con;
    }
}
