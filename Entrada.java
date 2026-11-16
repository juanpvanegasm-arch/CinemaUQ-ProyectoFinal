public abstract class Entrada {
    private String nombreTipo;
    // Porcentaje del valor base de la función que paga este tipo de entrada
    private double porcentaje;

    public Entrada(String nombreTipo, double porcentaje) {
        this.nombreTipo = nombreTipo;
        this.porcentaje = porcentaje;
    }

    public double calcularValor(double valorBase) {
        return valorBase * porcentaje / 100;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String obtenerInformacion() {
        return nombreTipo + ": " + porcentaje + "% del valor base de la función";
    }
}
