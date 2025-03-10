package com.narvasoft.agenda.controller;

import com.narvasoft.agenda.model.Salas;
import com.narvasoft.agenda.repo.SalasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/salas")
@Controller
public class SalasController {

    @Autowired
    private SalasRepository salasRepository;

    // ✅ Carga el formulario "nuevoSala.html"
    @GetMapping
    public String index(Model model) {
        List<Salas> salas = salasRepository.findAll();
        model.addAttribute("salas", salas);

        if (!model.containsAttribute("sala")) {
            model.addAttribute("sala", new Salas());
        }

        return "nuevoSala";
    }

    // ✅ Muestra el formulario para crear una nueva sala
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("sala")) {
            model.addAttribute("sala", new Salas());
        }
        return "nuevoSala";
    }

    // ✅ Guarda una nueva sala en la base de datos y muestra un mensaje de éxito
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute @Validated Salas sala,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("sala", sala);
            return "nuevoSala";
        }

        if (sala == null) {
            attr.addFlashAttribute("msgError", "Error: La sala no puede ser nula.");
            return "redirect:/salas/nuevo";
        }

        salasRepository.save(sala);
        attr.addFlashAttribute("msgExito", "Registro de sala guardado exitosamente!");

        return "redirect:/salas";
    }

    // ✅ Muestra el formulario para editar una sala existente
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes attr) {
        Optional<Salas> salaOpt = salasRepository.findById(id);

        if (salaOpt.isEmpty()) {
            attr.addFlashAttribute("msgError", "No se encontró la sala con ID " + id);
            return "redirect:/salas";
        }

        model.addAttribute("sala", salaOpt.get());
        return "nuevoSala";
    }

    // ✅ Actualiza una sala existente
    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute @Validated Salas sala,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attr) {

        Optional<Salas> salaOpt = salasRepository.findById(id);
        if (salaOpt.isEmpty()) {
            attr.addFlashAttribute("msgError", "No se encontró la sala para actualizar.");
            return "redirect:/salas";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("sala", sala);
            return "nuevoSala";
        }

        Salas salaFromDB = salaOpt.get();
        salaFromDB.setFecha(sala.getFecha());
        salaFromDB.setGrado(sala.getGrado());
        salaFromDB.setSeccion(sala.getSeccion());
        salaFromDB.setAsignatura(sala.getAsignatura());
        salaFromDB.setPc1(sala.isPc1());
        salaFromDB.setPc2(sala.isPc2());
        salaFromDB.setPc3(sala.isPc3());
        salaFromDB.setPc4(sala.isPc4());
        salaFromDB.setPc5(sala.isPc5());
        salaFromDB.setPc6(sala.isPc6());
        salaFromDB.setPc7(sala.isPc7());
        salaFromDB.setPc8(sala.isPc8());
        salaFromDB.setPc9(sala.isPc9());
        salaFromDB.setPc10(sala.isPc10());
        salaFromDB.setNovedad(sala.getNovedad());

        salasRepository.save(salaFromDB);
        attr.addFlashAttribute("msgExito", "Sala actualizada exitosamente!");

        return "redirect:/salas";
    }

    // ✅ Elimina una sala
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes attr) {
        Optional<Salas> salaOpt = salasRepository.findById(id);

        if (salaOpt.isPresent()) {
            salasRepository.delete(salaOpt.get());
            attr.addFlashAttribute("msgExito", "Registro eliminado exitosamente");
        } else {
            attr.addFlashAttribute("msgError", "No se encontró la sala para eliminar.");
        }

        return "redirect:/salas";
    }
}
