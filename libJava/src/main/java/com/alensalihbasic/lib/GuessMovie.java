package com.alensalihbasic.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GuessMovie {

    //Scan movies.txt and pick random movie title
    private String getRandomMovie() {
        Scanner movieScanner = null;
        ArrayList<String> movieList = new ArrayList<String>();

        try {
            movieScanner = new Scanner(new File("movies.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (movieScanner.hasNextLine()) {
            String movie = movieScanner.nextLine();
            movieList.add(movie);
        }
        Random random = new Random();
        String randomMovie = movieList.get(random.nextInt(movieList.size()));

        return randomMovie;
    }

    //Game logic
    private void Game() {

        ArrayList<String> wrongLetters = new ArrayList<>();
        boolean isGuessed = false;
        int count = 10;

        //Get random movie title and hide all letters with asterisk(*)
        String randomMovie = getRandomMovie();
        String hideRandomMovie = randomMovie.replaceAll("[a-z]", "*");
        System.out.println("The movie for you to guess is: " + hideRandomMovie);

        Scanner input = new Scanner(System.in);

        while (count != 0) {
            System.out.println("Choose your letter: ");
            String guess = input.nextLine();
            int indexChar = randomMovie.indexOf(guess);


            //Prepare for if-else statements :)
            if (!guess.matches("[a-z]{1}")) { //Check if input letter is one lowercase letter
                System.out.println("One lowercase letter only. You sneaky one, you had to try it ha? :D");
            } else {
                //What to do if input letter doesn't match
                if (indexChar < 0) {
                    //Print when list of wrong letters isn't empty and contains input letter
                    if (!wrongLetters.isEmpty() && wrongLetters.contains(guess)) {
                        System.out.println("You already tried that letter. It's WRONG!");
                    } else {
                        System.out.println("UGH! CLOSE!");
                        wrongLetters.add(guess); //Add wrong letter to the list
                        count--;
                        // Break the loop if count == 0
                        if (count == 0) {
                            break;
                        }
                    }
                } else {
                    //What to do if input letter does match
                    if (hideRandomMovie.contains(guess)) {
                        //Print when input letter is already guessed
                        System.out.println("You already tried that letter. It's CORRECT!");
                    } else {
                        //What to do when input letter is correct and not guessed
                        System.out.println("NICE!");
                        //Loop for checking all characters in a movie title
                        while (indexChar >= 0) {
                            char[] hideMovieChars = hideRandomMovie.toCharArray();
                            hideMovieChars[indexChar] = guess.charAt(0); //Replace input letter at correct position
                            hideRandomMovie = String.valueOf(hideMovieChars);
                            indexChar = randomMovie.indexOf(guess, indexChar + 1); //Check if there is multiple correct guesses
                        }
                    }
                }
            }

            //If all letters are uncovered, break the loop
            if (hideRandomMovie.contains(randomMovie)) {
                isGuessed = true;
                break;
            }

            System.out.println(hideRandomMovie);
            System.out.println("You have " + count + " remaining live(s). Letter(s) that aren't in the movie: " + wrongLetters);
        }

        if (isGuessed) {
            System.out.println("YOU WIN! You've guessed '" + randomMovie + "' correctly.");
        } else {
            System.out.println("GAME OVER! You've failed to guess '" + randomMovie + "' correctly.");
        }
    }

    public static void main(String[] args) {

        System.out.println("Welcome to GuessMovie!");
        System.out.println("Guess the letters, guess the movie...You've 10 lives :)");
        System.out.println("IMPORTANT: Only one lowercase letter per turn!");

        GuessMovie game = new GuessMovie();
        game.Game();
    }

}
