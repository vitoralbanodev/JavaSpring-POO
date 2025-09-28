package com.streaming.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.streaming.jpa.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    // Buscar vídeos pelo título (contendo texto), com ordenação por título
    List<Video> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    // Todos os vídeos de uma categoria (pelo nome da categoria), ordenado pelo título
    List<Video> findByCategoriaNomeIgnoreCaseOrderByTituloAsc(String categoriaNome);

    // Top 10 vídeos mais bem avaliados (média das notas) - native query com LIMIT
    @Query(value = "SELECT v.* FROM video v JOIN avaliacao a ON a.video_id = v.id " +
                   "GROUP BY v.id ORDER BY AVG(a.nota) DESC LIMIT 10",
           nativeQuery = true)
    List<Video> findTop10ByAverageRating();

    // Top 10 vídeos mais assistidos (contagem de visualizacoes)
    @Query(value = "SELECT v.* FROM video v JOIN visualizacao vis ON vis.video_id = v.id " +
                   "GROUP BY v.id ORDER BY COUNT(vis.id) DESC LIMIT 10",
           nativeQuery = true)
    List<Video> findTop10MostWatched();
}
