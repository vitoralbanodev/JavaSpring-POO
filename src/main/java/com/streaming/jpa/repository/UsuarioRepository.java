package com.streaming.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.streaming.jpa.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O usuário que mais assistiu vídeos (soma de visualizacoes across profiles)
    @Query(value = "SELECT u.* FROM usuario u " +
            "JOIN perfil p ON p.usuario_id = u.id " +
            "JOIN visualizacao vis ON vis.perfil_id = p.id " +
            "GROUP BY u.id ORDER BY COUNT(vis.id) DESC LIMIT 1",
           nativeQuery = true)
    Usuario findUserWhoWatchedMost();
}
