package com.github.throyer.granas;

import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.throyer.granas.shared.http.Quote;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/")
public class HomeController {
  @GetMapping
  @Operation(hidden = true)
  public Quote home() {
    var quotes = List.of(
      Quote.from("Taxi Driver", "You talking to me?"),
      Quote.from("The Godfather", "I'm going to make him an offer he can't refuse."),
      Quote.from("Star Wars", "May the Force be with you."),
      Quote.from("Jaws", "You're gonna need a bigger boat"),
      Quote.from("Cidade de Deus", "Dadinho é o caralho! meu nome é Zé Pequeno, porra!"),
      Quote.from("Scarface", "Say “hello” to my little friend!"),
      Quote.from("Dr. No", "Bond. James Bond."),
      Quote.from("Terminator 2","Hasta la vista, baby."),
      Quote.from("The Sixth Sense", "I see dead people."),
      Quote.from("Apollo 13", "Houston, we have a problem."),
      Quote.from("O Auto da Compadecida", "Só sei que foi assim."),
      Quote.from("Fight Club", "The first rule of Fight Club is. You do not talk about Fight Club."),
      Quote.from("Toy Story", "You Are A Toy!")
    );

    return quotes.get(new Random().nextInt(quotes.size()));
  }
}