package edu.cnm.deepdive.craps.controller;

import edu.cnm.deepdive.craps.model.Game;
import edu.cnm.deepdive.craps.model.State;
import java.util.Random;
import java.util.Scanner;

public class Craps {

  private Random rng;
  private Game game;

  public static void main(String[] args) {
    Craps craps = new Craps();
    try (Scanner scanner = new Scanner(System.in)) {

      do {
        craps.play(true);

      } while (craps.playAgain(scanner));
    }
  }

  public Craps() {
    rng = new Random();
    game = new Game(rng);
  }

  public void play(boolean verbose) {
    game.reset();
    State state = game.play();
    if (verbose) {
      for (int[] diceRoll : game.getRolls()) {
        System.out.printf("%d:%d%n", diceRoll[0], diceRoll[1]);
      }
      System.out.printf("Outcome: %s%n", state);
      long wins = game.getWins();
      long total = wins + game.getLosses();
      double percentage = 100.0 * wins / total;
      System.out.printf("Winning percentage = (%d/%d)= %.2f%%", wins, total,
          percentage);
    }
  }

  private boolean playAgain(Scanner scanner) {
    System.out.print("Play again (y/n)?");
    String input;
    do {
      input = scanner.nextLine().trim().toLowerCase();
    } while (input.isEmpty());
      return input.charAt(0) == 'y';
  }
}
