package com.pedro.apibancaria.entities;


import enums.TipoMovimentacao;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_movimentacao")
public class Movimentacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;

    @Column(nullable = false)
    private Instant dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipoMov;

    @ManyToOne
    @JoinColumn(name = "conta_id",nullable = false)
    private Conta conta;


    public Movimentacao() {}

    public Movimentacao(BigDecimal valor, Instant dataHora, TipoMovimentacao tipoMov, Conta conta) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.tipoMov = tipoMov;
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public TipoMovimentacao getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(TipoMovimentacao tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movimentacao that = (Movimentacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
