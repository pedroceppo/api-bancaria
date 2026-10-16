package com.pedro.apibancaria.dto;

import java.math.BigDecimal;

public class ValorMovimentacaoRequest {

    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
