package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {
    public static void main(String[] args) throws EmptyDictionaryException, IOException, GuessAlreadyMadeException {
        String fileName = args[0];
        File fileInput = new File(fileName);
        String inputWord = args[1];
        EvilHangmanGame game = new EvilHangmanGame();
        game.startGame(fileInput, inputWord.length());
        int guesses = 10;

        while (guesses > 0) {
            System.out.println("You have " + guesses + " guesses left");
            System.out.print("Used letters:");
            for (char c : game.getGuessedLetters()) {
                System.out.print(" ");
                System.out.print(c);
            }
            System.out.print("\n");
            System.out.print("Word: ");
            System.out.println(game.getCurrentLetters());
            System.out.print("Enter guess: ");
            Scanner userGuess = new Scanner(System.in);
            char letterGuess = userGuess.next().charAt(0);
            if (game.getGuessedLetters().contains(letterGuess)) {
                while (game.getGuessedLetters().contains(letterGuess)) {
                    System.out.println("Letter already guessed, try again: ");
                    letterGuess = userGuess.next().charAt(0);
                }
            }
            int wordBefore = game.getLettersAdded();
            game.makeGuess(Character.toLowerCase(letterGuess));
            int wordAfter = game.getLettersAdded();
            if (wordBefore == wordAfter) {
                System.out.println("Sorry, there are no " + letterGuess +"'s");
                --guesses;
            }
            else {
                System.out.println("Yes, there is " + wordAfter + " " + letterGuess);
            }
            System.out.print("\n");
        }
    }

}
