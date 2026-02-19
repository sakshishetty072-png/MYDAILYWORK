import java.util.*;
import java.util.concurrent.*;
public class QuizApplication{
    // Question Class
    static class Question {
        String questionText;
        String[] options;
        int correctAnswer;
        Question(String questionText, String[] options, int correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    // Result Class for Summary
    static class Result {
        int questionNo;
        int userAnswer;
        int correctAnswer;
        String status;

        Result(int questionNo, int userAnswer, int correctAnswer, String status) {
            this.questionNo = questionNo;
            this.userAnswer = userAnswer;
            this.correctAnswer = correctAnswer;
            this.status = status;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Question> quiz = new ArrayList<>();

        // Adding Questions
        quiz.add(new Question(
                "Which language is platform independent?",
                new String[]{"1. Python", "2. Java", "3. C#", "4. Ruby"},
                2
        ));

        quiz.add(new Question(
                "What is 5 + 3?",
                new String[]{"1. 5", "2. 8", "3. 10", "4. 12"},
                2
        ));

        quiz.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"1. implement", "2. extends", "3. inherit", "4. super"},
                2
        ));

        int score = 0;
        List<Result> summary = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < quiz.size(); i++) {

            Question q = quiz.get(i);

            System.out.println("\n----------------------------------");
            System.out.println("Q" + (i + 1) + ": " + q.questionText);

            for (String option : q.options) {
                System.out.println(option);
            }

            Future<Integer> future = executor.submit(() -> {
                System.out.print("Enter your answer (1-4): ");
                return sc.nextInt();
            });

            int timeLeft = 10;
            Integer userAnswer = null;
            boolean answered = false;

            while (timeLeft > 0) {

                if (future.isDone()) {
                    answered = true;
                    break;
                }

                System.out.println("\nTime left: " + timeLeft + "s");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                timeLeft--;
            }

            if (answered) {
                try {
                    userAnswer = future.get();
                    System.out.println("Your Answer: " + userAnswer);

                    if (userAnswer == q.correctAnswer) {
                        System.out.println("Correct ");
                        score++;
                        summary.add(new Result(i + 1, userAnswer, q.correctAnswer, "Correct"));
                    } else {
                        System.out.println("Wrong ");
                        summary.add(new Result(i + 1, userAnswer, q.correctAnswer, "Wrong"));
                    }

                } catch (Exception e) {
                    System.out.println("Invalid Input!");
                    summary.add(new Result(i + 1, 0, q.correctAnswer, "Invalid"));
                }

            } else {
                System.out.println("Time's Up!!!");
                summary.add(new Result(i + 1, 0, q.correctAnswer, "Time Up"));
                future.cancel(true);
            }
        }

        executor.shutdown();
        sc.close();

        // RESULT SCREEN
        System.out.println("\n==================================");
        System.out.println("           QUIZ RESULT            ");
        System.out.println("==================================");
        System.out.println("Final Score: " + score + "/" + quiz.size());

        double percentage = (score * 100.0) / quiz.size();
        System.out.printf("Percentage  : %.2f%%\n", percentage);

        System.out.println("\nDETAILED SUMMARY");
        System.out.println("----------------------------------");

        for (Result r : summary) {

            System.out.println("Question " + r.questionNo);

            if (r.status.equals("Time Up") || r.status.equals("Invalid")) {
                System.out.println("Your Answer   : Not Answered");
            } else {
                System.out.println("Your Answer   : " + r.userAnswer);
            }

            System.out.println("Correct Answer: " + r.correctAnswer);
            System.out.println("Status        : " + r.status);

            if (r.status.equals("Correct")) {
                System.out.println("Marks Earned  : 1");
            } else {
                System.out.println("Marks Earned  : 0");
            }

            System.out.println("----------------------------------");
        }

        // Performance Message
        System.out.println("Overall Performance:");

        if (percentage >= 80) {
            System.out.println("Excellent Performance ");
        } else if (percentage >= 50) {
            System.out.println("Good Job ");
        } else {
            System.out.println("Needs Improvement ");
        }

        System.out.println("==================================");
    }
}
