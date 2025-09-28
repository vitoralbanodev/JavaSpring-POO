package com.streaming.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.streaming.jpa.entity.Avaliacao;
import com.streaming.jpa.entity.Categoria;
import com.streaming.jpa.entity.Perfil;
import com.streaming.jpa.entity.Usuario;
import com.streaming.jpa.entity.Video;
import com.streaming.jpa.entity.Visualizacao;
import com.streaming.jpa.repository.AvaliacaoRepository;
import com.streaming.jpa.repository.CategoriaRepository;
import com.streaming.jpa.repository.PerfilRepository;
import com.streaming.jpa.repository.UsuarioRepository;
import com.streaming.jpa.repository.VideoRepository;
import com.streaming.jpa.repository.VisualizacaoRepository;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private UsuarioRepository usuarioRepo;
    private PerfilRepository perfilRepo;
    private CategoriaRepository categoriaRepo;
    private VideoRepository videoRepo;
    private VisualizacaoRepository visualRepo;
    private AvaliacaoRepository avalRepo;

    @Bean
    CommandLineRunner initData(
            UsuarioRepository usuarioRepo,
            PerfilRepository perfilRepo, 
            CategoriaRepository categoriaRepo,
            VideoRepository videoRepo,
            VisualizacaoRepository visualRepo,
            AvaliacaoRepository avalRepo
    ) {
        return args -> {
            this.usuarioRepo = usuarioRepo;
            this.perfilRepo = perfilRepo;
            this.categoriaRepo = categoriaRepo;
            this.videoRepo = videoRepo;
            this.visualRepo = visualRepo;
            this.avalRepo = avalRepo;

            if (usuarioRepo.count() == 0) {
                insertDataValues();
            }

            List<Video> missao = videoRepo.findByTituloContainingIgnoreCaseOrderByTituloAsc("Missão");
            System.out.println("=== Buscar vídeos pelo título com ordenação (ex: 'Missão') ===");
            missao.forEach(v -> System.out.println(v.getTitulo()));

            List<Video> catMissao = videoRepo.findByCategoriaNomeIgnoreCaseOrderByTituloAsc("Missão");
            System.out.println("\n=== Todos os vídeos de uma categoria ordenados pelo título (categoria 'Missão') ===");
            catMissao.forEach(v -> System.out.println(v.getTitulo()));

            List<Video> topRated = videoRepo.findTop10ByAverageRating();
            System.out.println("\n=== Top 10 vídeos mais bem avaliados ===");
            topRated.forEach(v -> System.out.println(v.getTitulo()));

            List<Video> topWatched = videoRepo.findTop10MostWatched();
            System.out.println("\n=== Top 10 vídeos mais assistidos ===");
            topWatched.forEach(v -> System.out.println(v.getTitulo()));

            Usuario topUsuario = usuarioRepo.findUserWhoWatchedMost();
            System.out.println("\n=== Usuário que mais assistiu vídeos ===");
            System.out.println(topUsuario != null ? topUsuario.getNome() : "Nenhum");
        };
    }

    private void insertDataValues()
    {
        Usuario u1 = new Usuario("Lucas", "lucas@example.com", "senha", LocalDateTime.now());
        Usuario u2 = new Usuario("Mariana", "mari@example.com", "senha2", LocalDateTime.now());
        usuarioRepo.save(u1);
        usuarioRepo.save(u2);
    
        Perfil p1 = new Perfil("Lucas - Principal");
        p1.setUsuario(u1);
        Perfil p2 = new Perfil("Mariana - Principal");
        p2.setUsuario(u2);
        perfilRepo.save(p1); perfilRepo.save(p2);
    
        Categoria cat1 = new Categoria("Ação");
        Categoria cat2 = new Categoria("Drama");
        Categoria cat3 = new Categoria("Missão");
        categoriaRepo.save(cat1); categoriaRepo.save(cat2); categoriaRepo.save(cat3);
    
        Video v1 = new Video("Missão Impossível", "ação e espionagem", 7200, cat1);
        Video v2 = new Video("Missão Secreta", "thriller", 5400, cat3);
        Video v3 = new Video("Drama Intenso", "história emocionante", 6000, cat2);
        Video v4 = new Video("Missão Final", "conclusão", 4000, cat3);
        Video v5 = new Video("Outra Aventura", "aventura", 3600, cat1);
        videoRepo.saveAll(List.of(v1,v2,v3,v4,v5));
    
        visualRepo.save(new Visualizacao(p1, v1, LocalDateTime.now().minusDays(3), 100));
        visualRepo.save(new Visualizacao(p1, v2, LocalDateTime.now().minusDays(2), 50));
        visualRepo.save(new Visualizacao(p2, v1, LocalDateTime.now().minusDays(1), 100));
        visualRepo.save(new Visualizacao(p1, v1, LocalDateTime.now(), 80));
        visualRepo.save(new Visualizacao(p2, v4, LocalDateTime.now(), 100));
        visualRepo.save(new Visualizacao(p2, v2, LocalDateTime.now(), 100));
        visualRepo.save(new Visualizacao(p2, v2, LocalDateTime.now().minusHours(5), 30));
        visualRepo.save(new Visualizacao(p1, v3, LocalDateTime.now().minusDays(4), 100));
        visualRepo.save(new Visualizacao(p1, v5, LocalDateTime.now().minusDays(4), 30));
    
        for(int i=0;i<20;i++) visualRepo.save(new Visualizacao(p1, v1, LocalDateTime.now().minusHours(i), 100));
    
        avalRepo.save(new Avaliacao(p1, v1, 5, "Excelente"));
        avalRepo.save(new Avaliacao(p2, v1, 4, "Bom"));
        avalRepo.save(new Avaliacao(p1, v2, 5, "Adorei"));
        avalRepo.save(new Avaliacao(p2, v2, 5, "Ótimo"));
        avalRepo.save(new Avaliacao(p1, v3, 3, "Ok"));
        avalRepo.save(new Avaliacao(p2, v4, 4, "Interessante"));
    
        videoRepo.flush();
        usuarioRepo.flush();
    }
}
