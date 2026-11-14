package model;

import java.time.LocalDate;
import java.util.List;

public record ReporteFuncionesFecha(LocalDate fechaBuscada, List<Funcion> funcionesProgramadas) {
}
