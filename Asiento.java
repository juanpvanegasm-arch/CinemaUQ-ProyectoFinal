public class Asiento {
    private String codigo;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.codigo = fila + numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public boolean estaDisponible() {
        return estado == EstadoAsiento.DISPONIBLE;
    }

    public void ocupar() {
        estado = EstadoAsiento.OCUPADO;
    }

    public void liberar() {
        estado = EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public String obtenerInformacion() {
        String texto;
        if (estado == EstadoAsiento.DISPONIBLE) {
            texto = "Disponible";
        } else {
            texto = "Ocupado";
        }
        return codigo + " " + texto;
    }
}
