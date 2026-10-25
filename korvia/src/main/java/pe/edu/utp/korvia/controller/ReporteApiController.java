package pe.edu.utp.korvia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.korvia.dto.ReservaDetalleDTO;
import pe.edu.utp.korvia.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteApiController {

    private final ReporteService reporteService;

    public ReporteApiController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Filtro dependiente para la tabla de reservas del reporte
    @GetMapping("/reservas")
    public List<ReservaDetalleDTO> reservas(@RequestParam(required = false) Long productoId) {
        if (productoId == null) {
            return reporteService.listarReservas();
        }
        return reporteService.listarReservasPorProducto(productoId);
    }
}
