package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public static Connection getConector(){
        
        Connection conexao;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/osdatabase";
        String usuario = "Victor";
        String senha = "12345678";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            return conexao;
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
