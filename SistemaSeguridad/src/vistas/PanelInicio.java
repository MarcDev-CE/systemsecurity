/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import modelos.Producto;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;
import javax.swing.*;

public class PanelInicio extends JPanel {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    ProductoDAO productoDAO = new ProductoDAO();
    private JLabel lblTotalProductos, lblTotalUsuarios, lblAlertaStock;

    // DEFINIMOS LAS FUENTES AQUÍ PARA QUE SEAN UNIFORMES
    private Font fontTitulo = new Font("Arial Black", Font.BOLD, 36); // Solo para el logo
    private Font fontSubtitulo = new Font("Arial", Font.PLAIN, 16);
    private Font fontBotones = new Font("Arial", Font.BOLD, 12);      // Botones legibles
    private Font fontTarjetasTit = new Font("Arial", Font.BOLD, 14);  // Títulos de tarjetas
    private Font fontNumeros = new Font("Arial", Font.BOLD, 52);      // Números grandes
    private Font fontTexto = new Font("Arial", Font.PLAIN, 14);       // Texto normal

    public PanelInicio() {
        setLayout(null);
        setBackground(new Color(245, 245, 250)); 

        // 1. BIENVENIDA (ESTE ES EL ÚNICO EN ARIAL BLACK)
        JLabel lblTitulo = new JLabel("SISTEMA SOS PERÚ");
        lblTitulo.setFont(fontTitulo); 
        lblTitulo.setForeground(new Color(40, 40, 40));
        lblTitulo.setBounds(30, 20, 500, 50);
        add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Panel de Control General y Estadísticas");
        lblSubtitulo.setFont(fontSubtitulo);
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setBounds(35, 70, 400, 20);
        add(lblSubtitulo);

        // ========================================================
        // 2. BOTONES DE CONTROL
        // ========================================================
        
        // BOTÓN CERRAR SESIÓN
        JButton btnLogout = new JButton("CERRAR SESIÓN");
        btnLogout.setBounds(630, 30, 150, 45);
        btnLogout.setBackground(new Color(52, 152, 219)); 
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(fontBotones); // Fuente uniforme
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLogout.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this, "¿Cambiar de usuario?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION) == 0) {
                ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
                new Login().setVisible(true);
            }
        });
        add(btnLogout);

        // BOTÓN SALIR
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBounds(800, 30, 150, 45);
        btnSalir.setBackground(new Color(231, 76, 60)); 
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(fontBotones); // Ahora usa Arial Bold normal (se lee mejor)
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSalir.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this, "¿Desea salir del sistema?", "Salir", JOptionPane.YES_NO_OPTION) == 0) {
                System.exit(0); 
            }
        });
        add(btnSalir);


        // 3. TARJETAS DE INFORMACIÓN
        crearTarjeta(30, 130, new Color(41, 128, 185), "PRODUCTOS TOTALES", "📦");
        lblTotalProductos = crearNumero(30, 130);
        
        crearTarjeta(350, 130, new Color(230, 126, 34), "USUARIOS ACTIVOS", "👥");
        lblTotalUsuarios = crearNumero(350, 130);
        
        crearTarjeta(670, 130, new Color(192, 57, 43), "STOCK CRÍTICO", "⚠️");
        lblAlertaStock = crearNumero(670, 130);

        // 4. BOTÓN ACTUALIZAR
        JButton btnActualizar = new JButton("🔄 Refrescar Datos");
        btnActualizar.setBounds(30, 300, 160, 35);
        btnActualizar.setBackground(Color.WHITE);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(fontBotones); // Fuente corregida
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarDatos());
        add(btnActualizar);
        
        // 5. PIE DE PÁGINA
        JPanel panelInfo = new JPanel();
        panelInfo.setBounds(30, 350, 930, 200);
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setLayout(null);
        panelInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(220,220,220)));
        add(panelInfo);
        
        JLabel lblInfoTitle = new JLabel("Estado del Sistema");
        lblInfoTitle.setFont(new Font("Arial", Font.BOLD, 16)); // Arial Bold limpio
        lblInfoTitle.setBounds(20, 20, 200, 20);
        panelInfo.add(lblInfoTitle);
        
        JLabel lblInfoText = new JLabel("<html>Conectado a la base de datos: <b>sistema_seguridad</b><br>Estado del servidor: <font color='green'>En Línea</font><br><br>Soporte Técnico: soporte@sosperu.com</html>");
        lblInfoText.setFont(fontTexto); // Fuente normal
        lblInfoText.setForeground(Color.DARK_GRAY);
        lblInfoText.setBounds(20, 50, 800, 100);
        panelInfo.add(lblInfoText);

        cargarDatos();
    }

    // --- MÉTODOS ---
    private void cargarDatos() {
        List<Producto> listaP = productoDAO.listar();
        lblTotalProductos.setText(String.valueOf(listaP.size()));
        lblTotalUsuarios.setText(String.valueOf(usuarioDAO.listar().size()));
        int stockBajo = 0;
        for(Producto p : listaP) { if(p.getStock() < 5) stockBajo++; }
        lblAlertaStock.setText(String.valueOf(stockBajo));
    }
    
    private void crearTarjeta(int x, int y, Color color, String titulo, String icono) {
        JPanel tarjeta = new JPanel();
        tarjeta.setBounds(x, y, 290, 150);
        tarjeta.setBackground(color);
        tarjeta.setLayout(null);
        add(tarjeta); 
        
        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(fontTarjetasTit); // Arial Bold normal
        lblTit.setForeground(new Color(255,255,255,220));
        lblTit.setBounds(20, 20, 200, 20);
        tarjeta.add(lblTit);
        
        JLabel lblIcon = new JLabel(icono);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setForeground(new Color(255,255,255,80));
        lblIcon.setBounds(200, 40, 80, 80);
        tarjeta.add(lblIcon);
    }
    
    private JLabel crearNumero(int x, int y) {
        JLabel lblNum = new JLabel("0");
        lblNum.setFont(fontNumeros); // Arial Bold grande
        lblNum.setForeground(Color.WHITE);
        lblNum.setBounds(x + 25, y + 55, 150, 60);
        add(lblNum); 
        setComponentZOrder(lblNum, 0); 
        return lblNum;
    }
}