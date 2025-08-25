package app.controller;

import app.model.Usuario;
import app.model.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormController {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField contraseñaField;

    @FXML
    private void guardarUsuario() {
        String nombre = nombreField.getText();
        String contraseña = contraseñaField.getText();
        if (nombre.trim().isEmpty() || contraseña.trim().isEmpty()) {
            mostrarError("Problema bb", "Los valores no pueden ser nulo");
            return;
        }


        Usuario usuario = new Usuario(nombre, contraseña);

        if (UsuarioDAO.insertar(usuario)) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente");
            nombreField.clear();
            contraseñaField.clear();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario");
        }
    }

    @FXML
    private void iniciarSesion() {
        String nombre = nombreField.getText();
        String contraseña = contraseñaField.getText();

        Usuario usuario = new Usuario(nombre, contraseña);

        if (UsuarioDAO.checkDB(usuario)) {
            mostrarAlerta("Bienvenido", "Inicio de sesión correcto");

            try {
                // Cierra la ventana de inicio de sesión
                Stage stageLogin = (Stage) nombreField.getScene().getWindow();
                stageLogin.close();

                // Abre la ventana de menú
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
                Parent root = loader.load();

                Stage stageMenu = new Stage();
                stageMenu.setTitle("Menú Principal");
                stageMenu.setScene(new Scene(root));
                stageMenu.show();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarError("Error", "No se pudo abrir el menú");
            }

        } else {
            mostrarError("Error", "Usuario o contraseña incorrectos");
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