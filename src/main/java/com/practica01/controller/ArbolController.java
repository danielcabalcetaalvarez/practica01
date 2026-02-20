package com.practica01.controller;

import com.practica01.domain.Arbol;
import com.practica01.service.ArbolService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/arbol")
public class ArbolController {

    private final ArbolService arbolService;

    public ArbolController(ArbolService arbolService) {
        this.arbolService = arbolService;
    }

    @GetMapping("/listado")
    public String inicio(Model model) {
        var arboles = arbolService.getArboles();
        model.addAttribute("arboles", arboles);
        model.addAttribute("totalArboles", arboles.size());
        model.addAttribute("arbol", new Arbol());
        return "arbol/listado";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Arbol arbol,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        arbolService.save(arbol, imagenFile);
        redirectAttributes.addFlashAttribute("todoOk", "Árbol guardado correctamente.");
        return "redirect:/arbol/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam("idArbol") Integer idArbol,
            RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String mensaje = "Árbol eliminado correctamente.";

        try {
            arbolService.delete(idArbol);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "El árbol con ese ID no existe.";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "No se puede eliminar el árbol porque tiene datos asociados.";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "Ocurrió un error inesperado al eliminar el árbol.";
        }

        redirectAttributes.addFlashAttribute(titulo, mensaje);
        return "redirect:/arbol/listado";
    }

    @GetMapping("/modificar/{idArbol}")
    public String modificar(
            @PathVariable("idArbol") Integer idArbol,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Arbol> arbolOpt = arbolService.getArbol(idArbol);

        if (arbolOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El árbol con ese ID no existe.");
            return "redirect:/arbol/listado";
        }

        model.addAttribute("arbol", arbolOpt.get());
        return "arbol/modifica";
    }
}
