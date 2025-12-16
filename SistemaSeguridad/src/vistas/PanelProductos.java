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
import java.text.MessageFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelProductos extends JPanel {

    // Componentes
    private JTextField txtCodigo, txtModelo, txtDescripcion, txtStock, txtPrecio, txtId;
    // AHORA PROVEEDOR ES UN COMBOBOX
    private JComboBox<String> cmbCategoria, cmbProveedor; 
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private ProductoDAO productoDAO = new ProductoDAO();
    
    // Fuentes
    private Font fuenteLabels = new Font("Arial", Font.BOLD, 12);
    private Font fuenteCajas = new Font("Arial", Font.PLAIN, 14);

    public PanelProductos() {
        setLayout(null);
        setBackground(new Color(245, 245, 250));

        // 1. ENCABEZADO
        JPanel header = new JPanel();
        header.setBounds(20, 20, 960, 60);
        header.setBackground(new Color(25, 118, 210));
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("INVENTARIO DE EQUIPOS CCTV");
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 10, 500, 40);
        header.add(lblTitulo);

        // 2. FORMULARIO
        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 100, 960, 230); 
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        panelForm.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200, 200, 200)));
        add(panelForm);

        txtId = new JTextField(); txtId.setVisible(false); panelForm.add(txtId);

        // Fila 1
        agregarEtiqueta(panelForm, "Código Interno:", 20, 20);
        txtCodigo = agregarCaja(panelForm, 20, 45, 150);

        agregarEtiqueta(panelForm, "Modelo (Fábrica):", 190, 20);
        txtModelo = agregarCaja(panelForm, 190, 45, 200);

        agregarEtiqueta(panelForm, "Categoría:", 410, 20);
        cmbCategoria = new JComboBox<>(new String[]{"- Seleccione -", "Cámaras IP", "Cámaras Análogas", "Grabadores (DVR/NVR)", "Discos Duros", "Cables y Conectores", "Fuentes de Poder", "Gabinetes"});
        cmbCategoria.setBounds(410, 45, 200, 30);
        cmbCategoria.setFont(fuenteCajas);
        cmbCategoria.setBackground(Color.WHITE);
        panelForm.add(cmbCategoria);

        // Fila 2
        agregarEtiqueta(panelForm, "Descripción Detallada:", 20, 85);
        txtDescripcion = agregarCaja(panelForm, 20, 110, 370);

        // --- AQUÍ ESTÁ EL CAMBIO: MARCA AHORA ES COMBOBOX ---
        agregarEtiqueta(panelForm, "Marca / Fabricante:", 410, 85);
        cmbProveedor = new JComboBox<>(new String[]{
            "- Seleccione -", 
            "Hikvision", 
            "Western Digital", 
            "Dahua", 
            "Seagate", 
            "OEM", 
            "Generico", 
            "Nacional", 
            "Imou", 
            "Ubiquiti"
        });
        cmbProveedor.setBounds(410, 110, 200, 30);
        cmbProveedor.setFont(fuenteCajas);
        cmbProveedor.setBackground(Color.WHITE);
        panelForm.add(cmbProveedor);
        // ---------------------------------------------------

        // Fila 3
        agregarEtiqueta(panelForm, "Stock Dispon.:", 20, 150);
        txtStock = agregarCaja(panelForm, 20, 175, 100);

        agregarEtiqueta(panelForm, "Precio Unit. (S/):", 140, 150);
        txtPrecio = agregarCaja(panelForm, 140, 175, 100);

        // --- BOTONES ---
        JButton btnGuardar = crearBoton("GUARDAR", new Color(46, 204, 113));
        btnGuardar.setBounds(660, 30, 130, 40);
        panelForm.add(btnGuardar);

        JButton btnModificar = crearBoton("MODIFICAR", new Color(52, 152, 219));
        btnModificar.setBounds(800, 30, 130, 40);
        panelForm.add(btnModificar);

        JButton btnEliminar = crearBoton("ELIMINAR", new Color(231, 76, 60));
        btnEliminar.setBounds(660, 80, 130, 40);
        panelForm.add(btnEliminar);

        JButton btnLimpiar = crearBoton("LIMPIAR", new Color(149, 165, 166));
        btnLimpiar.setBounds(800, 80, 130, 40);
        panelForm.add(btnLimpiar);
        
        JButton btnReporte = crearBoton("GENERAR REPORTE", new Color(60, 63, 65));
        btnReporte.setBounds(660, 130, 270, 40); 
        panelForm.add(btnReporte);

        // 4. TABLA
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");          // 0
        modelo.addColumn("CÓDIGO");      // 1
        modelo.addColumn("MODELO");      // 2
        modelo.addColumn("CATEGORÍA");   // 3
        modelo.addColumn("DESCRIPCIÓN"); // 4
        modelo.addColumn("MARCA");       // 5
        modelo.addColumn("STOCK");       // 6
        modelo.addColumn("PRECIO");      // 7

        tblProductos = new JTable(modelo);
        tblProductos.setRowHeight(30);
        tblProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tblProductos.getTableHeader().setBackground(new Color(50,50,50));
        tblProductos.getTableHeader().setForeground(Color.WHITE);
        tblProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scroll = new JScrollPane(tblProductos);
        scroll.setBounds(20, 350, 960, 270); 
        add(scroll);

        // EVENTOS
        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblProductos.rowAtPoint(e.getPoint());
                txtId.setText(obtenerValor(fila, 0));
                txtCodigo.setText(obtenerValor(fila, 1));
                txtModelo.setText(obtenerValor(fila, 2));
                cmbCategoria.setSelectedItem(obtenerValor(fila, 3));
                txtDescripcion.setText(obtenerValor(fila, 4));
                // Selecciona la marca en el combo
                cmbProveedor.setSelectedItem(obtenerValor(fila, 5)); 
                txtStock.setText(obtenerValor(fila, 6));
                txtPrecio.setText(obtenerValor(fila, 7).replace("S/ ", ""));
            }
        });

        btnGuardar.addActionListener(e -> {
            if (validar()) {
                Producto p = capturarDatos();
                if(productoDAO.registrar(p)) { act(); JOptionPane.showMessageDialog(this, "✅ Guardado"); }
            }
        });

        btnModificar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validar()) {
                Producto p = capturarDatos();
                p.setId(Integer.parseInt(txtId.getText()));
                if(productoDAO.modificar(p)) { act(); JOptionPane.showMessageDialog(this, "🔄 Modificado"); }
            }
        });

        btnEliminar.addActionListener(e -> {
            if(!txtId.getText().isEmpty() && JOptionPane.showConfirmDialog(this, "¿Borrar?")==0) {
                productoDAO.eliminar(Integer.parseInt(txtId.getText()));
                act();
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());
        
        btnReporte.addActionListener(e -> generarReportePDF());

        listarProductos();
    }

    // --- MÉTODOS ---
    private void generarReportePDF() {
        try {
            MessageFormat header = new MessageFormat("REPORTE DE INVENTARIO - SOS PERÚ");
            MessageFormat footer = new MessageFormat("Página {0,number,integer}");
            boolean complete = tblProductos.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            if (complete) JOptionPane.showMessageDialog(this, "✅ Reporte generado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    private String obtenerValor(int fila, int col) {
        return tblProductos.getValueAt(fila, col) != null ? tblProductos.getValueAt(fila, col).toString() : "";
    }

    // ==============================================================
    // MÉTODO LISTAR CON ANCHOS DE COLUMNA AJUSTADOS (COMO EN LA FOTO 2)
    // ==============================================================
    private void listarProductos() {
        // 1. Obtener datos y limpiar tabla
        List<Producto> lista = productoDAO.listar();
        modelo.setRowCount(0);
        
        // 2. Llenar filas
        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getId(), 
                p.getCodigo(), 
                p.getModelo(), 
                p.getCategoria(), 
                p.getDescripcion(), 
                p.getProveedor(), 
                p.getStock(), 
                "S/ " + p.getPrecio()
            });
        }
        
        // 3. --- DEFINIR ANCHOS FIJOS (AQUÍ ESTÁ LA SOLUCIÓN) ---
        
        // Columna 0: ID (Muy angosta)
        tblProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblProductos.getColumnModel().getColumn(0).setMaxWidth(40); 

        // Columna 1: CÓDIGO (Angosta)
        tblProductos.getColumnModel().getColumn(1).setPreferredWidth(70);

        // Columna 2: MODELO (Mediana)
        tblProductos.getColumnModel().getColumn(2).setPreferredWidth(120);

        // Columna 3: CATEGORÍA (Mediana)
        tblProductos.getColumnModel().getColumn(3).setPreferredWidth(125);

        // Columna 4: DESCRIPCIÓN (MUY ANCHA - Para ver todo el texto)
        tblProductos.getColumnModel().getColumn(4).setPreferredWidth(310);

        // Columna 5: MARCA (Mediana)
        tblProductos.getColumnModel().getColumn(5).setPreferredWidth(100);

        // Columna 6: STOCK (Angosta)
        tblProductos.getColumnModel().getColumn(6).setPreferredWidth(50);

        // Columna 7: PRECIO (Angosta)
        tblProductos.getColumnModel().getColumn(7).setPreferredWidth(70);
    }

    private void act() { listarProductos(); limpiar(); }
    
    private void limpiar() {
        txtId.setText(""); txtCodigo.setText(""); txtModelo.setText(""); 
        txtDescripcion.setText(""); txtStock.setText(""); txtPrecio.setText("");
        cmbCategoria.setSelectedIndex(0);
        cmbProveedor.setSelectedIndex(0); // Limpiar combo marca
    }

    private boolean validar() {
        return !txtCodigo.getText().isEmpty() && !txtDescripcion.getText().isEmpty();
    }

    private Producto capturarDatos() {
        Producto p = new Producto();
        p.setCodigo(txtCodigo.getText());
        p.setModelo(txtModelo.getText());
        p.setCategoria(cmbCategoria.getSelectedItem().toString());
        p.setDescripcion(txtDescripcion.getText());
        // Aquí capturamos lo que elegiste del combo
        p.setProveedor(cmbProveedor.getSelectedItem().toString());
        try { p.setStock(Integer.parseInt(txtStock.getText())); } catch(Exception e){ p.setStock(0); }
        try { p.setPrecio(Double.parseDouble(txtPrecio.getText())); } catch(Exception e){ p.setPrecio(0.0); }
        return p;
    }

    private void agregarEtiqueta(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t); l.setBounds(x, y, 150, 20); l.setFont(fuenteLabels); l.setForeground(Color.GRAY); p.add(l);
    }
    
    private JTextField agregarCaja(JPanel p, int x, int y, int w) {
        JTextField t = new JTextField(); t.setBounds(x, y, w, 30); t.setFont(fuenteCajas); p.add(t); return t;
    }

    private JButton crearBoton(String t, Color c) {
        JButton b = new JButton(t); b.setBackground(c); b.setForeground(Color.WHITE); b.setFocusPainted(false); b.setFont(new Font("Segoe UI", Font.BOLD, 12)); b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }
}