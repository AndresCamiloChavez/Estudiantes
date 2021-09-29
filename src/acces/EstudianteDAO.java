package acces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.Estudiante;
import utilis.SQLServer;

public class EstudianteDAO {

    private Connection conexion;

    public ObservableList<Estudiante> getEstudiantes() {
        ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
        try {
            conexion = SQLServer.getConnection();
            if (conexion != null) {
                String query = "SELECT id, nombre, email, telefono FROM Estudiante";
                Statement statement = conexion.createStatement();
                ResultSet rs = statement.executeQuery(query);
                Estudiante estudiante = null;
                while (rs.next()) {
                    estudiante = new Estudiante(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("telefono"));
                    listaEstudiantes.add(estudiante);
                }
            }// cuidado aquí

        } catch (SQLException e) {
            System.out.println("LOG: ERROR: SQL(obtener estudiantes)" + e.toString());

        } catch (Exception e) {
            System.out.println("LOG: ERROR: (obtener estudiantes)" + e.toString());
        } finally {
            SQLServer.closeConnection(conexion);
        }
        return listaEstudiantes;
    }
    public ObservableList<Estudiante> getEstudiantesOrden(String valor) {
        ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
        try {
            conexion = SQLServer.getConnection();
            if (conexion != null) {
                String query = "SELECT id, nombre, email, telefono FROM Estudiante ORDER BY +"+valor.toLowerCase()+";";
                Statement statement = conexion.createStatement();
                ResultSet rs = statement.executeQuery(query);
                Estudiante estudiante = null;
                while (rs.next()) {
                    estudiante = new Estudiante(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("telefono"));
                    listaEstudiantes.add(estudiante);
                }
            }// cuidado aquí

        } catch (SQLException e) {
            System.out.println("LOG: ERROR: SQL(obtener estudiantes)" + e.toString());

        } catch (Exception e) {
            System.out.println("LOG: ERROR: (obtener estudiantes)" + e.toString());
        } finally {
            SQLServer.closeConnection(conexion);
        }
        return listaEstudiantes;
    }
    public boolean insertEstudiante(Estudiante estudiante) {
        boolean exito = false;
        try {
            conexion = SQLServer.getConnection();
            if (conexion != null) {
                String query = "INSERT INTO Estudiante(nombre, email, telefono) VALUES (?, ?, ?);";
                PreparedStatement statement = conexion.prepareStatement(query);
                statement.setString(1, estudiante.getNombre());
                statement.setString(2, estudiante.getEmail());
                statement.setString(3, estudiante.getTelefono());

                int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    exito = true;
                }

            }// cuidado aquí

        } catch (SQLException e) {
            System.out.println("LOG: ERROR: SQL(obtener estudiantes)" + e.toString());

        } catch (Exception e) {
            System.out.println("LOG: ERROR: (obtener estudiantes)" + e.toString());
        } finally {
            SQLServer.closeConnection(conexion);
        }
        return exito;
    }
    public boolean actualizarEstudiante(Estudiante estudiante) {
        boolean exito = false;
        try {
            conexion = SQLServer.getConnection();
            if (conexion != null) {
                String query = "UPDATE Estudiante SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
                PreparedStatement statement = conexion.prepareStatement(query);
                statement.setString(1, estudiante.getNombre());
                statement.setString(2, estudiante.getEmail());
                statement.setString(3, estudiante.getTelefono());
                statement.setInt(4, estudiante.getId());
                
                int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    exito = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("LOG: ERROR:acutalizar" + e.toString());

        } catch (Exception e) {
            System.out.println("LOG: ERROR: (obtener estudiantes)" + e.toString());
        } finally {
            SQLServer.closeConnection(conexion);
        }
        return exito;
    }
    
    public boolean eliminarEstudiante(Estudiante estudiante) {
        boolean exito = false;
        try {
            conexion = SQLServer.getConnection();
            if (conexion != null) {
                String query = "DELETE FROM Estudiante WHERE id = ?;";
                PreparedStatement statement = conexion.prepareStatement(query);
                statement.setInt(1,estudiante.getId());
                int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    exito = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("LOG: ERROR:eliminar" + e.toString());

        } catch (Exception e) {
            System.out.println("LOG: ERROR: (obtener estudiantes)" + e.toString());
        } finally {
            SQLServer.closeConnection(conexion);
        }
        return exito;
    }
    public ObservableList<Estudiante> getEstudiantesFiltrado(String texto) {
        ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
        try {
            conexion = SQLServer.getConnection();
            if (conexion != null) {
                String query = "SELECT id, nombre, email, telefono FROM Estudiante  WHERE nombre LIKE '%"+texto+"%' OR email LIKE '%"+texto+ "%'";
                Statement statement = conexion.createStatement();
                ResultSet rs = statement.executeQuery(query);
                Estudiante estudiante = null;
                while (rs.next()) {
                    estudiante = new Estudiante(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("telefono"));
                    listaEstudiantes.add(estudiante);
                }
            }// cuidado aquí

        } catch (SQLException e) {
            System.out.println("LOG: ERROR: SQL(obtener estudiantes)" + e.toString());

        } catch (Exception e) {
            System.out.println("LOG: ERROR: (obtener estudiantes)" + e.toString());
        } finally {
            SQLServer.closeConnection(conexion);
        }
        return listaEstudiantes;
    }
}
