
package utilis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLServer {
    public static Connection getConnection(){
        Connection con = null;
        String  conexionString = "jdbc:sqlserver://localhost:1433;databaseName=EstudiantesClase;user=accesEstudiante;password=1234";
        //Class.forName("com.microsoft.sqlserver.jdbc")
        try {
            con = DriverManager.getConnection(conexionString);
            if(con != null){
                System.out.println("LOG: INFO conectado!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("LOG: ERROR al momento de crear conexión con la base de datos");
        }
        return con;
    }
    public static void closeConnection(Connection con){
        try{
            con.close();
            System.out.println("LOG: INFO Conexión cerrada");

        }catch(SQLException e){
            System.out.println("LOG: ERROR Conexión no se cerro");
        }
    }
    
}
