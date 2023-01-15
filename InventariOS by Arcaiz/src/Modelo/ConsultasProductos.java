//Esta clase es la encargada de realizar todas las peticiones a la base de datos
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultasProductos extends Conexion {

    PreparedStatement ps; /* Este método nos permite ingresar, editar y eliminar registros de la base de datos */

    ResultSet rs; /* Este método nos permite obtener datos de la base de datos */

    public boolean insertar(Producto producto) {
        Connection conexion = getConnection(); //Método heredado desde conexión

        try {
            ps = conexion.prepareStatement("insert into producto(id,nombre,precio,cantidad,etiquetas,descripcion) values(?,?,?,?,?,?)");
            ps.setString(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCantidad());
            ps.setString(5, producto.getEtiquetas());
            ps.setString(6, producto.getDescripción());
            int resultado = ps.executeUpdate(); /* En esta línea de código se nos hara 
             la insercción a nuestra base de datos  */

            if (resultado > 0) { //con este if revisamos si se ha hecho el cambio en la base de datos
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Error " + ex);
            return false;
        } finally { //Agregando esta sentencia nos ayudara a cerrar nuestra base de datos siempre
            try {
                conexion.close();
            } catch (Exception ex) {
                System.err.println("Error " + ex);
            }
        }
    }

    public boolean modificar(Producto producto) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("update producto set id=?,nombre=?,precio=?,cantidad=?,etiquetas=?,descripcion=? where idProducto=?");
            ps.setString(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCantidad());
            ps.setString(5, producto.getEtiquetas());
            ps.setString(6, producto.getDescripción());
            ps.setInt(7, producto.getIdProducto());
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            System.err.println("Error " + ex);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception ex) {
                System.err.println("Error " + ex);
            }
        }
    }

    public boolean eliminar(Producto producto) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("delete from producto where idProducto=?");
            ps.setInt(1, producto.getIdProducto());
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Error " + ex);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception ex) {
                System.err.println("Error " + ex);
            }
        }
    }
}
