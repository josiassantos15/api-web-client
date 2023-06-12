package com.josias.apiwebclient.service;

import com.josias.apiwebclient.response.CharacterResponse;
import com.josias.apiwebclient.response.EpisodeResponse;
import com.josias.apiwebclient.response.ListOfEpisodesResponse;
import com.josias.apiwebclient.response.LocationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class RickAndMortyClient {

    private final WebClient webClient;
    private final String rickAndMortyApiUrl;

    public RickAndMortyClient(WebClient.Builder builder, @Value("${rickandmortyapi.url}") String rickAndMortyApiUrl) {
        this.webClient = builder.baseUrl(rickAndMortyApiUrl).build();
        this.rickAndMortyApiUrl = rickAndMortyApiUrl;
    }

    public Mono<CharacterResponse> findACharacterById(String id) {
        log.info("Buscando personagem com o id [{}]", id);
        return webClient
                .get()
                .uri("/character/" + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CharacterResponse.class);
    }

    public Mono<LocationResponse> findALocationById(String id) {
        log.info("Buscando localizacao com o id [{}]", id);
        return webClient
                .get()
                .uri("/location/" + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LocationResponse.class);
    }

    public Mono<EpisodeResponse> findAEpisodeById(String id) {
        log.info("Buscando episodio com o id [{}]", id);
        return webClient
                .get()
                .uri("/episode/" + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(EpisodeResponse.class);
    }

    public Flux<ListOfEpisodesResponse> ListAllEpisodes() {
        log.info("Listando todos os episodios cadastrados");
        return webClient
                .get()
                .uri("/episode")
                .accept(APPLICATION_JSON)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        error -> Mono.error(new RuntimeException("verifique os parâmetros informados")))
                .bodyToFlux(ListOfEpisodesResponse.class);
    }

}
