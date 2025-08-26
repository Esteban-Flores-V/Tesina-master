package app.controller;

import app.model.Cliente;
import app.model.ClienteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Menu {

    @FXML
    private MenuItem menuVerPropiedades;
    @FXML
    private MenuItem menuAgregarPropiedad;
    @FXML
    private MenuItem menuVerClientes;
    @FXML
    private MenuItem menuRegistrarCliente;
    @FXML
    private MenuItem menuVerContratos;
    @FXML
    private MenuItem menuNuevoContrato;
    @FXML
    private MenuItem menuSalir;

    @FXML
    private AnchorPane cRegistrar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;


    @FXML
    public void initialize() {
        menuVerPropiedades.setOnAction(e -> abrirVentana("propiedades.fxml"));
        menuAgregarPropiedad.setOnAction(e -> abrirVentana("agregarPropiedad.fxml"));
        menuVerClientes.setOnAction(e -> abrirVentana("clientes.fxml"));
        menuVerContratos.setOnAction(e -> abrirVentana("contratos.fxml"));
        menuNuevoContrato.setOnAction(e -> abrirVentana("nuevoContrato.fxml"));
        menuSalir.setOnAction(e -> System.exit(0));

        menuRegistrarCliente.setOnAction(e -> {
            cRegistrar.setVisible(true);
        });
    }

    private void abrirVentana(String fxmlFile) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlFile));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void limpiarRegistrarC() {
        txtNombre.clear();
        txtApellido.clear();
        txtDni.clear();
        txtTelefono.clear();
        txtEmail.clear();

        cRegistrar.setVisible(false);
    }

    public void registrarCliente() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String dni = txtDni.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || dni.trim().isEmpty() || telefono.trim().isEmpty() || email.trim().isEmpty()) {
            mostrarError("Error", "Todos los campos son obligatorios");
            return;
        }

        Cliente cliente = new Cliente(nombre, apellido, dni, telefono, email);

        if (ClienteDAO.insertar(cliente)) {
            mostrarAlerta("Registro", "Usuario registrado correctamente");

            limpiarRegistrarC();

        } else {
            mostrarError("Error", "No se pudo registrar el cliente");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}