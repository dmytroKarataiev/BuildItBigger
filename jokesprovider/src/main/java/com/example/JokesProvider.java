package com.example;

import java.util.Random;

public class JokesProvider {

    public final static String JOKE = "joke";

    Random random;

    String[] jokes = {
            "First Joke",
            "Second Joke",
            "Third Joke",
            "I'm not a funny guy",
            "Lame joke",
            "Not a joke"};

    public JokesProvider() {
        random = new Random();
    }

    public String getJoke() {
        int jokeNumber = random.nextInt(jokes.length);
        return jokes[jokeNumber];
    }
}
