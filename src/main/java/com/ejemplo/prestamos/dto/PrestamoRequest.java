package com.ejemplo.prestamos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoRequest {

    @NotBlank(message = "El nombre del responsable es obligatorio")
    private String responsable;

    @NotBlank(message = "La fecha de préstamo es obligatoria")
    private String fechaPrestamo;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad a prestar debe ser mayor a cero")
    private Integer cantidad;

    @NotNull(message = "El ID de la herramienta es obligatorio")
    private Long herramientaId;
}