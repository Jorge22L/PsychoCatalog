package ni.com.psychocatalog.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoriaDTO(
        Long id,
        String nombre,
        String descripcion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        Integer totalInstrumrnyod
) {
    // Factory method para crear desde entidad
    public static CategoriaDTO fromEntity(ni.com.psychocatalog.model.Categoria categoria){
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .fechaCreacion(categoria.getFechaCreacion())
                .build();
    }
}
