package ni.com.psychocatalog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ni.com.psychocatalog.model.Categoria;
import ni.com.psychocatalog.model.dto.CategoriaDTO;
import ni.com.psychocatalog.model.dto.CategoriaRequest;
import ni.com.psychocatalog.repository.CategoriaRepository;
import ni.com.psychocatalog.service.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Page<CategoriaDTO> listarPaginado(Pageable pageable) {
        log.debug("Listando categorías paginadas: {}", pageable);
        return categoriaRepository.findAll(pageable)
                .map(CategoriaDTO::fromEntity);
    }

    @Override
    public List<CategoriaDTO> listarTodos() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDTO::fromEntity)
                .toList();
    }

    @Override
    public CategoriaDTO obtenerPorId(Long id) {
        log.debug("Buscando categoría con id: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Categoria", "id", id));
        return CategoriaDTO.fromEntity(categoria);
    }

    @Override
    public List<CategoriaDTO> buscarPorNombre(String termino) {
        if(termino == null || termino.isBlank()) return Collections.emptyList();
        return categoriaRepository.buscarPorTermino(termino)
                .stream()
                .map(CategoriaDTO::fromEntity)
                .toList();
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return categoriaRepository.existsByNombreIgnoreCase(nombre);
    }

    @Override
    @Transactional
    public CategoriaDTO crear(CategoriaRequest request) {
        log.info("Creando nueva categoría: {}", request.getNombre());
        if(existePorNombre(request.getNombre())){
            throw new IllegalStateException("Ya existe una categoría con ese nombre");
        }

        Categoria nueva = Categoria.builder()
                .nombre(request.getNombre().trim())
                .descripcion(request.getDescripcion() != null ? request.getDescripcion().trim() : null)
                .build();

        Categoria guardada = categoriaRepository.save(nueva);
        log.info("Categoria creada con id: {}", guardada.getId());
        return CategoriaDTO.fromEntity(guardada);
    }

    @Override
    @Transactional
    public CategoriaDTO actualizar(Long id, CategoriaRequest request) {
        log.info("Actualizando categoría id: {}", id);
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Categoría", "id", id));

        // Validad unicidad solo si cambia el nombre
        if(!existente.getNombre().equalsIgnoreCase(request.getNombre()) &&
          existePorNombre(request.getNombre())){
            throw new IllegalStateException("Ya existe una categoría con ese nombre");
        }

        existente.setNombre(request.getNombre().trim());
        existente.setDescripcion(request.getDescripcion() != null ? request.getDescripcion().trim() : null);

        Categoria actualizada = categoriaRepository.save(existente);
        log.info("Categoría actualizada: {}", id);
        return CategoriaDTO.fromEntity(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        log.warn("Eliminando categoria id: {}", id);
        if(!categoriaRepository.existsById(id)){
            throw new ResourceNotFoundException("Categoría", "id", id);
        }

        categoriaRepository.deleteById(id);
        log.info("Categoria eliminada: {}", id);
    }

    @Override
    public Categoria obtenerEntityPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Categoría", "id", id));
    }
}
