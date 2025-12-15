/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

public class Producto {
    // Atributos
    private int id;
    private String codigo;
    private String modelo;      // Nuevo campo
    private String categoria;   // Nuevo campo
    private String descripcion; 
    private String proveedor;   
    private int stock;
    private double precio;
    private int estado;

    // Constructor Vacío
    public Producto() {
    }

    // Constructor Completo
    public Producto(int id, String codigo, String modelo, String categoria, String descripcion, String proveedor, int stock, double precio, int estado) {
        this.id = id;
        this.codigo = codigo;
        this.modelo = modelo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
        this.stock = stock;
        this.precio = precio;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    // --- AQUÍ ESTÁN LOS QUE TE FALTABAN ---
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    // ---------------------------------------

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}