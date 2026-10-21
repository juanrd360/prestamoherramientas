package com.ejemplo.prestamos.service;

import com.ejemplo.prestamos.dto.PrestamoRequest;
import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.model.Prestamo;
import com.ejemplo.prestamos.repository.HerramientaRepository;
import com.ejemplo.prestamos.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final HerramientaRepository herramientaRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, HerramientaRepository herramientaRepository) {
        this.prestamoRepository = prestamoRepository;
        this.herramientaRepository = herramientaRepository;
    }

    @Transactional
    public Prestamo registrarPrestamo(PrestamoRequest request) {
        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad a prestar debe ser mayor a cero");
        }

        Herramienta herramienta = herramientaRepository.findById(request.getHerramientaId())
                .orElseThrow(() -> new IllegalArgumentException("La herramienta con ID " + request.getHerramientaId() + " no existe"));

        if (request.getCantidad() > herramienta.getCantidad()) {
            throw new IllegalArgumentException("Unidades insuficientes. Disponibles: " + herramienta.getCantidad() + ", Solicitadas: " + request.getCantidad());
        }

        herramienta.setCantidad(herramienta.getCantidad() - request.getCantidad());
        herramientaRepository.save(herramienta);

        Prestamo prestamo = new Prestamo();
        prestamo.setResponsable(request.getResponsable());
        prestamo.setFechaPrestamo(request.getFechaPrestamo());
        prestamo.setCantidad(request.getCantidad());
        prestamo.setHerramienta(herramienta);

        return prestamoRepository.save(prestamo);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosPorHerramienta(Long herramientaId) {
        if (!herramientaRepository.existsById(herramientaId)) {
            throw new IllegalArgumentException("La herramienta con ID " + herramientaId + " no existe");
        }
        return prestamoRepository.findByHerramientaId(herramientaId);
    }
}
