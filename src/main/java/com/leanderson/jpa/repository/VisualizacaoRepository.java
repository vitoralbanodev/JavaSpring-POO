package com.leanderson.jpa.repository;

import com.leanderson.jpa.Visualizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VisualizacaoRepository extends JpaRepository<Visualizacao, Long> {

    // Usuário que mais assistiu vídeos
    @Query("SELECT v.perfil.usuario.nome, COUNT(v) " +
           "FROM Visualizacao v " +
           "GROUP BY v.perfil.usuario.id, v.perfil.usuario.nome " +
           "ORDER BY COUNT(v) DESC")
    List<Object[]> findUsuarioQueMaisAssistiu();
}
