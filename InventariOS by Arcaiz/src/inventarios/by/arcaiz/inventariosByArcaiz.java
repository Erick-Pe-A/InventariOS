package inventarios.by.arcaiz;

import Controlador.ControladorProducto;
import Modelo.ConsultasProductos;
import Modelo.Producto;
import Vista.Interfaz;

public class inventariosByArcaiz {

    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz();
        Producto producto = new Producto();
        ConsultasProductos modelo = new ConsultasProductos();
        ControladorProducto controlador = new ControladorProducto(interfaz, producto, modelo);

        controlador.iniciar();
        interfaz.setVisible(true);
    }
}
