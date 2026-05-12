package ni.com.psychocatalog.repository;

import ni.com.psychocatalog.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Métodos por convención de nombres (Query Methods)
    boolean existsByNombreIgnoreCase(String nombre);
    Optional<Categoria> findByNombreIgnoreCase(String nombre);
    List<Categoria> findByNombreContainingIgnoreCase(String termino);

    // Query personalizado con JPQL
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombre) LIKE  LOWER(CONCAT('%', :term, '%')) ORDER BY c.nombre ASC")
    List<Categoria> buscarPorTermino(@Param("term") String termino);

    // Query nativo si se necesita algo específico
    @Query(value = """
        SELECT c.* FROM categorias c WHERE c.nombre ILIKE CONCAT('%', :term, '%')
        ORDER BY c.nombre ASC 
        """, nativeQuery = true)
    List<Categoria> buscarPorTerminoNativo(@Param("term") String termino);
}
