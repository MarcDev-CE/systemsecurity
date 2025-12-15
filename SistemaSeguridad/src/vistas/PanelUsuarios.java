/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.UsuarioDAO;
import modelos.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelUsuarios extends JPanel {

    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtPass;
    private JComboBox<String> cmbRol;
    private JTable tblUsuarios;
    private DefaultTableModel modelo;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public PanelUsuarios() {
        setLayout(null);
        setBackground(new Color(245, 245, 250));

        // 1. Título Interno (Naranja)
        JPanel header = new JPanel();
        header.setBounds(20, 20, 940, 60);
        header.setBackground(new Color(230, 126, 34)); // Naranja
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("GESTIÓN DE PERSONAL");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 10, 500, 40);
        header.add(lblTitulo);

        // 2. Formulario
        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 100, 300, 450);
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        panelForm.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(panelForm);

        // Campos manuales
        JLabel l1 = new JLabel("Nombre:"); l1.setBounds(30, 20, 200, 20); panelForm.add(l1);
        txtNombre = new JTextField(); txtNombre.setBounds(30, 45, 240, 30); panelForm.add(txtNombre);

        JLabel l2 = new JLabel("Correo:"); l2.setBounds(30, 90, 200, 20); panelForm.add(l2);
        txtCorreo = new JTextField(); txtCorreo.setBounds(30, 115, 240, 30); panelForm.add(txtCorreo);

        JLabel l3 = new JLabel("Contraseña:"); l3.setBounds(30, 160, 200, 20); panelForm.add(l3);
        txtPass = new JPasswordField(); txtPass.setBounds(30, 185, 240, 30); panelForm.add(txtPass);

        JLabel l4 = new JLabel("Rol:"); l4.setBounds(30, 230, 200, 20); panelForm.add(l4);
        cmbRol = new JComboBox<>(new String[]{"Administrador", "Vendedor", "Técnico"});
        cmbRol.setBounds(30, 255, 240, 30); panelForm.add(cmbRol);

        JButton btnGuardar = new JButton("REGISTRAR");
        btnGuardar.setBounds(30, 350, 240, 45);
        btnGuardar.setBackground(new Color(230, 126, 34));
        btnGuardar.setForeground(Color.WHITE);
        panelForm.add(btnGuardar);

        // 3. Tabla
        modelo = new DefaultTableModel();
        modelo.addColumn("ID"); modelo.addColumn("NOMBRE"); modelo.addColumn("CORREO"); modelo.addColumn("ROL");
        tblUsuarios = new JTable(modelo);
        tblUsuarios.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tblUsuarios);
        scroll.setBounds(340, 100, 620, 450);
        add(scroll);

        listarUsuarios();
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarUsuario();
            }
        });
    }

    private void listarUsuarios() {
        List<Usuario> lista = usuarioDAO.listar();
        modelo.setRowCount(0);
        for(Usuario u : lista) modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getCorreo(), u.getRol()});
    }

    private void guardarUsuario() {
        if(txtNombre.getText().isEmpty()) return;
        Usuario u = new Usuario(0, txtNombre.getText(), txtCorreo.getText(), new String(txtPass.getPassword()), cmbRol.getSelectedItem().toString());
        if(usuarioDAO.registrar(u)){
            JOptionPane.showMessageDialog(this, "Usuario Registrado");
            listarUsuarios();
            txtNombre.setText(""); txtCorreo.setText(""); txtPass.setText("");
        }
    }
}