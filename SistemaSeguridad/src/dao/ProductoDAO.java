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
/**
 *
 * @author USER
 */
public class ProductoDAO {
    private Connection conexion;

    public ProductoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    // REGISTRAR (INSERT)
    public boolean registrar(Producto p) {
        String sql = "INSERT INTO productos (codigo, descripcion, proveedor, stock, precio, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getProveedor());
            ps.setInt(4, p.getStock());
            ps.setDouble(5, p.getPrecio());
            ps.setInt(6, p.getEstado()); // 1: Activo
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar: " + e.toString());
            return false;
        }
    }

    // LISTAR (SELECT)
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
    
    // ELIMINAR (DELETE)
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
}
