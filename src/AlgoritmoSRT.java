import java.util.*;

public class AlgoritmoSRT implements Algoritmo {
    @Override
    public Metricas executar(List<Processo> processos) {
        Queue<Processo> fila = new PriorityQueue<>(Comparator.comparingInt(Processo::getTempoRestante));

        int ultimoAChegar = processos.stream().max(Comparator.comparing(Processo::getTempoChegada)).get().getTempoChegada();
        int tempo = 0;
        while (tempo <= ultimoAChegar || !fila.isEmpty()) {
            int finalTempo = tempo;
            fila.addAll(processos.stream().filter(p -> p.getTempoChegada().equals(finalTempo)).toList());
            Processo primeiro = fila.peek();
            if (primeiro != null) {
                fila.remove(primeiro);
                primeiro.setTempoPrimeiraExecucao(tempo);
                primeiro.executar();
                if (primeiro.concluido()) {
                    primeiro.setTempoConclusao(tempo);
                } else {
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
