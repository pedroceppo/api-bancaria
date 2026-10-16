package com.pedro.apibancaria.services;

import com.pedro.apibancaria.entities.Cliente;
import com.pedro.apibancaria.entities.Conta;
import com.pedro.apibancaria.entities.Movimentacao;
import com.pedro.apibancaria.exceptions.OperacaoInvalidaException;
import com.pedro.apibancaria.exceptions.RecursoNaoEncontradoException;
import com.pedro.apibancaria.repository.ContaRepository;
import com.pedro.apibancaria.repository.MovimentacaoRepository;
import enums.TipoMovimentacao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class ContaService {

        @Autowired
        ContaRepository contaRepository;

        @Autowired
        ClienteService clienteService;

        @Autowired
        MovimentacaoRepository movimentacaoRepository;

        public Conta abrir(Long clienteId) {
            Cliente cliente = clienteService.buscarPorId(clienteId);
            if(contaRepository.existsByClienteId(clienteId)) {
                throw new OperacaoInvalidaException("Cliente ja possuí conta");
            }
            Conta conta = new Conta();
            conta.setCliente(cliente);
            return contaRepository.save(conta);
        }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Conta não encontrada: " + id
                        )
                );
    }

    @Transactional
    public Conta depositar(Long id, BigDecimal valor) {
            if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new OperacaoInvalidaException("O valor do depósito deve ser maior que zero");
            }
            Conta conta = buscarPorId(id);

            BigDecimal novoSaldo = conta.getSaldo().add(valor);
            conta.setSaldo(novoSaldo);

            Movimentacao movimentacao = new Movimentacao(
                    valor,
                    Instant.now(),
                    TipoMovimentacao.DEPOSITO,
                    conta
            );

            movimentacaoRepository.save(movimentacao);

            return conta;
    }

    @Transactional
    public Conta sacar(Long id,BigDecimal valor) {
            if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new OperacaoInvalidaException("O valor do saque deve ser maior que zero!");
            }
            Conta conta = buscarPorId(id);
            if(conta.getSaldo().compareTo(valor) <= 0) {
                throw new OperacaoInvalidaException("Saldo insuficiente");
            }

            BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
            conta.setSaldo(novoSaldo);

            Movimentacao movimentacao = new Movimentacao(
                    valor,
                    Instant.now(),
                    TipoMovimentacao.SAQUE,
                    conta
            );

            movimentacaoRepository.save(movimentacao);
           return conta;
        }

    @Transactional
    public Conta transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoInvalidaException("O valor a ser transferido deve ser positivo!");
        }

        Conta origem = buscarPorId(idOrigem);

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new OperacaoInvalidaException("Saldo insuficiente");
        }

        Conta destino = buscarPorId(idDestino);

        if (destino.getId().equals(origem.getId())) {
            throw new OperacaoInvalidaException("A conta deve ser diferente da sua!");
        }

        BigDecimal origemSaldo = origem.getSaldo().subtract(valor);
        origem.setSaldo(origemSaldo);

        Movimentacao movimentacaoOrigem = new Movimentacao(
                valor,
                Instant.now(),
                TipoMovimentacao.TRANSFERENCIA_ENVIADA,
                origem
        );

        movimentacaoRepository.save(movimentacaoOrigem);

        BigDecimal destinoSaldo = destino.getSaldo().add(valor);
        destino.setSaldo(destinoSaldo);

        Movimentacao movimentacaoDestino = new Movimentacao(
                valor,
                Instant.now(),
                TipoMovimentacao.TRANSFERENCIA_RECEBIDA,
                destino
        );

        movimentacaoRepository.save(movimentacaoDestino);

        return origem;


    }

}
