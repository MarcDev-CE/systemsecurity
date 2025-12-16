/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelos.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

   
    // REGISTRAR USUARIO
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPass());
            ps.setString(4, u.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.toString());
            return false;
        }
    }

    // LISTAR USUARIOS
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPass(rs.getString("pass"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.toString());
        }
        return lista;
    }
    
    
    // ==========================================
    // MÉTODO NUEVO: VALIDAR LOGIN
    // ==========================================
    public Usuario login(String correo, String pass) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        Usuario u = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPass(rs.getString("pass"));
                u.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.toString());
        }
        return u; // Retorna el usuario si existe, o null si falló
    }
    
    // 4. MODIFICAR 
    public boolean modificar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, pass=?, rol=? WHERE id=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPass());
            ps.setString(4, u.getRol());
            ps.setInt(5, u.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar: " + e.toString());
            return false;
        }
    }

    //  ELIMINAR 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
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

