package proyecto.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.ClientPreparedStatement;
import com.mysql.cj.jdbc.result.ResultSetImpl;

public class ConexionBD {
    // URL de conexión a la base de datos (cambia 'nombreBaseDatos' por el nombre de tu BD)
    private static final String URL = "jdbc:mysql://localhost:3306/ MI_BASE_DE_DATOS";
    private static final String USER = "root"; // Cambia 'tuUsuario' por tu usuario de MySQL (ej. 'root')
    private static final String PASSWORD = "HianStiven#324"; // Cambia 'tuContraseña' por tu contraseña de MySQL

    private static Connection conexion = null;

    // Método para obtener la conexión
    public static Connection getConnection() {
        if (conexion == null) {
            try {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver no encontrado: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Error en la conexión: " + e.getMessage());
            }
        }
        return conexion;
    }


// Método para insertar datos
public static void insertarProducto(String nombre, double precio) {
    String query = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
    try (Connection conexion = getConnection();
         PreparedStatement pstmt = conexion.prepareStatement(query)) {
        pstmt.setString(1, nombre);
        pstmt.setDouble(2, precio);
        pstmt.executeUpdate();
        System.out.println("Producto insertado correctamente");
    } catch (SQLException e) {
        System.out.println("Error al insertar producto: " + e.getMessage());
    }
}

// Método para consultar todos los productos
public static void consultarProductos() {
    String query = "SELECT * FROM productos";
    try (Connection conexion = getConnection();
         PreparedStatement pstmt = conexion.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                               ", Nombre: " + rs.getString("nombre") +
                               ", Precio: " + rs.getDouble("precio"));
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar productos: " + e.getMessage());
    }
}

// Método para actualizar el precio de un producto
public static void actualizarProducto(int id, double nuevoPrecio) {
    String query = "UPDATE productos SET precio = ? WHERE id = ?";
    try (Connection conexion = getConnection();
         PreparedStatement pstmt = conexion.prepareStatement(query)) {
        pstmt.setDouble(1, nuevoPrecio);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
        System.out.println("Producto actualizado correctamente");
    } catch (SQLException e) {
        System.out.println("Error al actualizar producto: " + e.getMessage());
    }
}

// Método para eliminar un producto
public static void eliminarProducto(int id) {
    String query = "DELETE FROM productos WHERE id = ?";
    try (Connection conexion = getConnection();
         PreparedStatement pstmt = conexion.prepareStatement(query)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Producto eliminado correctamente");
    } catch (SQLException e) {
        System.out.println("Error al eliminar producto: " + e.getMessage());
    }
}
}

