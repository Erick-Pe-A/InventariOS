package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasProductos;
import Modelo.Producto;
import Tools.InputVerifier;
import Vista.Interfaz;
import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorProducto implements ActionListener {
    
    private final Interfaz interfaz;
    private final Producto producto;
    private final ConsultasProductos modelo;
    private final InputVerifier validator;

    public ControladorProducto(Interfaz interfaz, Producto producto, ConsultasProductos modelo) {
        this.interfaz = interfaz;
        this.producto = producto;
        this.modelo = modelo;

        validator = new InputVerifier();
        validator.add(interfaz.CajaId, "required");
        validator.add(interfaz.CajaNombre, "required");
        validator.add(interfaz.CajaPrecio, "price");
        validator.add(interfaz.CajaCantidad, "required|number");
        validator.add(interfaz.CajaEtiquetas, "required");

        interfaz.BotonAgregar.addActionListener(this);
        interfaz.BotonDeBusqueda.addActionListener(this);
        interfaz.botonModificar.addActionListener(this);
        interfaz.BotonEliminar.addActionListener(this);
        interfaz.BotonLimpiar.addActionListener(this);

    }

    public void iniciar() {
        interfaz.setTitle("InventariOS By Arcaiz");
        interfaz.setLocationRelativeTo(null);
        interfaz.CajaIdProducto.setVisible(false);
        interfaz.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/logo.png")));
        cargarTabla();
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfaz.BotonAgregar) {
            if (!validator.validate()) {
                JOptionPane.showMessageDialog(null, validator.getAllErrors());
                return;
            } else {
                InsertarProducto();
                cargarTabla();
                limpiarCajas();
            }
        }
        if (e.getSource() == interfaz.botonModificar) {
            if (!validator.validate()) {
                JOptionPane.showMessageDialog(null, validator.getAllErrors());
                return;
            } else {
                EditarProducto();
                cargarTabla();
            }
        }
        if (e.getSource() == interfaz.BotonDeBusqueda) {
            cargarTabla();
            limpiarCajas();
        }
        if (e.getSource() == interfaz.BotonEliminar) {
            if (!validator.validate()) {
                JOptionPane.showMessageDialog(null, validator.getAllErrors());
                return;
            } else {
                eliminar();
                cargarTabla();
            }
        }
        if (e.getSource() == interfaz.BotonLimpiar) {
            limpiarCajas();
        }

    }

    public void InsertarProducto() {
        producto.setId(interfaz.CajaId.getText());
        producto.setNombre(interfaz.CajaNombre.getText());
        producto.setPrecio(Double.parseDouble(interfaz.CajaPrecio.getText()));
        producto.setCantidad(Integer.parseInt(interfaz.CajaCantidad.getText()));
        producto.setEtiquetas(interfaz.CajaEtiquetas.getText());
        producto.setDescripción(interfaz.txtDescripcion.getText());
        if (modelo.insertar(producto)) {
            JOptionPane.showMessageDialog(null, "Producto ingresado");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Estamos teniendo problemas de conexión");
        }
    }

    public void EditarProducto() {
        producto.setIdProducto(Integer.parseInt(interfaz.CajaIdProducto.getText()));
        producto.setId(interfaz.CajaId.getText());
        producto.setNombre(interfaz.CajaNombre.getText());
        producto.setPrecio(Double.parseDouble(interfaz.CajaPrecio.getText()));
        producto.setCantidad(Integer.parseInt(interfaz.CajaCantidad.getText()));
        producto.setEtiquetas(interfaz.CajaEtiquetas.getText());
        producto.setDescripción(interfaz.txtDescripcion.getText());
        int resp = JOptionPane.showConfirmDialog(interfaz, "¿Estás seguro que deseas modificar este registro?");
        if (resp == 0) {
            if (modelo.modificar(producto)) {
                JOptionPane.showMessageDialog(null, "Producto modificado");
                limpiarCajas();
            } else {
                JOptionPane.showMessageDialog(null, "Estamos teniendo problemas de conexión");
            }
        }
    }

    public void cargarTabla() {
    DefaultTableModel modeloTabla = new DefaultTableModel();
        interfaz.TablaDeProductos.setModel(modeloTabla);

        int tipo = interfaz.CriterioDeBusqueda.getSelectedIndex();
        String campo = interfaz.BarraDeBusqueda.getText();
        String where = "";
        switch (tipo) {
            case 0:
                where = "select id,nombre,precio,cantidad,etiquetas,descripcion from producto where id like '%" + campo + "%'";
                break;
            case 1:
                where = "select id,nombre,precio,cantidad,etiquetas,descripcion from producto where nombre like '%" + campo + "%'";
                break;
            case 2:
                where = "select id,nombre,precio,cantidad,etiquetas,descripcion from producto where etiquetas like '%" + campo + "%'";
                break;
        }
        PreparedStatement ps;
        ResultSet rs;
        try {
            Conexion con = new Conexion();
            Connection conexion = con.getConnection();

            if (!"".equalsIgnoreCase(campo)) {
                ps = conexion.prepareStatement(where);
            } else {
                ps = conexion.prepareStatement("select id,nombre,precio,cantidad,etiquetas,descripcion from producto");
            }
            rs = ps.executeQuery();

            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Precio");
            modeloTabla.addColumn("Cantidad");
            modeloTabla.addColumn("Etiquetas");
            modeloTabla.addColumn("Descripción");

            while (rs.next()) {
                Object fila[] = new Object[6];
                for (int i = 0; i < 6; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }

    public void eliminar() {
        producto.setIdProducto(Integer.parseInt(interfaz.CajaIdProducto.getText()));
        int resp = JOptionPane.showConfirmDialog(interfaz, "¿Estás seguro que deseas eliminar este registro?");
        if (resp == 0) {
            if (modelo.eliminar(producto)) {
                JOptionPane.showMessageDialog(interfaz, "Registro Eliminado");
                limpiarCajas();
            } else {
                JOptionPane.showMessageDialog(interfaz, "Estamos teniendo problemas de conexión");
            }
        }
    }

    public void limpiarCajas() {
        interfaz.CajaIdProducto.setText("");
        interfaz.CajaId.setText("");
        interfaz.CajaId.setBackground(Color.white);
        interfaz.CajaNombre.setText("");
        interfaz.CajaNombre.setBackground(Color.white);
        interfaz.CajaPrecio.setText("0.00");
        interfaz.CajaPrecio.setBackground(Color.white);
        interfaz.CajaCantidad.setText("");
        interfaz.CajaCantidad.setBackground(Color.white);
        interfaz.CajaEtiquetas.setText("");
        interfaz.CajaEtiquetas.setBackground(Color.white);
        interfaz.txtDescripcion.setText("");
    }
}
