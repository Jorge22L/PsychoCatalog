package ni.com.psychocatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        List<Map<String, String>> instrumentos = List.of(
                Map.of(
                        "nombre", "Inventario de Ansiedad",
                        "descripcion", "Instrumento para medir niveles de ansiedad",
                        "categoria","Ansiedad",
                        "imagen", "/images/ansiedad.jpg"
                ),
                Map.of(
                        "nombre", "Escala de Depresión",
                        "descripcion", "Evalúa síntomas depresivos en población",
                        "categoria", "Depresión",
                        "imagen", "/images/depresion.jpg"
                ),
                Map.of(
                        "nombre", "Test de estrés académico",
                        "descripcion", "Permite identificar factores de estrés en el entorno académico",
                        "categoria", "Estrés",
                        "imagen", "/images/estres.jpg"
                )
        );

        model.addAttribute("titulo", "Catálogo de Instrumentos Psicológicos");
        model.addAttribute("instrumentos", instrumentos);

        return "index";
    }
}
