public class Metricas {
    private final double tempoEsperaMedio;
    private final double tempoRespostaMedio;
    private final double turnaroundMedio;

    public Metricas(double tempoEsperaMedio, double tempoRespostaMedio, double turnaroundMedio) {
        this.tempoEsperaMedio = tempoEsperaMedio;
        this.tempoRespostaMedio = tempoRespostaMedio;
        this.turnaroundMedio = turnaroundMedio;
    }

    public double getTempoEsperaMedio() {
        return tempoEsperaMedio;
    }

    public double getTempoRespostaMedio() {
        return tempoRespostaMedio;
    }

    public double getTurnaroundMedio() {
        return turnaroundMedio;
    }
}
