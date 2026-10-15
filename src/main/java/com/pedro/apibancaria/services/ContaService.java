package com.pedro.apibancaria.services;

import com.pedro.apibancaria.entities.Cliente;
import com.pedro.apibancaria.entities.Conta;
import com.pedro.apibancaria.exceptions.OperacaoInvalidaException;
import com.pedro.apibancaria.exceptions.RecursoNaoEncontradoException;
import com.pedro.apibancaria.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

        @Autowired
        ContaRepository contaRepository;

        @Autowired
        ClienteService clienteService;


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
}
