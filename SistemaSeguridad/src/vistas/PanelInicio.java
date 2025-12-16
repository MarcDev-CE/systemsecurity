/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import modelos.Producto;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.*;

public class PanelInicio extends JPanel {

    // DAOs para obtener los datos
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    ProductoDAO productoDAO = new ProductoDAO();
    
    // Etiquetas para los números (las haremos públicas para poder actualizarlas)
    private JLabel lblTotalProductos;
    private JLabel lblTotalUsuarios;
    private JLabel lblAlertaStock;

    public PanelInicio() {
        setLayout(null);
        setBackground(new Color(245, 245, 250)); // Fondo gris suave

        // 1. BIENVENIDA
        JLabel lblTitulo = new JLabel("¡Bienvenido al Sistema SOS PERÚ!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setBounds(30, 30, 600, 40);
        add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Resumen general del estado del sistema");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setBounds(30, 70, 400, 20);
        add(lblSubtitulo);

        // 2. TARJETAS DE INFORMACIÓN (WIDGETS)
        // Tarjeta 1: Productos (Azul)
        crearTarjeta(30, 120, new Color(41, 128, 185), "TOTAL PRODUCTOS", "📦");
        lblTotalProductos = crearNumero(30, 120);
        
        // Tarjeta 2: Usuarios (Naranja)
        crearTarjeta(350, 120, new Color(230, 126, 34), "USUARIOS ACTIVOS", "👥");
        lblTotalUsuarios = crearNumero(350, 120);
        
        // Tarjeta 3: Alertas (Rojo)
        crearTarjeta(670, 120, new Color(192, 57, 43), "STOCK BAJO", "⚠️");
        lblAlertaStock = crearNumero(670, 120);

        // 3. LOGO O IMAGEN CENTRAL (Decorativo)
        // Simulamos un panel informativo abajo
        JPanel panelInfo = new JPanel();
        panelInfo.setBounds(30, 300, 930, 250);
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setLayout(null);
        panelInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200,200,200)));
        add(panelInfo);
        
        JLabel lblInfo = new JLabel("<html><body><h2>Sistema de Gestión v1.0</h2><p>Desarrollado para SOS Perú. Este sistema permite controlar el inventario de cámaras, grabadores y accesorios, así como gestionar al personal autorizado.</p><br><p><b>Soporte Técnico:</b> soporte@sosperu.com</p></body></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblInfo.setForeground(Color.DARK_GRAY);
        lblInfo.setBounds(40, 20, 800, 150);
        panelInfo.add(lblInfo);

        // 4. BOTÓN ACTUALIZAR
        JButton btnActualizar = new JButton("🔄 Actualizar Datos");
        btnActualizar.setBounds(800, 30, 160, 40);
        btnActualizar.setBackground(Color.WHITE);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarDatos());
        add(btnActualizar);

        // Cargar los números al iniciar
        cargarDatos();
    }

    // --- MÉTODOS LÓGICOS ---
    
    private void cargarDatos() {
        // 1. Contar Productos
        List<Producto> listaP = productoDAO.listar();
        lblTotalProductos.setText(String.valueOf(listaP.size()));

        // 2. Contar Usuarios
        lblTotalUsuarios.setText(String.valueOf(usuarioDAO.listar().size()));

        // 3. Calcular Stock Bajo (Menos de 5 unidades)
        int stockBajo = 0;
        for(Producto p : listaP) {
            if(p.getStock() < 5) stockBajo++;
        }
        lblAlertaStock.setText(String.valueOf(stockBajo));
    }

    // --- MÉTODOS DE DISEÑO (Para no repetir código) ---
    
    private void crearTarjeta(int x, int y, Color color, String titulo, String icono) {
        // Fondo de la tarjeta
        JPanel tarjeta = new JPanel();
        tarjeta.setBounds(x, y, 290, 150);
        tarjeta.setBackground(color);
        tarjeta.setLayout(null);
        add(tarjeta);
        
        // Título
        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Arial", Font.BOLD, 14));
        lblTit.setForeground(new Color(255,255,255,200)); // Blanco con transparencia
        lblTit.setBounds(20, 20, 200, 20);
        tarjeta.add(lblTit);
        
        // Icono (Texto grande)
        JLabel lblIcon = new JLabel(icono);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setForeground(new Color(255,255,255,100));
        lblIcon.setBounds(200, 40, 80, 80);
        tarjeta.add(lblIcon);
    }
    
    private JLabel crearNumero(int x, int y) {
        JLabel lblNum = new JLabel("0");
        lblNum.setFont(new Font("Arial", Font.BOLD, 48));
        lblNum.setForeground(Color.WHITE);
        lblNum.setBounds(x + 20, y + 50, 150, 60);
        add(lblNum); // Importante: Se añade al panel principal, pero encima de la tarjeta visualmente
        setComponentZOrder(lblNum, 0); // Traer al frente
        return lblNum;
    }
}