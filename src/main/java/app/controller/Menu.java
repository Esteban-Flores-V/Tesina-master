package app.controller;

import app.model.Cliente;
import app.model.ClienteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Menu {

    // Barra de herramientas
    @FXML private MenuItem menuVerPropiedades;
    @FXML private MenuItem menuAgregarPropiedad;
    @FXML private MenuItem menuVerClientes;
    @FXML private MenuItem menuRegistrarCliente;
    @FXML private MenuItem menuVerContratos;
    @FXML private MenuItem menuNuevoContrato;
    @FXML private MenuItem menuSalir;

    // Ventana Registrar Cliente
    @FXML private AnchorPane cRegistrar;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDni;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;

    // Ventana Modificar Cliente
    @FXML private AnchorPane modCliente;
    @FXML private TextField txtNombre1;
    @FXML private TextField txtApellido1;
    @FXML private TextField txtDni1;
    @FXML private TextField txtTelefono1;
    @FXML private TextField txtEmail1;
    @FXML private Button RegCli;
    @FXML private Button ModCli;
    @FXML private Button DelCli;

    // Ver tabla de clientes
    @FXML private AnchorPane verCliente;
    @FXML private TableView<Cliente> tablaCliente;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellido;
    @FXML private TableColumn<Cliente, String> colDNI;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colEmail;

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        menuVerPropiedades.setOnAction(e -> abrirVentana("propiedades.fxml"));
        menuAgregarPropiedad.setOnAction(e -> abrirVentana("agregarPropiedad.fxml"));
        menuVerContratos.setOnAction(e -> abrirVentana("contratos.fxml"));
        menuNuevoContrato.setOnAction(e -> abrirVentana("nuevoContrato.fxml"));
        menuSalir.setOnAction(e -> System.exit(0));

        menuRegistrarCliente.setOnAction(e -> {cRegistrar.setVisible(true);});
        menuVerClientes.setOnAction(e -> {verCliente.setVisible(true);});

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cargarClientes();
    }

    private void cargarClientes() {
        listaClientes.clear();
        listaClientes.addAll(ClienteDAO.obtenerTodos());
        tablaCliente.setItems(listaClientes);
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

    @FXML
    private void seleccionarClienteParaEditar() {
        Cliente seleccionado = tablaCliente.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Error", "Debes seleccionar un cliente");
            return;
        }
        verCliente.setVisible(false);

        // Llenar los campos del AnchorPane de edición
        txtNombre1.setText(seleccionado.getNombre());
        txtApellido1.setText(seleccionado.getApellido());
        txtDni1.setText(seleccionado.getDni());
        txtTelefono1.setText(seleccionado.getTelefono());
        txtEmail1.setText(seleccionado.getEmail());

        // Hacer visible el AnchorPane de edición
        modCliente.setVisible(true);
    }

    @FXML
    private void guardarEdicionCliente() {
        Cliente seleccionado = tablaCliente.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        seleccionado.setNombre(txtNombre1.getText());
        seleccionado.setApellido(txtApellido1.getText());
        seleccionado.setDni(txtDni1.getText());
        seleccionado.setTelefono(txtTelefono1.getText());
        seleccionado.setEmail(txtEmail1.getText());

        if (ClienteDAO.actualizar(seleccionado)) {
            mostrarAlerta("Edición", "Cliente actualizado correctamente");
            cargarClientes(); // refresca la tabla
            modCliente.setVisible(false); // ocultar panel
        } else {
            mostrarError("Error", "No se pudo actualizar el cliente");
        }
    }

    private void cancelarEdicionCliente() {
        modCliente.setVisible(false);
    }

    @FXML
    private void eliminarCliente() {
        Cliente seleccionado = tablaCliente.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Error", "Selecciona un cliente para eliminar");
            return;
        }

        if (ClienteDAO.eliminar(seleccionado)) {
            mostrarAlerta("Éxito", "Cliente eliminado correctamente");
            cargarClientes(); // refrescar tabla
        } else {
            mostrarError("Error", "No se pudo eliminar el cliente");
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