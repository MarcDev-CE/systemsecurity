/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author USER
 */
public class ConexionBD {
// 1. CONSTANTES DE CONFIGURACIÓN
    // Es buena práctica tenerlas separadas para cambiarlas fácilmente
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_NAME = "sistema_seguridad";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // En XAMPP suele estar vacío por defecto

    // Variable estática que guardará la instancia única de la conexión
    private static Connection conexion = null;

    // Constructor privado: Evita que se creen instancias con 'new ConexionBD()'
    private ConexionBD() {
    }

    /**
     * Método principal para obtener la conexión.
     * Si no existe, la crea. Si ya existe, devuelve la que está activa.
     * @return Connection Objeto de conexión a MySQL
     */
    public static Connection obtenerConexion() {
        // Si la conexión es nula, intentamos establecerla
        if (conexion == null) {
            try {
                // Paso 1: Cargar el Driver de MySQL en memoria
                Class.forName(DRIVER);
                System.out.println("✅ [ConexionBD] Driver JDBC cargado correctamente.");

                // Paso 2: Establecer la conexión usando DriverManager
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("✅ [ConexionBD] Conectado exitosamente a la base de datos: " + DB_NAME);

            } catch (ClassNotFoundException ex) {
                // Error si no se agregó la librería 'mysql-connector-java.jar'
                System.err.println("❌ [Error Crítico] Falta la librería del Driver MySQL.");
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            
            } catch (SQLException ex) {
                // Error si XAMPP está apagado o los datos son incorrectos
                System.err.println("❌ [Error SQL] No se pudo conectar a la Base de Datos.");
                System.err.println("   -> Verifica que XAMPP (MySQL) esté encendido.");
                System.err.println("   -> Verifica el nombre de la BD: " + DB_NAME);
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conexion;
    }

    /**
     * Método para cerrar la conexión manualmente.
     * Es útil para liberar recursos cuando se cierra la aplicación.
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("🔒 [ConexionBD] Conexión cerrada correctamente.");
            } catch (SQLException ex) {
                System.err.println("⚠️ [Error] No se pudo cerrar la conexión.");
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // =========================================================================
    // MÉTODO MAIN: Únicamente para probar este archivo individualmente (Run File)
    // =========================================================================
    public static void main(String[] args) {
        System.out.println("--- INICIANDO PRUEBA DE CONEXIÓN (SISTEMA DE SEGURIDAD) ---");
        
        // 1. Intentar conectar
        Connection prueba = ConexionBD.obtenerConexion();

        // 2. Verificar estado
        if (prueba != null) {
            System.out.println("🎉 ¡EXITO TOTAL! La aplicación ya puede hablar con MySQL.");
            
            // 3. (Opcional) Probar desconexión para verificar que funciona
            // ConexionBD.cerrarConexion(); 
        } else {
            System.out.println("💀 FALLO: La conexión devolvió 'null'. Revisa los errores rojos arriba.");
        }
    }
}
