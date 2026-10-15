package com.pedro.apibancaria.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class TratadorDeExcecoes {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> recursoNaoEncontrado(RecursoNaoEncontradoException erro){
        return ResponseEntity.status(404).body(Map.of("Mensagem",erro.getMessage()));
    }

    @ExceptionHandler(OperacaoInvalidaException.class)
    public ResponseEntity<Map<String, String>> operacaoInvalida(OperacaoInvalidaException erro){
        return ResponseEntity.status(409).body(Map.of("Mensagem",erro.getMessage()));
    }
}
