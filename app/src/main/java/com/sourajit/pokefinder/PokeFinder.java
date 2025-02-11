package com.sourajit.pokefinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeFinder extends AppCompatActivity {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private EditText pokemonName;
    private Button btnSearch;
    private ImageView pokemonImage;
    private TextView pokemonDetails;
    private ProgressBar progressBar;
    private PokeApiService pokeApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokefinder);

        pokemonName = findViewById(R.id.pokemonName);
        btnSearch = findViewById(R.id.btnSearch);
        pokemonImage = findViewById(R.id.pokemonImage);
        pokemonDetails = findViewById(R.id.pokemonDetails);
        progressBar = findViewById(R.id.progressBar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeApiService = retrofit.create(PokeApiService.class);

        btnSearch.setOnClickListener(v -> fetchPokemonData());
    }

    private void fetchPokemonData() {
        String name = pokemonName.getText().toString().trim().toLowerCase();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter a Pokémon name!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        pokemonImage.setVisibility(View.GONE);

        Call<PokemonResponse> call = pokeApiService.getPokemon(name);
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    PokemonResponse pokemon = response.body();

                    pokemonDetails.setText("ID: " + pokemon.getId() + "\nName: " + pokemon.getName());

                    Glide.with(PokeFinder.this)
                            .load(pokemon.getSprites().getFrontDefault())
                            .into(pokemonImage);

                    pokemonImage.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(PokeFinder.this, "Pokémon not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PokeFinder.this, "Error fetching data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
