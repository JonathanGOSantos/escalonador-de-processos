import java.util.*;

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
        Queue<Processo> fila = new LinkedList<>();
        int ultimoAChegar = processos.stream().max(Comparator.comparing(Processo::getTempoChegada)).get().getTempoChegada();
        int tempo = 0;
        int execucoes = 0;
        while (tempo <= ultimoAChegar || !fila.isEmpty()) {
            int finalTempo = tempo;
            fila.addAll(processos.stream().filter(p -> p.getTempoChegada().equals(finalTempo)).toList());
            Processo primeiro = fila.peek();
            if (primeiro != null) {
                primeiro.setTempoPrimeiraExecucao(tempo);
                primeiro.executar();
                execucoes++;
                if (primeiro.concluido()) {
                    fila.remove(primeiro);
                    primeiro.setTempoConclusao(tempo);
                    execucoes = 0;
                } else if (execucoes == quantum) {
                    fila.remove(primeiro);
                    execucoes = 0;
                    fila.add(primeiro);
                }
            }
            tempo++;
        }

        double mediaTempoResposta = (double) processos.stream().map(Processo::getTempoResposta).map(Optional::get).reduce(Integer::sum).get() / processos.size();
        double mediaTempoEspera = (double) processos.stream().map(Processo::getTempoEspera).map(Optional::get).reduce(Integer::sum).get() / processos.size();;
        double mediaTurnaround = (double) processos.stream().map(Processo::getTurnaround).map(Optional::get).reduce(Integer::sum).get() / processos.size();;
        return new Metricas(mediaTempoResposta, mediaTempoEspera, mediaTurnaround);
    }
}
