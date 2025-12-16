/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import javax.swing.*;
import java.awt.Font;

public class SistemaPrincipal extends JFrame {

    public SistemaPrincipal() {
        // 1. Configurar la ventana
        setTitle("SISTEMA DE SEGURIDAD - SOS PERÚ");
        setSize(1020, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        
        // 2. Crear las Pestañas
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // --- AQUÍ ESTÁ EL ORDEN CORRECTO (SOLO 3 LÍNEAS) ---
        
        // 1° Pestaña: EL DASHBOARD (Inicio)
        pestañas.addTab("🏠 INICIO", new PanelInicio());

        // 2° Pestaña: INVENTARIO
        pestañas.addTab("📦 INVENTARIO", new PanelProductos());
        
        // 3° Pestaña: PERSONAL
        pestañas.addTab("👥 PERSONAL", new PanelUsuarios());
        
        // ---------------------------------------------------
        
        // 3. Agregar pestañas a la ventana
        add(pestañas);
    }

    // --- PUNTO DE ARRANQUE DEL SISTEMA ---
    public static void main(String[] args) {
        // Poner estilo visual de Windows para que se vea moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        // Abrir la ventana
        java.awt.EventQueue.invokeLater(() -> {
            new SistemaPrincipal().setVisible(true);
        });
    }
}