package com.pedro.apibancaria.controller;

import com.pedro.apibancaria.entities.Cliente;
import com.pedro.apibancaria.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.listar();
        return ResponseEntity.ok().body(clientes);
    }

    @PostMapping()
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente){
        Cliente cli = clienteService.cadastrar(cliente);
        return ResponseEntity.status(201).body(cli);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){
        return  ResponseEntity.ok().body(clienteService.buscarPorId(id));
    }
}
