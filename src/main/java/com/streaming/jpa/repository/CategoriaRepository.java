package com.streaming.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streaming.jpa.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNomeIgnoreCase(String nome);
}
