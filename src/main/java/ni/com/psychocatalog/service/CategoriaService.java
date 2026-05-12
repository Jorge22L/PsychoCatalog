package ni.com.psychocatalog.service;

import ni.com.psychocatalog.model.Categoria;
import ni.com.psychocatalog.model.dto.CategoriaDTO;
import ni.com.psychocatalog.model.dto.CategoriaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CategoriaService {
    // Consultas
    Page<CategoriaDTO> listarPaginado(Pageable pageable);
    List<CategoriaDTO> listarTodos();
    CategoriaDTO obtenerPorId(Long id);

    // Búsquedas
    List<CategoriaDTO> buscarPorNombre(String termino);
    boolean existePorNombre(String nombre);

    // Operaciones de negocio
    CategoriaDTO crear(CategoriaRequest request);
    CategoriaDTO actualizar(Long id, CategoriaRequest request);
    void eliminar(Long id);

    // Utilidades
    Categoria obtenerEntityPorId(Long id);

}
