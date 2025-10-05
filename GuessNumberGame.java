
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;

// Class that represents a single game session
class Game {
    private int secretNumber;    // The random number to guess
    private int maxAttempts;     // Maximum attempts allowed
    private int attemptsMade;    // Attempts made so far
    private boolean won;         // Whether the player has won

    // Constructor to initialize a new game
    public Game(int maxAttempts) {
        Random random = new Random();
        this.secretNumber = random.nextInt(100) + 1; // Random number between 1 and 100
        this.maxAttempts = maxAttempts;
        this.attemptsMade = 0;
        this.won = false;
    }

    // Processes a player's guess and returns true if correct
    public boolean playAttempt(int guess) {
        attemptsMade++;
        if (guess == secretNumber) {
            won = true;
            return true;
        }
        return false;
    }

    // Returns true if the player has won
    public boolean isWon() {
        return won;
    }

    // Returns the number of attempts made
    public int getAttemptsMade() {
        return attemptsMade;
    }

    // Returns the maximum number of attempts allowed
    public int getMaxAttempts() {
        return maxAttempts;
    }

    // Returns the secret number (used after the game ends)
    public int getSecretNumber() {
        return secretNumber;
    }
}

// The Record class and its methods for tracking game statistics and exporting results
// were generated with the assistance of AI to demonstrate handling collections and file I/O operations.
class Record {
    private ArrayList<Game> games;
    private HashMap<String, Integer> stats;

    public Record() {
        games = new ArrayList<>();
        stats = new HashMap<>();
        stats.put("played", 0);
        stats.put("won", 0);
        stats.put("lost", 0);
        stats.put("totalAttemptsWon", 0);
    }

    public void addGame(Game game) {
        games.add(game);
        stats.put("played", stats.get("played") + 1);
        if (game.isWon()) {
            stats.put("won", stats.get("won") + 1);
            stats.put("totalAttemptsWon", stats.get("totalAttemptsWon") + game.getAttemptsMade());
        } else {
            stats.put("lost", stats.get("lost") + 1);
        }
    }

    public void showStats() {
        System.out.println("\n=== Game Statistics ===");
        System.out.println("Games played: " + stats.get("played"));
        System.out.println("Games won: " + stats.get("won"));
        System.out.println("Games lost: " + stats.get("lost"));
        if (stats.get("won") > 0) {
            double average = (double) stats.get("totalAttemptsWon") / stats.get("won");
            System.out.println("Average attempts per won game: " + String.format("%.2f", average));
        }
    }

    public void exportStats(String fileName) {
        // AI-generated method to safely export game statistics to a text file
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("=== Game Statistics ===\n");
            writer.write("Games played: " + stats.get("played") + "\n");
            writer.write("Games won: " + stats.get("won") + "\n");
            writer.write("Games lost: " + stats.get("lost") + "\n");
            if (stats.get("won") > 0) {
                double average = (double) stats.get("totalAttemptsWon") / stats.get("won");
                writer.write("Average attempts per won game: " + String.format("%.2f", average) + "\n");
            }
            writer.close();
            System.out.println("Statistics saved in '" + fileName + "'.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

// Main class that runs the number guessing game
public class GuessNumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Record record = new Record(); // Create record to track all results
        boolean playAgain = true;

        System.out.println("Welcome to the Number Guessing Game!");

        while (playAgain) {
            // Start a new game with a maximum of 7 attempts
            Game game = new Game(7);
            System.out.println("\nNew game: I have chosen a number between 1 and 100. You have 7 attempts.");

            // Play the current game until won or attempts exhausted
            while (!game.isWon() && game.getAttemptsMade() < game.getMaxAttempts()) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();

                if (game.playAttempt(guess)) {
                    System.out.println("Congratulations! You guessed the number in " + game.getAttemptsMade() + " attempts!");
                } else if (guess < game.getSecretNumber()) {
                    System.out.println("The number is higher. You have " + (game.getMaxAttempts() - game.getAttemptsMade()) + " attempts left.");
                } else {
                    System.out.println("The number is lower. You have " + (game.getMaxAttempts() - game.getAttemptsMade()) + " attempts left.");
                }
            }

            // Reveal the correct number if the player didn’t win
            if (!game.isWon()) {
                System.out.println("Out of attempts! The correct number was: " + game.getSecretNumber());
            }

            // Update the record with the finished game
            record.addGame(game);

            // Ask the player if they want to play again
            System.out.print("\nDo you want to play again? (y/n): ");
            String answer = scanner.next();
            playAgain = answer.equalsIgnoreCase("y");
        }

        // Display overall statistics and export them to a file
        record.showStats();
        record.exportStats("game_record.txt");
        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
