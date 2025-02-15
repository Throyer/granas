package com.github.granas;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class HomeController {

  @Getter
  @AllArgsConstructor
  static class Quote {
    private String movie;
    private String quote;
  }

  @GetMapping
  @Operation(hidden = true)
  public Quote home() {
    var quotes = List.of(
      new Quote("Taxi Driver", "You talking to me?"),
      new Quote("The Godfather", "I'm going to make him an offer he can't refuse."),
      new Quote("Star Wars", "May the Force be with you."),
      new Quote("Jaws", "You're gonna need a bigger boat"),
      new Quote("Cidade de Deus", "Dadinho é o caralho! meu nome é Zé Pequeno, porra!"),
      new Quote("Scarface", "Say “hello” to my little friend!"),
      new Quote("Dr. No", "Bond. James Bond."),
      new Quote("Terminator 2","Hasta la vista, baby."),
      new Quote("The Sixth Sense", "I see dead people."),
      new Quote("Apollo 13", "Houston, we have a problem."),
      new Quote("O Auto da Compadecida", "Só sei que foi assim.")
    );

    return quotes.get(new Random().nextInt(quotes.size()));
  }
}