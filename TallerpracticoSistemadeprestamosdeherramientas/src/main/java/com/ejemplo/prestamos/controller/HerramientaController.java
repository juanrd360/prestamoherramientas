package com.ejemplo.prestamos.controller;

import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.service.HerramientaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/herramientas")
@CrossOrigin(origins = "*")
public class HerramientaController {

    private final HerramientaService herramientaService;

    public HerramientaController(HerramientaService herramientaService) {
        this.herramientaService = herramientaService;
    }

    @PostMapping
    public ResponseEntity<?> registrarHerramienta(@Valid @RequestBody Herramienta herramienta) {
        try {
            Herramienta nueva = herramientaService.registrarHerramienta(herramienta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Herramienta>> consultarHerramientas() {
        return ResponseEntity.ok(herramientaService.listarHerramientas());
    }
}
