public record Metricas(double tempoRespostaMedio, double tempoEsperaMedio, double turnaroundMedio) {

    @Override
    public String toString() {
        return String.format("%.03f %.03f %.03f", tempoRespostaMedio, tempoEsperaMedio, turnaroundMedio).replaceAll("\\.", ",");
    }
}
