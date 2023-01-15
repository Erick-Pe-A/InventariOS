package Modelo;

//Creando la clase que nos conecta a nuestra base de datos y  a nuestra tabla 
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class Conexion {
  public static final String URL="jdbc:mysql://localhost:3306/inventarios?autoReconnect=true&useSSL=false";
    public static final String usuario="root";
    public static final String contraseña="18011272";
    
    public Connection getConnection(){
        Connection conexion=null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion= (Connection) DriverManager.getConnection(URL, usuario, contraseña);
        }catch(Exception e){
            System.err.println("Error"+e);
        }
        return conexion;
    }
}