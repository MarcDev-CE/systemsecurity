/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.ProductoDAO;
import modelos.Producto;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelProductos extends JPanel {

    // Componentes
    private JTextField txtCodigo, txtDescripcion, txtProveedor, txtStock, txtPrecio, txtId;
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private ProductoDAO productoDAO = new ProductoDAO();
    
    // Fuentes personalizadas
    private Font fuenteLabels = new Font("Arial", Font.BOLD, 13);
    private Font fuenteCajas = new Font("Arial", Font.PLAIN, 14);

    public PanelProductos() {
        setLayout(null);
        setBackground(new Color(245, 245, 250)); // Fondo Gris Suave

        // =======================================================
        // 1. ENCABEZADO (Igual que antes)
        // =======================================================
        JPanel header = new JPanel();
        header.setBounds(20, 20, 960, 60); // Más ancho
        header.setBackground(new Color(25, 118, 210));
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("INVENTARIO DE EQUIPOS CCTV");
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 22)); // Fuente más gruesa
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 10, 500, 40);
        header.add(lblTitulo);

        // =======================================================
        // 2. FORMULARIO HORIZONTAL (ARRIBA)
        // =======================================================
        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 100, 960, 150); // Panel ANCHO y horizontal
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        // Borde sutil
        panelForm.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200, 200, 200)));
        add(panelForm);

        // Campo ID Oculto
        txtId = new JTextField(); txtId.setVisible(false); panelForm.add(txtId);

        // --- FILA 1 DE DATOS ---
        // Columna 1: Código
        agregarEtiqueta(panelForm, "Código:", 20, 20);
        txtCodigo = agregarCaja(panelForm, 20, 45, 120); // x, y, ancho

        // Columna 2: Descripción (Más ancha porque los nombres son largos)
        agregarEtiqueta(panelForm, "Descripción del Producto:", 160, 20);
        txtDescripcion = agregarCaja(panelForm, 160, 45, 300);

        // Columna 3: Proveedor
        agregarEtiqueta(panelForm, "Marca / Proveedor:", 480, 20);
        txtProveedor = agregarCaja(panelForm, 480, 45, 150);
        
        // --- FILA 2 DE DATOS ---
        // Columna 1: Stock
        agregarEtiqueta(panelForm, "Stock:", 20, 85);
        txtStock = agregarCaja(panelForm, 20, 110, 120);

        // Columna 2: Precio
        agregarEtiqueta(panelForm, "Precio (S/.):", 160, 85);
        txtPrecio = agregarCaja(panelForm, 160, 110, 120);

        // =======================================================
        // 3. BOTONES (A LA DERECHA DEL FORMULARIO)
        // =======================================================
        // Los ponemos en el mismo panelForm pero a la derecha
        
        JButton btnGuardar = crearBoton("GUARDAR", new Color(46, 204, 113));
        btnGuardar.setBounds(660, 30, 130, 40);
        panelForm.add(btnGuardar);

        JButton btnModificar = crearBoton("MODIFICAR", new Color(52, 152, 219));
        btnModificar.setBounds(800, 30, 130, 40);
        panelForm.add(btnModificar);

        JButton btnEliminar = crearBoton("ELIMINAR", new Color(231, 76, 60));
        btnEliminar.setBounds(660, 85, 130, 40);
        panelForm.add(btnEliminar);

        JButton btnLimpiar = crearBoton("LIMPIAR", new Color(149, 165, 166));
        btnLimpiar.setBounds(800, 85, 130, 40);
        panelForm.add(btnLimpiar);


        // =======================================================
        // 4. TABLA (ABAJO - ANCHO COMPLETO)
        // =======================================================
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("CÓDIGO");
        modelo.addColumn("DESCRIPCIÓN");
        modelo.addColumn("MARCA");
        modelo.addColumn("STOCK");
        modelo.addColumn("PRECIO");

        tblProductos = new JTable(modelo);
        tblProductos.setRowHeight(35); // Filas más altas para leer mejor
        tblProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tblProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblProductos.getTableHeader().setBackground(new Color(50,50,50)); // Encabezado negro
        tblProductos.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tblProductos);
        // Ahora la tabla empieza más abajo y ocupa todo el ancho
        scroll.setBounds(20, 270, 960, 350); 
        add(scroll);

        // =======================================================
        // LÓGICA Y EVENTOS
        // =======================================================
        
        // Clic en Tabla
        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblProductos.rowAtPoint(e.getPoint());
                txtId.setText(tblProductos.getValueAt(fila, 0).toString());
                txtCodigo.setText(tblProductos.getValueAt(fila, 1).toString());
                txtDescripcion.setText(tblProductos.getValueAt(fila, 2).toString());
                txtProveedor.setText(tblProductos.getValueAt(fila, 3).toString());
                txtStock.setText(tblProductos.getValueAt(fila, 4).toString());
                String precioStr = tblProductos.getValueAt(fila, 5).toString();
                txtPrecio.setText(precioStr.replace("S/ ", ""));
            }
        });

        // Eventos Botones
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                Producto p = crearProductoDesdeForm();
                if(productoDAO.registrar(p)) {
                    JOptionPane.showMessageDialog(this, "✅ Producto Guardado");
                    actualizarTodo();
                }
            }
        });

        btnModificar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                Producto p = crearProductoDesdeForm();
                p.setId(Integer.parseInt(txtId.getText()));
                if(productoDAO.modificar(p)) {
                    JOptionPane.showMessageDialog(this, "✅ Producto Modificado");
                    actualizarTodo();
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un producto primero");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                if(JOptionPane.showConfirmDialog(this, "¿Borrar producto?") == 0) {
                    productoDAO.eliminar(Integer.parseInt(txtId.getText()));
                    actualizarTodo();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Carga inicial
        listarProductos();
    }

    // --- MÉTODOS DE AYUDA VISUAL ---

    private void agregarEtiqueta(JPanel panel, String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 200, 20);
        lbl.setFont(fuenteLabels); // Usamos Arial Bold
        lbl.setForeground(new Color(80, 80, 80));
        panel.add(lbl);
    }

    private JTextField agregarCaja(JPanel panel, int x, int y, int ancho) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, ancho, 30);
        txt.setFont(fuenteCajas); // Usamos Arial Normal
        // Margen interno para que el texto no se pegue al borde
        txt.setBorder(BorderFactory.createCompoundBorder(
            txt.getBorder(), 
            BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        panel.add(txt);
        return txt;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // --- MÉTODOS LÓGICOS ---

    private void listarProductos() {
        List<Producto> lista = productoDAO.listar();
        modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setRowCount(0);
        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getId(), p.getCodigo(), p.getDescripcion(), p.getProveedor(), p.getStock(), p.getPrecio()
            });
        }
        // Auto-ajustar ancho de descripción (Columna 2)
        ajustarAnchoColumna(tblProductos, 2, 20);
    }

    private void ajustarAnchoColumna(JTable table, int colIndex, int padding) {
        // Tu método mágico para ensanchar columnas
        int ancho = 0;
        // Cabeza
        ancho = Math.max(ancho, table.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(table, table.getColumnModel().getColumn(colIndex).getHeaderValue(), false, false, 0, colIndex)
                .getPreferredSize().width);
        // Filas
        for (int i = 0; i < table.getRowCount(); i++) {
            ancho = Math.max(ancho, table.prepareRenderer(table.getCellRenderer(i, colIndex), i, colIndex).getPreferredSize().width);
        }
        table.getColumnModel().getColumn(colIndex).setPreferredWidth(ancho + padding);
    }

    private void actualizarTodo() {
        listarProductos();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText(""); txtCodigo.setText(""); txtDescripcion.setText("");
        txtProveedor.setText(""); txtStock.setText(""); txtPrecio.setText("");
    }

    private boolean validarCampos() {
        return !txtCodigo.getText().isEmpty() && !txtDescripcion.getText().isEmpty();
    }

    private Producto crearProductoDesdeForm() {
        Producto p = new Producto();
        p.setCodigo(txtCodigo.getText());
        p.setDescripcion(txtDescripcion.getText());
        p.setProveedor(txtProveedor.getText());
        try { p.setStock(Integer.parseInt(txtStock.getText())); } catch(Exception e){ p.setStock(0); }
        try { p.setPrecio(Double.parseDouble(txtPrecio.getText())); } catch(Exception e){ p.setPrecio(0.0); }
        p.setEstado(1);
        return p;
    }
}