package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {
    public static void main(String[] args) throws EmptyDictionaryException, IOException, GuessAlreadyMadeException {
        String fileName = args[0];
        File fileInput = new File(fileName);
        int inputWord = Integer.parseInt(args[1]);
        EvilHangmanGame game = new EvilHangmanGame();
        game.startGame(fileInput, inputWord);
        int guesses = Integer.parseInt(args[2]);

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
            String inputGuess = userGuess.next();
             char letterGuess = inputGuess.charAt(0);
            // TODO: Implement better checkers here so that the thrown exceptions are better handled
            // maybe a while loop around the whole thing that calls separate functions and doesn't exit the while loop
            // until they are all satisfied
            if (inputGuess.length() > 1 || (inputGuess.toLowerCase().charAt(0) < 97 || inputGuess.toLowerCase().charAt(0) > 122) || (game.getGuessedLetters().contains(letterGuess))) {
                while (inputGuess.length() > 1 || (inputGuess.toLowerCase().charAt(0) < 97 || inputGuess.toLowerCase().charAt(0) > 122) || (game.getGuessedLetters().contains(letterGuess))) {
                    System.out.print("Invalid input/Guess already made! Enter guess: ");
                    inputGuess = userGuess.next();
                    letterGuess = inputGuess.charAt(0); 
                }
            }
            int wordBefore = 0;
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
            int gameOver = 0;
            for (int i = 0; i < game.getCurrentLetters().length(); ++i) {
                if (game.getCurrentLetters().charAt(i) == '-') {
                    ++gameOver;
                }
            }
            if (gameOver == 0) {
                break;
            }
        }
        if (game.dashesLeft() > 0) {
            System.out.println("Sorry, you lost!");
            System.out.println("The word was: " + game.getFinalWord());
        }
        else {
            System.out.print("You win! You guessed the word: " + game.getFinalWord());
        }
    }

}
