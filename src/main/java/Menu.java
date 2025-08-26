import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class Menu {

    @FXML private MenuItem menuVerPropiedades;
    @FXML private MenuItem menuAgregarPropiedad;
    @FXML private MenuItem menuVerClientes;
    @FXML private MenuItem menuRegistrarCliente;
    @FXML private MenuItem menuVerContratos;
    @FXML private MenuItem menuNuevoContrato;
    @FXML private MenuItem menuSalir;

    @FXML
    private AnchorPane cRegistrar;


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
}
