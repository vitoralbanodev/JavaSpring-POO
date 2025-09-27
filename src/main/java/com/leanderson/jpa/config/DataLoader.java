package com.leanderson.jpa.config;

import com.leanderson.jpa.*;
import com.leanderson.jpa.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final CategoriaRepository categoriaRepo;
    private final VideoRepository videoRepo;
    private final PerfilRepository perfilRepo;

    public DataLoader(UsuarioRepository usuarioRepo,
                      CategoriaRepository categoriaRepo,
                      VideoRepository videoRepo,
                      PerfilRepository perfilRepo) {
        this.usuarioRepo = usuarioRepo;
        this.categoriaRepo = categoriaRepo;
        this.videoRepo = videoRepo;
        this.perfilRepo = perfilRepo;
    }

    @Override
    public void run(String... args) {
        // Criar usuário
        Usuario u1 = new Usuario();
        u1.setNome("João Silva");
        u1.setEmail("joao@email.com");
        u1.setSenha("123456");
        u1.setDataCadastro(LocalDateTime.now());
        usuarioRepo.save(u1);

        // Criar categoria
        Categoria c1 = new Categoria();
        c1.setNome("Ação");
        categoriaRepo.save(c1);

        // Criar vídeo
        Video v1 = new Video();
        v1.setTitulo("Missão Impossível");
        v1.setDescricao("Filme de espionagem com Tom Cruise");
        v1.setDuracao(120);
        v1.setCategoria(c1);
        videoRepo.save(v1);

        // Criar perfil
        Perfil p1 = new Perfil();
        p1.setNomePerfil("Perfil João");
        p1.setUsuario(u1);
        perfilRepo.save(p1);

        System.out.println("✅ Dados iniciais inseridos com sucesso!");
    }
}
