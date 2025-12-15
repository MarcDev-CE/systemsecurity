/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.ProductoDAO;
import modelos.Producto;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class Dashboard extends JFrame {
    // Componentes de la interfaz
    private JTextField txtCodigo, txtDescripcion, txtProveedor, txtStock, txtPrecio;
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private ProductoDAO productoDAO = new ProductoDAO();

    public Dashboard() {
        // 1. Configuración de la Ventana Principal
        setTitle("Sistema de Gestión de Seguridad - SOS Perú");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(null); // Diseño absoluto para controlar posiciones exactas
        getContentPane().setBackground(new Color(245, 245, 250)); // Fondo Gris muy suave

        // 2. Crear el Encabezado Azul
        JPanel header = new JPanel();
        header.setBounds(0, 0, 1000, 70);
        header.setBackground(new Color(25, 118, 210)); // Azul Material Design
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("CONTROL DE INVENTARIO - CCTV");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 15, 500, 40);
        header.add(lblTitulo);

        // 3. Sección de Formulario (Izquierda)
        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 90, 300, 500);
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        // Borde suave
        panelForm.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(panelForm);

        // Agregamos las etiquetas y cajas de texto
        agregarCampo(panelForm, "Código:", 20, txtCodigo = new JTextField());
        agregarCampo(panelForm, "Descripción:", 90, txtDescripcion = new JTextField());
        agregarCampo(panelForm, "Proveedor:", 160, txtProveedor = new JTextField());
        agregarCampo(panelForm, "Stock:", 230, txtStock = new JTextField());
        agregarCampo(panelForm, "Precio (S/):", 300, txtPrecio = new JTextField());

        // Botón Guardar (Verde)
        JButton btnGuardar = new JButton("GUARDAR PRODUCTO");
        btnGuardar.setBounds(30, 380, 240, 45);
        btnGuardar.setBackground(new Color(46, 204, 113)); // Verde Esmeralda
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        panelForm.add(btnGuardar);

        // 4. Sección de Tabla (Derecha)
        // Configurar columnas
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("CÓDIGO");
        modelo.addColumn("DESCRIPCIÓN");
        modelo.addColumn("MARCA");
        modelo.addColumn("STOCK");
        modelo.addColumn("PRECIO");

        tblProductos = new JTable(modelo);
        tblProductos.setRowHeight(30); // Filas más altas
        tblProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Estilo del encabezado de la tabla
        tblProductos.getTableHeader().setBackground(new Color(50, 50, 50));
        tblProductos.getTableHeader().setForeground(Color.WHITE);
        tblProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(tblProductos);
        scroll.setBounds(340, 90, 620, 500);
        add(scroll);

        // 5. Cargar datos iniciales
        listarProductos();

        // 6. Acción del Botón
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    // Método auxiliar para no repetir código al crear etiquetas
    private void agregarCampo(JPanel panel, String texto, int y, JTextField campo) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(30, y, 200, 20);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 100, 100));
        panel.add(lbl);

        campo.setBounds(30, y + 25, 240, 30);
        panel.add(campo);
    }

    // Lógica para llenar la tabla
    private void listarProductos() {
        List<Producto> lista = productoDAO.listar();
        modelo.setRowCount(0); // Limpiar tabla
        for (Producto p : lista) {
            Object[] fila = {
                p.getId(),
                p.getCodigo(),
                p.getDescripcion(),
                p.getProveedor(),
                p.getStock(),
                "S/ " + p.getPrecio()
            };
            modelo.addRow(fila);
        }
    }

    // Lógica para guardar
    private void guardarProducto() {
        if (txtCodigo.getText().isEmpty() || txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios");
            return;
        }

        Producto p = new Producto();
        p.setCodigo(txtCodigo.getText());
        p.setDescripcion(txtDescripcion.getText());
        p.setProveedor(txtProveedor.getText());
        try {
            p.setStock(Integer.parseInt(txtStock.getText()));
            p.setPrecio(Double.parseDouble(txtPrecio.getText()));
        } catch (NumberFormatException e) {
            p.setStock(0);
            p.setPrecio(0.0);
        }
        p.setEstado(1);

        if (productoDAO.registrar(p)) {
            JOptionPane.showMessageDialog(this, "✅ Producto Guardado Correctamente");
            listarProductos(); // Actualizar tabla
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar");
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtProveedor.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
    }

    // MÉTODO MAIN: Para que puedas darle Run File directamente
    public static void main(String[] args) {
        try {
            // Poner estilo visual de Windows (se ve mejor)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        // Iniciar ventana
        java.awt.EventQueue.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
