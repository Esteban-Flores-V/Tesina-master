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
    private TextField nombreField; // Aquí puede ir nombre o email
    @FXML
    private TextField contraseñaField;

    @FXML
    private void abrirRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Usuario");
            stage.setScene(new Scene(root));
            stage.show();

            // Opcional: cerrar el login al abrir el registro
            // ((Stage) nombreField.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error", "No se pudo abrir el formulario de registro");
        }
    }

    @FXML
    private void iniciarSesion() {
        String nombre = nombreField.getText();
        String contraseña = contraseñaField.getText();

        Usuario usuario = new Usuario(nombreField.getText(), contraseñaField.getText());

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