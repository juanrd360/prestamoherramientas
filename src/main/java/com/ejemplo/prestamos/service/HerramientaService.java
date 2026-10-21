package com.ejemplo.prestamos.service;

import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.repository.HerramientaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = herramientaRepository;
    }

    @Transactional
    public Herramienta registrarHerramienta(Herramienta herramienta) {
        if (herramienta.getCantidad() == null || herramienta.getCantidad() < 0) {
            throw new IllegalArgumentException("No se permiten cantidades negativas al registrar una herramienta");
        }
        return herramientaRepository.save(herramienta);
    }

    @Transactional(readOnly = true)
    public List<Herramienta> listarHerramientas() {
        return herramientaRepository.findAll();
    }
}
