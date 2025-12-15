/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelos.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    private Connection conexion;

    public ProductoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    // 1. REGISTRAR (Con Modelo y Categoría)
    public boolean registrar(Producto p) {
        String sql = "INSERT INTO productos (codigo, modelo, categoria, descripcion, proveedor, stock, precio, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getModelo());
            ps.setString(3, p.getCategoria());
            ps.setString(4, p.getDescripcion());
            ps.setString(5, p.getProveedor());
            ps.setInt(6, p.getStock());
            ps.setDouble(7, p.getPrecio());
            ps.setInt(8, 1);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar: " + e.toString());
            return false;
        }
    }

    // 2. MODIFICAR (Este es el que te faltaba)
    public boolean modificar(Producto p) {
        String sql = "UPDATE productos SET codigo=?, modelo=?, categoria=?, descripcion=?, proveedor=?, stock=?, precio=? WHERE id=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getModelo());
            ps.setString(3, p.getCategoria());
            ps.setString(4, p.getDescripcion());
            ps.setString(5, p.getProveedor());
            ps.setInt(6, p.getStock());
            ps.setDouble(7, p.getPrecio());
            ps.setInt(8, p.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar: " + e.toString());
            return false;
        }
    }

    // 3. ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.toString());
            return false;
        }
    }

    // 4. LISTAR
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setModelo(rs.getString("modelo"));
                p.setCategoria(rs.getString("categoria"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setProveedor(rs.getString("proveedor"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getDouble("precio"));
                p.setEstado(rs.getInt("estado"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.toString());
        }
        return lista;
    }
}