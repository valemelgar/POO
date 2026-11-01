// ============================================================
// Propósito: enumeración con los estados posibles de la conexión de una
// integración. Usar un enum evita errores de escritura 
// ============================================================
public enum EstadoConexion {
    CONECTADA,      // la API responde con normalidad
    DEGRADADA,      // la API responde, pero lento o con errores ocasionales
    DESCONECTADA    // la API no responde; la integración se considera inactiva
}