package proyecto.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaConexion {
    public static void main(String[] args) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.getConnection(); // Obtén la conexión

            // Llama a los métodos para realizar operaciones
            insertarProducto(conexion);
            consultarProductos(conexion);
            actualizarProducto(conexion);
            eliminarProducto(conexion);

        } catch (Exception e) {
            System.out.println("Error en la conexión o en las operaciones: " + e.getMessage());
        } finally {
            // Cierra la conexión al final
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Conexión cerrada");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    private static void insertarProducto(Connection conexion) {
        String sql = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, "Nombre del Producto");
            pstmt.setBigDecimal(2, new java.math.BigDecimal("19.99"));
            pstmt.executeUpdate();
            System.out.println("Producto insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    private static void consultarProductos(Connection conexion) {
        String sql = "SELECT * FROM productos";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", Precio: " + rs.getBigDecimal("precio"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
        }
    }

    private static void actualizarProducto(Connection conexion) {
        String sql = "UPDATE productos SET precio = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, new java.math.BigDecimal("24.99"));
            pstmt.setInt(2, 1); // Asegúrate de que el ID existe
            pstmt.executeUpdate();
            System.out.println("Producto actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    private static void eliminarProducto(Connection conexion) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, 1); // Asegúrate de que el ID existe
            pstmt.executeUpdate();
            System.out.println("Producto eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }
}

