/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.UsuarioDAO;
import modelos.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelUsuarios extends JPanel {

    private JTextField txtNombre, txtCorreo, txtId; 
    private JPasswordField txtPass;
    private JComboBox<String> cmbRol;
    private JTable tblUsuarios;
    private DefaultTableModel modelo;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    // Fuentes personalizadas
    private Font fuenteLabels = new Font("Arial", Font.BOLD, 12);
    private Font fuenteCajas = new Font("Arial", Font.PLAIN, 14);

    public PanelUsuarios() {
        setLayout(null);
        setBackground(new Color(245, 245, 250)); // Fondo gris suave

        // 1. Encabezado Naranja
        JPanel header = new JPanel();
        header.setBounds(20, 20, 960, 60);
        header.setBackground(new Color(230, 126, 34)); // Color Naranja
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("GESTIÓN DE PERSONAL");
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 10, 500, 40);
        header.add(lblTitulo);

        // 2. Panel del Formulario (Izquierda)
        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 100, 320, 450);
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        panelForm.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        add(panelForm);

        // Campo ID Oculto (Vital para eliminar)
        txtId = new JTextField(); 
        txtId.setVisible(false); 
        panelForm.add(txtId);

        // --- CAMPOS DE TEXTO ---
        agregarEtiqueta(panelForm, "Nombre Completo:", 30, 20);
        txtNombre = agregarCaja(panelForm, 30, 45, 260);

        agregarEtiqueta(panelForm, "Correo Electrónico:", 30, 90);
        txtCorreo = agregarCaja(panelForm, 30, 115, 260);

        agregarEtiqueta(panelForm, "Contraseña:", 30, 160);
        txtPass = new JPasswordField();
        txtPass.setBounds(30, 185, 260, 30);
        txtPass.setFont(fuenteCajas);
        panelForm.add(txtPass);

        agregarEtiqueta(panelForm, "Rol / Cargo:", 30, 230);
        cmbRol = new JComboBox<>(new String[]{"Administrador", "Vendedor", "Técnico"});
        cmbRol.setBounds(30, 255, 260, 30);
        cmbRol.setFont(fuenteCajas);
        cmbRol.setBackground(Color.WHITE);
        panelForm.add(cmbRol);

        // --- AQUÍ ESTÁN TUS BOTONES NUEVOS ---
        
        // 1. REGISTRAR (Naranja)
        JButton btnGuardar = crearBoton("REGISTRAR", new Color(230, 126, 34));
        btnGuardar.setBounds(30, 310, 125, 40);
        panelForm.add(btnGuardar);

        // 2. MODIFICAR (Azul - Para corregir errores)
        JButton btnModificar = crearBoton("MODIFICAR", new Color(52, 152, 219)); 
        btnModificar.setBounds(165, 310, 125, 40);
        panelForm.add(btnModificar);

        // 3. ELIMINAR (Rojo - El que pediste)
        JButton btnEliminar = crearBoton("ELIMINAR", new Color(231, 76, 60)); 
        btnEliminar.setBounds(30, 360, 260, 40); // Ocupa todo el ancho para que se vea bien
        panelForm.add(btnEliminar);


        // 3. Tabla de Usuarios (Derecha)
        modelo = new DefaultTableModel();
        modelo.addColumn("ID"); 
        modelo.addColumn("NOMBRE"); 
        modelo.addColumn("CORREO"); 
        modelo.addColumn("ROL");
        modelo.addColumn("PASSWORD"); // Oculto visualmente si quieres, pero útil para cargar datos

        tblUsuarios = new JTable(modelo);
        tblUsuarios.setRowHeight(30);
        tblUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tblUsuarios.getTableHeader().setBackground(new Color(50, 50, 50));
        tblUsuarios.getTableHeader().setForeground(Color.WHITE);
        tblUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scroll = new JScrollPane(tblUsuarios);
        scroll.setBounds(360, 100, 620, 450);
        add(scroll);

        // ================= EVENTOS (LA MAGIA) =================
        
        // A) Al hacer clic en la tabla, rellenamos el formulario
        tblUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblUsuarios.rowAtPoint(e.getPoint());
                txtId.setText(tblUsuarios.getValueAt(fila, 0).toString());
                txtNombre.setText(tblUsuarios.getValueAt(fila, 1).toString());
                txtCorreo.setText(tblUsuarios.getValueAt(fila, 2).toString());
                cmbRol.setSelectedItem(tblUsuarios.getValueAt(fila, 3).toString());
                txtPass.setText(tblUsuarios.getValueAt(fila, 4).toString());
            }
        });

        // B) Botón REGISTRAR
        btnGuardar.addActionListener(e -> {
            if (validar()) {
                Usuario u = capturarDatos();
                if(usuarioDAO.registrar(u)){ 
                    JOptionPane.showMessageDialog(this, "✅ Usuario Registrado");
                    act(); 
                }
            }
        });

        // C) Botón MODIFICAR
        btnModificar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validar()) {
                Usuario u = capturarDatos();
                u.setId(Integer.parseInt(txtId.getText()));
                if(usuarioDAO.modificar(u)){ 
                    JOptionPane.showMessageDialog(this, "🔄 Usuario Modificado");
                    act(); 
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un usuario de la tabla para modificar");
            }
        });

        // D) Botón ELIMINAR (TU PEDIDO)
        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar a este usuario?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if(respuesta == JOptionPane.YES_OPTION){
                    if(usuarioDAO.eliminar(Integer.parseInt(txtId.getText()))){
                         JOptionPane.showMessageDialog(this, "🗑️ Usuario Eliminado Correctamente");
                         act();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un usuario de la tabla para eliminar");
            }
        });

        listarUsuarios();
    }

    // --- MÉTODOS AUXILIARES ---
    
    private void listarUsuarios() {
        List<Usuario> lista = usuarioDAO.listar();
        modelo.setRowCount(0);
        for(Usuario u : lista) {
            modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getCorreo(), u.getRol(), u.getPass()});
        }
    }

    private void act() { listarUsuarios(); limpiar(); }

    private void limpiar() {
        txtId.setText(""); txtNombre.setText(""); txtCorreo.setText(""); txtPass.setText("");
    }

    private boolean validar() {
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios");
            return false;
        }
        return true;
    }

    private Usuario capturarDatos() {
        return new Usuario(0, txtNombre.getText(), txtCorreo.getText(), new String(txtPass.getPassword()), cmbRol.getSelectedItem().toString());
    }

    // Métodos para diseño rápido
    private void agregarEtiqueta(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t); l.setBounds(x, y, 200, 20); l.setFont(fuenteLabels); l.setForeground(Color.GRAY); p.add(l);
    }
    
    private JTextField agregarCaja(JPanel p, int x, int y, int w) {
        JTextField t = new JTextField(); t.setBounds(x, y, w, 30); t.setFont(fuenteCajas); p.add(t); return t;
    }

    private JButton crearBoton(String t, Color c) {
        JButton b = new JButton(t); b.setBackground(c); b.setForeground(Color.WHITE); b.setFocusPainted(false); 
        b.setFont(new Font("Segoe UI", Font.BOLD, 12)); b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }
}