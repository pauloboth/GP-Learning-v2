package br.org.gdt.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Avaliacao implements Serializable {

    @SequenceGenerator(name = "genavaliacao", sequenceName = "seqavaliacao", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genavaliacao")

    @Id
    @Column(name = "ava_id")
    private int id;

    @Column(name = "ava_ivalor", length = 5)
    private int valor;

    @Column(name = "ava_dcriacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date criacao;
    
    @ManyToOne
    @JoinColumn(name = "tpp_id")
    private EtapaParametro etapaParametro;

    @ManyToOne
    @JoinColumn(name = "eta_id")
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name = "ind_id")
    private Indicador indicador;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private Projeto projeto;

    public Avaliacao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public EtapaParametro getEtapaParametro() {
        return etapaParametro;
    }

    public void setEtapaParametro(EtapaParametro etapaParametro) {
        this.etapaParametro = etapaParametro;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Indicador getIndicador() {
        return indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

}
