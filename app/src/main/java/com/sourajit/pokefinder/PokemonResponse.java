package com.sourajit.pokefinder;

public class PokemonResponse {
    private int id;
    private String name;
    private Sprites sprites;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public static class Sprites {
        private String front_default;

        public String getFrontDefault() {
            return front_default;
        }
    }
}
