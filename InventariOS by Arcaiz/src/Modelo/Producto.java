package Modelo;

//En esta clase crearemos todos los atributos para nuestros productos que se vayan agregando
//También se crearan los setters y getters de las respectivas variables
public class Producto {
    private int idProducto;
    private String id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String etiquetas;

    public int getIdProducto() {
        return idProducto;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
    private String descripción;
    
    
}
