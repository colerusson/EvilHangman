package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    Set<String> allWords = new HashSet<>();
    SortedSet<Character> guessedLetters = new TreeSet<>();
    Map<String, Set<String>> allSets = new HashMap<>();
    StringBuilder currentLetters = new StringBuilder();
    int lettersAdded;
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        allWords.clear();
        guessedLetters.clear();
        Scanner scan = new Scanner(dictionary);
        while (scan.hasNext()) {
            String word = scan.next();
            if (word.length() == wordLength) {
                allWords.add(word.toLowerCase());
            }
        }
        for (int i = 0; i < wordLength; ++i) {
            currentLetters.setCharAt(i, '-');
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guessedLetters.add(guess);
        for (String word : allWords) {
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < word.length(); ++i) {
                if (word.toLowerCase().charAt(i) == guess) {
                    key.setCharAt(i, guess);
                }
                else {
                    key.setCharAt(i,'-');
                }
            }
            if (allSets.containsKey(key.toString())) {
                allSets.get(key.toString()).add(word.toLowerCase());
            }
            else {
                Set<String> newSet = new HashSet<>();
                allSets.put(key.toString(), newSet);
                allSets.get(key.toString()).add(word.toLowerCase());
            }
        }
        int largestSet = 0;
        String finalKey = "";
        for (String name : allSets.keySet()) {
            if (allSets.get(name).size() > largestSet) {
                largestSet = allSets.get(name).size();
                finalKey = name.toLowerCase();
            }
            if (allSets.get(name).size() == largestSet) {
                // TODO: need implementation here to get the best set
            }
        }
        allWords.clear();
        allWords = allSets.get(finalKey);
        lettersAdded = 0;
        for (int i = 0; i < finalKey.length(); ++i) {
            if (finalKey.charAt(i) != '-' && currentLetters.charAt(i) == '-') {
                currentLetters.setCharAt(i, finalKey.charAt(i));
                ++lettersAdded;
            }
        }

        return allSets.get(finalKey);
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String getCurrentLetters() {
        return currentLetters.toString();
    }

    public int getLettersAdded() {
        return lettersAdded;
    }
}
