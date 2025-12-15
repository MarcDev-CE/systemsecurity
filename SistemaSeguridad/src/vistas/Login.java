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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Login extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtPass;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Login() {
        // 1. Configuración de la Ventana
        setTitle("Acceso al Sistema");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // 2. Encabezado Azul
        JPanel header = new JPanel();
        header.setBounds(0, 0, 400, 200);
        header.setBackground(new Color(25, 118, 210)); // Azul Institucional
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("BIENVENIDO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 60, 400, 40);
        header.add(lblTitulo);
        
        JLabel lblSub = new JLabel("Sistema de Seguridad SOS Perú");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(200, 200, 200));
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        lblSub.setBounds(0, 100, 400, 20);
        header.add(lblSub);

        // 3. Formulario de Ingreso
        JLabel lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCorreo.setForeground(Color.GRAY);
        lblCorreo.setBounds(50, 230, 300, 20);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(50, 255, 300, 35);
        txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // ¡YA NO HAY TEXTO POR DEFECTO AQUÍ!
        add(txtCorreo);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(Color.GRAY);
        lblPass.setBounds(50, 300, 300, 20);
        add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(50, 325, 300, 35);
        // ¡YA NO HAY CONTRASEÑA POR DEFECTO AQUÍ!
        add(txtPass);

        // 4. Botón Ingresar (MEJORADO)
        JButton btnIngresar = new JButton("INICIAR SESIÓN");
        btnIngresar.setBounds(50, 390, 300, 45);
        
        // COLOR DEL BOTÓN:
        // Usamos un Azul oscuro para que contraste con el fondo blanco
        btnIngresar.setBackground(new Color(25, 118, 210)); 
        btnIngresar.setForeground(Color.WHITE); // Texto blanco
        
        // Trucos para que Windows NO lo ponga blanco
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false); // Quita el recuadro de selección feo
        btnIngresar.setBorderPainted(false); // Quita el borde 3D para que se vea plano y moderno
        btnIngresar.setOpaque(true); // Fuerza a pintar el fondo
        
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnIngresar);

        // 5. Acción del Botón
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarIngreso();
            }
        });
    }

    private void validarIngreso() {
        String correo = txtCorreo.getText();
        String pass = new String(txtPass.getPassword());

        if (correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor, escribe tu correo y contraseña.");
            return;
        }

        // Llamamos al DAO
        Usuario u = usuarioDAO.login(correo, pass);

        if (u != null) {
            JOptionPane.showMessageDialog(this, "✅ Bienvenido: " + u.getNombre());
            
            // Abrir el sistema principal
            new SistemaPrincipal().setVisible(true);
            
            // Cerrar login
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Correo o contraseña incorrectos.");
        }
    }

    public static void main(String[] args) {
        // IMPORTANTE: He quitado la línea de "UIManager..." para que el botón
        // respete el color Azul y no se ponga blanco por culpa de Windows.
        new Login().setVisible(true);
    }
}