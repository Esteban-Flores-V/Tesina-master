package app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class UsuarioDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/inmobiliaria";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (usuario, contraseña) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContraseña());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkDB(Usuario usuario) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre().trim());
            stmt.setString(2, usuario.getContraseña().trim());

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Si existe al menos un registro, el usuario es válido
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
