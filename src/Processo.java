import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Processo {
    private final Integer tempoChegada;
    private final Integer tempoServico;
    private Integer tempoRestante;
    private Integer tempoPrimeiraExecucao;
    private Integer tempoConclusao;

    public Processo(int tempoChegada, int tempoServico) {
        if (tempoChegada < 0) {
            throw new IllegalArgumentException("O tempo de chegada não pode ser menor que 0");
        }
        this.tempoChegada = tempoChegada;

        if (tempoServico <= 0) {
            throw new IllegalArgumentException("O tempo de serviço deve ser maior que 0");
        }
        this.tempoServico = tempoServico;
        this.tempoRestante = tempoServico;
    }

    public boolean concluido() {
        return tempoRestante == 0;
    }

    public void executar() {
        if (tempoRestante > 0) {
            tempoRestante--;
        }
    }

    public Integer getTempoChegada() {
        return tempoChegada;
    }

    public Integer getTempoServico() {
        return tempoServico;
    }

    public Integer getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoPrimeiraExecucao(Integer tempoPrimeiraExecucao) {
        if (this.tempoPrimeiraExecucao == null) {
            this.tempoPrimeiraExecucao = tempoPrimeiraExecucao;
        }
    }

    public void setTempoConclusao(Integer tempoConclusao) {
        this.tempoConclusao = tempoConclusao;
    }

    public Integer getTempoEspera() {
        return tempoConclusao - tempoChegada - tempoServico + 1;
    }

    public void setTempoRestante(Integer tempoRestante) {
        this.tempoRestante = tempoRestante;
    }

    public Integer getTempoResposta() {
        return tempoPrimeiraExecucao - tempoChegada;
    }

    public Integer getTurnaround() {
        return tempoConclusao - tempoChegada + 1;
    }

    public static Integer getUltimoAChegar(List<Processo> processos) {
        return processos.stream().max(Comparator.comparing(Processo::getTempoChegada)).orElseThrow().getTempoChegada();
    }

    public static List<Processo> getProcessosEm(List<Processo> processos, int finalTempo) {
        return processos.stream().filter(p -> p.getTempoChegada().equals(finalTempo)).toList();
    }

    public static double getMediaTurnaround(List<Processo> processos) {
        return (double) processos.stream().map(Processo::getTurnaround).reduce(Integer::sum).orElse(0) / processos.size();
    }

    public static double getMediaTempoEspera(List<Processo> processos) {
        return (double) processos.stream().map(Processo::getTempoEspera).reduce(Integer::sum).orElse(0) / processos.size();
    }

    public static double getMediaTempoResposta(List<Processo> processos) {
        return (double) processos.stream().map(Processo::getTempoResposta).reduce(Integer::sum).orElse(0) / processos.size();
    }
}
