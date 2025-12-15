/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas;
import dao.ProductoDAO;
import modelos.Producto;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author USER
 */
public class VentanaProductos extends javax.swing.JPanel {
    
    private ProductoDAO productoDAO = new ProductoDAO();
    private DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Creates new form VentanaProductos
     */
    public VentanaProductos() {
        initComponents();
        listarProductos(); // Cargar la tabla al abrir
    }
///FIN
    
    // Método para llenar la tabla visual
    private void listarProductos() {
        List<Producto> lista = productoDAO.listar();
        modelo = (DefaultTableModel) tblProductos.getModel();
        Object[] ob = new Object[6];
        
        // Limpiar tabla actual
        modelo.setRowCount(0);
        
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getCodigo();
            ob[2] = lista.get(i).getDescripcion();
            ob[3] = lista.get(i).getProveedor();
            ob[4] = lista.get(i).getStock();
            ob[5] = lista.get(i).getPrecio();
            modelo.addRow(ob);
        }
        tblProductos.setModel(modelo);
    }

    // Lógica para el botón GUARDAR
    // (Debes llamar a este método dentro del evento del botón btnGuardar)
    private void guardarProducto() {
        if (!"".equals(txtCodigo.getText()) || !"".equals(txtDescripcion.getText())) {
            Producto p = new Producto();
            p.setCodigo(txtCodigo.getText());
            p.setDescripcion(txtDescripcion.getText());
            p.setProveedor(txtProveedor.getText());
            
            // Convertir texto a números con seguridad
            try {
                p.setStock(Integer.parseInt(txtStock.getText()));
                p.setPrecio(Double.parseDouble(txtPrecio.getText()));
            } catch (NumberFormatException e) {
                p.setStock(0);
                p.setPrecio(0.0);
            }
            
            p.setEstado(1); // 1 = Activo por defecto

            productoDAO.registrar(p);
            JOptionPane.showMessageDialog(null, "Producto Registrado");
            
            // Limpiar campos
            txtCodigo.setText("");
            txtDescripcion.setText("");
            txtProveedor.setText("");
            txtStock.setText("");
            txtPrecio.setText("");
            
            // Actualizar tabla
            listarProductos();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos");
        }
    }
    
    // --- IMPORTANTE: VINCULACIÓN DEL BOTÓN ---
    // Ve a la vista "Design", doble clic en tu botón "Guardar" 
    // y pega esta línea dentro:
    // guardarProducto();

    // El código generado por NetBeans (initComponents) estará aquí abajo oculto...
    
    
    
    
    
    
    
//==============================================////////////////////////////////////////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPrecio = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtProveedor = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();

        txtPrecio.setText("Precio");

        txtCodigo.setText("Codigo");

        txtProveedor.setText("Proveedor");

        txtDescripcion.setText("Descripcion");

        txtStock.setText("Stock");

        btnGuardar.setText("Guardar");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(btnGuardar)))
                .addContainerGap(529, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
