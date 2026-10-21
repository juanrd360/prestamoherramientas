package com.ejemplo.prestamos.controller;

import com.ejemplo.prestamos.dto.PrestamoRequest;
import com.ejemplo.prestamos.model.Prestamo;
import com.ejemplo.prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarPrestamo(@Valid @RequestBody PrestamoRequest request) {
        try {
            Prestamo nuevoPrestamo = prestamoService.registrarPrestamo(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPrestamo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> consultarPrestamos() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @GetMapping("/herramienta/{id}")
    public ResponseEntity<?> consultarPrestamosPorHerramienta(@PathVariable Long id) {
        try {
            List<Prestamo> prestamos = prestamoService.listarPrestamosPorHerramienta(id);
            return ResponseEntity.ok(prestamos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}