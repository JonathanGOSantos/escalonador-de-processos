import java.util.*;

public class TestRunner {
    public static void main(String[] args) {
        List<Processo> processos = new ArrayList<>();
        processos.add(new Processo(1, 8));
        processos.add(new Processo(4, 4));
        processos.add(new Processo(5, 3));
        processos.add(new Processo(5, 3));
        processos.add(new Processo(15, 4));
        processos.add(new Processo(16, 2));
        processos.add(new Processo(17, 1));
        
        AlgoritmoRR rr = new AlgoritmoRR(3);
        Metricas m = rr.executar(processos);
        System.out.println(m);
    }
}
