package com.sourajit.pokefinder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeApiService {
    @GET("pokemon/{name}")
    Call<PokemonResponse> getPokemon(@Path("name") String name);
}
