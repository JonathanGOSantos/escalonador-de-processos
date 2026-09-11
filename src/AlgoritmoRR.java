import java.util.List;

public class AlgoritmoRR implements Algoritmo {
    private int quantum;

    public AlgoritmoRR(int quantum) {
        if (quantum <= 0) {
            throw new IllegalArgumentException("Quantum deve ser maior que 0");
        }
        this.quantum = quantum;
    }

    @Override
    public Metricas executar(List<Processo> processos) {
        return null;
    }
}
