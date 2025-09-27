package com.leanderson.jpa.repository;

import com.leanderson.jpa.Video;
import com.leanderson.jpa.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    // Buscar vídeos pelo título (ordenados por título)
    List<Video> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    // Todos os vídeos de uma categoria ordenados pelo título
    List<Video> findByCategoriaOrderByTituloAsc(Categoria categoria);

    // Top 10 vídeos mais bem avaliados (média de nota)
    @Query("SELECT v FROM Video v JOIN v.avaliacoes a GROUP BY v ORDER BY AVG(a.nota) DESC")
    List<Video> findTop10ByMelhorAvaliados();

    // Top 10 vídeos mais assistidos
    @Query("SELECT v FROM Video v JOIN v.visualizacoes vis GROUP BY v ORDER BY COUNT(vis) DESC")
    List<Video> findTop10ByMaisAssistidos();
}
