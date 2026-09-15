import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner3 {
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
        while (indiceProximoProcesso.get() < processos.size() || !fila.isEmpty()) {
            fila.addAll(Processo.getProcessosEm(processos, tempo, indiceProximoProcesso));
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
        
        for (int i = 0; i < processos.size(); i++) {
            Processo p = processos.get(i);
            System.out.printf("P%d: Arr=%d Serv=%d First=%d Fin=%d Resp=%d Wait=%d Turn=%d\n",
                i+1, p.getTempoChegada(), p.getTempoServico(), p.getTempoResposta() + p.getTempoChegada(), 
                p.getTurnaround() + p.getTempoChegada() - 1, p.getTempoResposta(), p.getTempoEspera(), p.getTurnaround());
        }
    }
}
