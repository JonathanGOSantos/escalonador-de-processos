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

    public Optional<Integer> getTempoEspera() {
        if (tempoConclusao == null) {
            return Optional.empty();
        }
        return Optional.of(tempoConclusao - tempoChegada - tempoServico + 1);
    }

    public Optional<Integer> getTempoResposta() {
        if (tempoPrimeiraExecucao == null) {
            return Optional.empty();
        }
        return Optional.of(tempoPrimeiraExecucao - tempoChegada);
    }

    public Optional<Integer> getTurnaround() {
        if (tempoConclusao == null) {
            return Optional.empty();
        }
        return Optional.of(tempoConclusao - tempoChegada + 1);
    }
}
