import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner2 {
    public static void main(String[] args) {
        List<Processo> processos = new ArrayList<>();
        processos.add(new Processo(1, 8));
        processos.add(new Processo(4, 4));
        processos.add(new Processo(5, 3));
        processos.add(new Processo(5, 3));
        processos.add(new Processo(15, 4));
        processos.add(new Processo(16, 2));
        processos.add(new Processo(17, 1));
        
        int quantum = 3;
        Queue<Processo> fila = new LinkedList<>();
        AtomicInteger indiceProximoProcesso = new AtomicInteger(0);
        int tempo = 0;
        int execucoes = 0;
        Processo atual = null;
        
        while (indiceProximoProcesso.get() < processos.size() || !fila.isEmpty() || atual != null) {
            // New arrivals
            List<Processo> novos = Processo.getProcessosEm(processos, tempo, indiceProximoProcesso);
            fila.addAll(novos);
            
            // Re-add preempted AFTER new arrivals
            if (atual != null && !atual.concluido() && execucoes == quantum) {
                fila.add(atual);
                atual = null;
                execucoes = 0;
            }
            
            if (atual == null || atual.concluido() || execucoes == quantum) {
                atual = fila.poll();
                execucoes = 0;
            }
            
            if (atual != null) {
                atual.setTempoPrimeiraExecucao(tempo);
                atual.executar();
                execucoes++;
                if (atual.concluido()) {
                    atual.setTempoConclusao(tempo);
                    atual = null; // will pick new one next
                }
            }
            tempo++;
        }
        
        double mediaTempoResposta = Processo.getMediaTempoResposta(processos);
        double mediaTempoEspera = Processo.getMediaTempoEspera(processos);
        double mediaTurnaround = Processo.getMediaTurnaround(processos);
        Metricas m = new Metricas(mediaTempoResposta, mediaTempoEspera, mediaTurnaround);
        System.out.println(m);
    }
}
