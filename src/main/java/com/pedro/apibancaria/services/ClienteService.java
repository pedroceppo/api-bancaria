package com.pedro.apibancaria.services;

import com.pedro.apibancaria.entities.Cliente;
import com.pedro.apibancaria.exceptions.OperacaoInvalidaException;
import com.pedro.apibancaria.exceptions.RecursoNaoEncontradoException;
import com.pedro.apibancaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente cadastrar(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new OperacaoInvalidaException("CPF já cadastrado!");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Cliente não encontrado: " + id
                        )
                );
    }
}