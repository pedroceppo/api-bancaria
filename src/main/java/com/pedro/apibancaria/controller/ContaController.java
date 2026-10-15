package com.pedro.apibancaria.controller;

import com.pedro.apibancaria.dto.AbrirContaRequest;
import com.pedro.apibancaria.entities.Conta;
import com.pedro.apibancaria.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> abrir (@RequestBody AbrirContaRequest pedido){
        Conta conta = contaService.abrir(pedido.getClienteId());

        return ResponseEntity.status(201).body(conta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id){
        Conta conta = contaService.buscarPorId(id);
        return ResponseEntity.ok().body(conta);
    }
}
