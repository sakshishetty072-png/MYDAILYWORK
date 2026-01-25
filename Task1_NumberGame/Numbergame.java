import java.util.Random;
import java.util.Scanner;

public class Numbergame {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int score = 0;
        char playAgain;

        do {
            int number = rand.nextInt(100) + 1; // 1 to 100
            int attempts = 5;
            boolean guessed = false;

            System.out.println("\nGuess a number between 1 to 100");
            System.out.println("You have " + attempts + " attempts");

            while (attempts > 0) {
                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();

                if (guess == number) {
                    System.out.println("Correct! You win 🎉");
                    score++;
                    guessed = true;
                    break;
                } else if (guess > number) {
                    System.out.println("Too High!");
                } else {
                    System.out.println("Too Low!");
                }

                attempts--;
                System.out.println("Remaining attempts: " + attempts);
            }

            if (!guessed) {
                System.out.println("You lost! Number was: " + number);
            }

            System.out.print("Play again? (y/n): ");
            playAgain = sc.next().charAt(0);

        } while (playAgain == 'y' || playAgain == 'Y');

        System.out.println("\nFinal Score: " + score);
        sc.close();
    }
}