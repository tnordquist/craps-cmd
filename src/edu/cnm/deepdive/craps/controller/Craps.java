package edu.cnm.deepdive.craps.controller;

import edu.cnm.deepdive.craps.model.Game;
import edu.cnm.deepdive.craps.model.State;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.rng.simple.JDKRandomBridge;
import org.apache.commons.rng.simple.RandomSource;

public class Craps {

  public static final int SEED_SIZE = 312;
  private Random rng;
  private Game game;

  public static void main(String[] args) {
    Craps craps = new Craps();

    if (args.length > 0) {
      int numTrials = Integer.parseInt(args[0]);
      for (int i = 0; i < numTrials; ++i) {
        craps.play(false);
      }
      craps.displayTally();
    } else {
      try (Scanner scanner = new Scanner(System.in)) {

        do {
          craps.play(true);

        } while (craps.playAgain(scanner));
      }

    }
  }

  public Craps() {
    rng = new JDKRandomBridge(RandomSource.MT_64,
        RandomSource.createLongArray(SEED_SIZE)); //  represents a 19968 long speed.
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
      displayTally();
    }
  }

  private void displayTally() {
    long wins = game.getWins();
    long total = wins + game.getLosses();
    double percentage = 100.0 * wins / total;
    System.out.printf("Winning percentage = (%d/%d)= %.2f%%", wins, total,
        percentage);
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
