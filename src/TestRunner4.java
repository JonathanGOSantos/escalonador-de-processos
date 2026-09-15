import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner4 {
    public static void main(String[] args) {
        List<Processo> p = new ArrayList<>();
        p.add(new Processo(0, 2));
        p.add(new Processo(2, 3));
        p.add(new Processo(3, 1));
        p.add(new Processo(3, 7));
        p.add(new Processo(6, 3));
        p.add(new Processo(6, 2));
        p.add(new Processo(12, 2));
        p.add(new Processo(15, 1));
        
        System.out.println("Buggy RR (current code):");
        AlgoritmoRR rr = new AlgoritmoRR(2);
        System.out.println(rr.executar(duplicar(p)));
        
        System.out.println("Standard RR:");
        System.out.println(runStandardRR(duplicar(p), 2));
    }
    
    private static List<Processo> duplicar(List<Processo> p) {
        List<Processo> n = new ArrayList<>();
        for(Processo x : p) n.add(new Processo(x.getTempoChegada(), x.getTempoServico()));
        return n;
    }
    
    private static Metricas runStandardRR(List<Processo> processos, int quantum) {
        Queue<Processo> fila = new LinkedList<>();
        AtomicInteger indiceProximoProcesso = new AtomicInteger(0);
        int tempo = 0;
        int execucoes = 0;
        Processo atual = null;
        
        while (indiceProximoProcesso.get() < processos.size() || !fila.isEmpty() || atual != null) {
            List<Processo> novos = Processo.getProcessosEm(processos, tempo, indiceProximoProcesso);
            fila.addAll(novos);
            
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
                    atual = null;
                }
            }
            tempo++;
        }
        
        return new Metricas(Processo.getMediaTempoResposta(processos), Processo.getMediaTempoEspera(processos), Processo.getMediaTurnaround(processos));
    }
}
