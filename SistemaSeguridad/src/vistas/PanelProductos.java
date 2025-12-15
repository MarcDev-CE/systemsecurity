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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelProductos extends JPanel {

    // Componentes
    private JTextField txtCodigo, txtDescripcion, txtProveedor, txtStock, txtPrecio;
    private JTextField txtId; // Campo oculto para guardar el ID seleccionado
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private ProductoDAO productoDAO = new ProductoDAO();

    public PanelProductos() {
        setLayout(null);
        setBackground(new Color(245, 245, 250));

        // 1. Encabezado
        JPanel header = new JPanel();
        header.setBounds(20, 20, 940, 60);
        header.setBackground(new Color(25, 118, 210));
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("INVENTARIO DE EQUIPOS CCTV");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 10, 500, 40);
        header.add(lblTitulo);

        // 2. Formulario (Izquierda)
        JPanel panelForm = new JPanel();
        panelForm.setBounds(20, 100, 300, 450);
        panelForm.setBackground(Color.WHITE);
        panelForm.setLayout(null);
        panelForm.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(panelForm);

        // Campo ID (Invisible o deshabilitado, sirve para saber cuál editar)
        txtId = new JTextField();
        txtId.setVisible(false); // Lo ocultamos porque el usuario no debe tocarlo
        panelForm.add(txtId);

        agregarCampo(panelForm, "Código:", 20, txtCodigo = new JTextField());
        agregarCampo(panelForm, "Descripción:", 90, txtDescripcion = new JTextField());
        agregarCampo(panelForm, "Proveedor:", 160, txtProveedor = new JTextField());
        agregarCampo(panelForm, "Stock:", 230, txtStock = new JTextField());
        agregarCampo(panelForm, "Precio (S/):", 300, txtPrecio = new JTextField());

        // --- BOTONES DE ACCIÓN ---
        
        // Botón GUARDAR (Verde)
        JButton btnGuardar = crearBoton("GUARDAR", new Color(46, 204, 113));
        btnGuardar.setBounds(30, 360, 110, 35);
        panelForm.add(btnGuardar);

        // Botón MODIFICAR (Azul Oscuro)
        JButton btnModificar = crearBoton("MODIFICAR", new Color(52, 152, 219));
        btnModificar.setBounds(160, 360, 110, 35);
        panelForm.add(btnModificar);

        // Botón ELIMINAR (Rojo)
        JButton btnEliminar = crearBoton("ELIMINAR", new Color(231, 76, 60));
        btnEliminar.setBounds(30, 405, 110, 35);
        panelForm.add(btnEliminar);

        // Botón NUEVO / LIMPIAR (Gris)
        JButton btnNuevo = crearBoton("LIMPIAR", new Color(149, 165, 166));
        btnNuevo.setBounds(160, 405, 110, 35);
        panelForm.add(btnNuevo);

        // 3. Tabla (Derecha)
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("CÓDIGO");
        modelo.addColumn("DESCRIPCIÓN");
        modelo.addColumn("MARCA");
        modelo.addColumn("STOCK");
        modelo.addColumn("PRECIO");

        tblProductos = new JTable(modelo);
        tblProductos.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tblProductos);
        scroll.setBounds(340, 100, 620, 450);
        add(scroll);

        // =======================================================
        // EVENTOS (LA MAGIA)
        // =======================================================
        
        // A) CLIC EN LA TABLA: Pasa los datos a las cajitas
        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblProductos.rowAtPoint(e.getPoint());
                txtId.setText(tblProductos.getValueAt(fila, 0).toString());
                txtCodigo.setText(tblProductos.getValueAt(fila, 1).toString());
                txtDescripcion.setText(tblProductos.getValueAt(fila, 2).toString());
                txtProveedor.setText(tblProductos.getValueAt(fila, 3).toString());
                txtStock.setText(tblProductos.getValueAt(fila, 4).toString());
                // El precio viene como "2450.0" o "S/ 2450.0", hay que limpiarlo si tiene texto extra
                String precioStr = tblProductos.getValueAt(fila, 5).toString();
                txtPrecio.setText(precioStr.replace("S/ ", ""));
            }
        });

        // B) BOTÓN GUARDAR
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                Producto p = new Producto();
                p.setCodigo(txtCodigo.getText());
                p.setDescripcion(txtDescripcion.getText());
                p.setProveedor(txtProveedor.getText());
                p.setStock(Integer.parseInt(txtStock.getText()));
                p.setPrecio(Double.parseDouble(txtPrecio.getText()));
                
                if(productoDAO.registrar(p)) {
                    JOptionPane.showMessageDialog(this, "✅ Producto Guardado");
                    limpiarTabla();
                    listarProductos();
                    limpiarCampos();
                }
            }
        });

        // C) BOTÓN MODIFICAR
        btnModificar.addActionListener(e -> {
            if ("".equals(txtId.getText())) {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un producto de la tabla primero.");
            } else {
                if (validarCampos()) {
                    Producto p = new Producto();
                    p.setId(Integer.parseInt(txtId.getText())); // ID ES CLAVE PARA MODIFICAR
                    p.setCodigo(txtCodigo.getText());
                    p.setDescripcion(txtDescripcion.getText());
                    p.setProveedor(txtProveedor.getText());
                    p.setStock(Integer.parseInt(txtStock.getText()));
                    p.setPrecio(Double.parseDouble(txtPrecio.getText()));

                    if(productoDAO.modificar(p)) {
                        JOptionPane.showMessageDialog(this, "✅ Producto Modificado");
                        limpiarTabla();
                        listarProductos();
                        limpiarCampos();
                    }
                }
            }
        });

        // D) BOTÓN ELIMINAR
        btnEliminar.addActionListener(e -> {
            if (!"".equals(txtId.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este producto?");
                if (pregunta == 0) { // 0 es SI
                    int id = Integer.parseInt(txtId.getText());
                    productoDAO.eliminar(id);
                    JOptionPane.showMessageDialog(this, "🗑️ Producto Eliminado");
                    limpiarTabla();
                    listarProductos();
                    limpiarCampos();
                }
            } else {
                 JOptionPane.showMessageDialog(this, "⚠️ Seleccione un producto para eliminar.");
            }
        });

        // E) BOTÓN LIMPIAR
        btnNuevo.addActionListener(e -> cleaning());

        // Cargar datos al inicio
        listarProductos();
    }

    // --- MÉTODOS DE AYUDA ---
    
        private void listarProductos() {
        List<Producto> lista = productoDAO.listar();
        modelo = (DefaultTableModel) tblProductos.getModel();
        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getId(), p.getCodigo(), p.getDescripcion(), p.getProveedor(), p.getStock(), p.getPrecio()
            });
        }
        tblProductos.setModel(modelo);
        
        // --- ¡AQUÍ ESTÁ LA CLAVE! ---
        // Ajustamos la columna 2 ("DESCRIPCIÓN") que es la que tiene texto largo.
        // Le damos 20 píxeles extra de margen para que no quede pegado.
        ajustarAnchoColumna(tblProductos, 20, 20); 
    }
    
    
    
    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtProveedor.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
    }
    
    private void cleaning(){
        limpiarCampos();
    }

    private boolean validarCampos() {
        if ("".equals(txtCodigo.getText()) || "".equals(txtDescripcion.getText()) || 
            "".equals(txtStock.getText()) || "".equals(txtPrecio.getText())) {
            JOptionPane.showMessageDialog(this, "Complete los campos vacíos");
            return false;
        }
        return true;
    }

    private void agregarCampo(JPanel panel, String texto, int y, JTextField campo) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(30, y, 200, 20);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl);
        campo.setBounds(30, y + 25, 240, 30);
        panel.add(campo);
    }
    
    // Método para crear botones bonitos rápido
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false); // Plano
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    // =====================================================================
    // MÉTODO MÁGICO: AJUSTAR ANCHO DE COLUMNA AL CONTENIDO MÁS LARGO
    // =====================================================================
    private void ajustarAnchoColumna(JTable table, int columnaIndex, int padding) {
        // 1. Obtener el modelo de la columna
        javax.swing.table.TableColumnModel columnModel = table.getColumnModel();
        javax.swing.table.TableColumn column = columnModel.getColumn(columnaIndex);
        int anchoMaximo = 0;

        // 2. Calcular ancho del ENCABEZADO
        javax.swing.table.TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        java.awt.Component headerComp = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, columnaIndex);
        anchoMaximo = Math.max(anchoMaximo, headerComp.getPreferredSize().width);

        // 3. Calcular ancho de cada CELDA de datos
        for (int fila = 0; fila < table.getRowCount(); fila++) {
            javax.swing.table.TableCellRenderer cellRenderer = table.getCellRenderer(fila, columnaIndex);
            java.awt.Component comp = table.prepareRenderer(cellRenderer, fila, columnaIndex);
            anchoMaximo = Math.max(anchoMaximo, comp.getPreferredSize().width);
        }

        // 4. Establecer el nuevo ancho (+ un pequeño margen)
        column.setPreferredWidth(anchoMaximo + padding);
    }
    
    
    
    
}