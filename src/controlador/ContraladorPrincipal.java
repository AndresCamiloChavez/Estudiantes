package controlador;

import acces.EstudianteDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
//import javafx.scene.input.KeyEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import models.Estudiante;

public class ContraladorPrincipal implements Initializable {

    boolean hayActu = true;
    int contador = 0;
    Estudiante estudiante;

    EstudianteDAO estudianteDAO = new EstudianteDAO();
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Button btnInsetar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn columId;
    @FXML
    private TableView<Estudiante> tableEstu;
    @FXML
    private TextField txtBuscar;
    @FXML
    private ChoiceBox<String> selector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstudiantes();
        ObservableList<String> opciones = FXCollections.observableArrayList();
        opciones.add("Nombre");
        opciones.add("Email");
        opciones.add("Telefono");
        selector.setItems(opciones);
        selector.setValue("Nombre");
        selector.setOnAction((event) -> {
          
            cargarEstudiantesOrden(selector.getValue());
        });
        txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                cargarEstudiantesFiltrados();
            }
        });
      

    }

    public void cargarEstudiantesOrden(String orden) {
        ObservableList<Estudiante> lista = estudianteDAO.getEstudiantesOrden(orden);
        tableEstu.setItems(lista);
        this.columId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        tableEstu.setEditable(true);
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefono.setCellFactory(TextFieldTableCell.forTableColumn());

    }
    public void cargarEstudiantes() {
        ObservableList<Estudiante> lista = estudianteDAO.getEstudiantes();
        tableEstu.setItems(lista);
        this.columId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        tableEstu.setEditable(true);
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefono.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    @FXML
    private void click(ActionEvent event) {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        Estudiante estudiante = new Estudiante(nombre, email, telefono);
        if (!nombre.isEmpty() && !email.isEmpty() && !telefono.isEmpty()) {
            if (estudianteDAO.insertEstudiante(estudiante)) {
                txtNombre.setText("");
                txtEmail.setText("");
                txtTelefono.setText("");
                txtNombre.setStyle("-fx-border-color: none");
                txtEmail.setStyle("-fx-border-color: none");
                txtTelefono.setStyle("-fx-border-color: none");
                mostrarAlertaPersonalizada("EXITO!", "Se inserto el estudiante con el nombre de: " + estudiante.getNombre() + " correctamente", Alert.AlertType.INFORMATION);

            }
        } else {
            mostrarAlertaPersonalizada("ERROR", "Hay campos vacios (Intente de nuevo)", Alert.AlertType.ERROR);

            if (nombre.isEmpty()) {
                txtNombre.setStyle("-fx-border-color: red");
            } else {
                txtNombre.setStyle("-fx-border-color: none");
            }
            if (email.isEmpty()) {
                txtEmail.setStyle("-fx-border-color: red");
            } else {
                txtEmail.setStyle("-fx-border-color: none");
            }
            if (telefono.isEmpty()) {
                txtTelefono.setStyle("-fx-border-color: red");
            } else {
                txtTelefono.setStyle("-fx-border-color: none");
            }
        }
        cargarEstudiantes();
    }

    public void mostrarAlertaPersonalizada(String title, String contenido, Alert.AlertType estado) {
        Alert alert = new Alert(estado);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.show();
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        if (tableEstu.getSelectionModel().getSelectedItem().getId() >= 0) {
            if (estudianteDAO.eliminarEstudiante(tableEstu.getSelectionModel().getSelectedItem())) {
                mostrarAlertaPersonalizada("EXITO", "Se ha elimina el estudiante: " + tableEstu.getSelectionModel().getSelectedItem().getNombre() + " ", Alert.AlertType.INFORMATION);
            }
            cargarEstudiantes();
        } else {
            mostrarAlertaPersonalizada("Error", "No se ha seleccionado una fila", Alert.AlertType.ERROR);

        }
    }

    @FXML
    private void clickActu(ActionEvent event) {
        if (estudiante != null) {
            if (estudianteDAO.actualizarEstudiante(estudiante)) {
                mostrarAlertaPersonalizada("EXITO", "Se ha actualizado correctamente el registro", Alert.AlertType.INFORMATION);
            }
            cargarEstudiantes();
        } else {
            mostrarAlertaPersonalizada("Error", "No se ha seleccionado una fila", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void onEditChange(TableColumn.CellEditEvent<Estudiante, String> event) {
        if (contador < 2) {
            mostrarAlertaPersonalizada("INFO", "Recuerde: Después de editado el dato presionar Enter", Alert.AlertType.INFORMATION);
            contador++;
        }
        estudiante = tableEstu.getSelectionModel().getSelectedItem();

        if (event.getTableColumn().getText().equals("NOMBRE")) {
            estudiante.setNombre(event.getNewValue());
        }
        if (event.getTableColumn().getText().equals("EMAIL")) {
            estudiante.setEmail(event.getNewValue());
        }
        if (event.getTableColumn().getText().equals("TELEFONO")) {
            estudiante.setTelefono(event.getNewValue());
        }

    }

    public void cargarEstudiantesFiltrados() {
        ObservableList<Estudiante> lista = estudianteDAO.getEstudiantesFiltrado(txtBuscar.getText());
        tableEstu.setItems(lista);
        this.columId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        tableEstu.setEditable(true);
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefono.setCellFactory(TextFieldTableCell.forTableColumn());
    
    }
}
