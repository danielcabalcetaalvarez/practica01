package com.practica01.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "arbol")
public class Arbol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arbol")
    private Integer idArbol;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El tipo de flor no puede estar vacío")
    @Size(max = 50)
    @Column(name = "tipo_flor", nullable = false, length = 50)
    private String tipoFlor;

    @NotBlank(message = "La dureza de la madera no puede estar vacía")
    @Size(max = 50)
    @Column(name = "dureza_madera", nullable = false, length = 50)
    private String durezaMadera;

    @NotBlank(message = "El color no puede estar vacío")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String color;

    @NotNull(message = "La edad no puede estar vacía.")
    @Min(value = 0, message = "La edad debe ser mayor o igual a 0.")
    @Column(name = "edad_anios", nullable = false)
    private Integer edadAnios;

    @Column(name = "ruta_imagen", columnDefinition = "TEXT")
    private String rutaImagen;
}
