import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * KatakanaQuizApp is a console-based quiz application that tests the user's knowledge of Katakana characters.
 * The quiz consists of 10 questions, each asking the user to identify the correct Katakana character for a given romaji.
 * If the user answers incorrectly, the correct answer is marked with a '*'.
 * At the end of the quiz, the user is prompted to restart the quiz or exit the application.
 */
public class KatakanaQuizApp {
    private static final Map<String, String> katakana = new HashMap<>();
    private static final Random random = new Random();
    private int score = 0;
    private int questionCount = 0;
    private String correctAnswer;
    private boolean quizFinished = false;
    private final Scanner scanner = new Scanner(System.in);

    // Static block to initialize the Katakana characters and their romaji
    static {
        katakana.put("ア", "a");
        katakana.put("イ", "i");
        katakana.put("ウ", "u");
        katakana.put("エ", "e");
        katakana.put("オ", "o");
        katakana.put("カ", "ka");
        katakana.put("キ", "ki");
        katakana.put("ク", "ku");
        katakana.put("ケ", "ke");
        katakana.put("コ", "ko");
        katakana.put("サ", "sa");
        katakana.put("シ", "shi");
        katakana.put("ス", "su");
        katakana.put("セ", "se");
        katakana.put("ソ", "so");
        katakana.put("タ", "ta");
        katakana.put("チ", "chi");
        katakana.put("ツ", "tsu");
        katakana.put("テ", "te");
        katakana.put("ト", "to");
        katakana.put("ナ", "na");
        katakana.put("ニ", "ni");
        katakana.put("ヌ", "nu");
        katakana.put("ネ", "ne");
        katakana.put("ノ", "no");
        katakana.put("ハ", "ha");
        katakana.put("ヒ", "hi");
        katakana.put("フ", "fu");
        katakana.put("ヘ", "he");
        katakana.put("ホ", "ho");
        katakana.put("マ", "ma");
        katakana.put("ミ", "mi");
        katakana.put("ム", "mu");
        katakana.put("メ", "me");
        katakana.put("モ", "mo");
        katakana.put("ヤ", "ya");
        katakana.put("ユ", "yu");
        katakana.put("ヨ", "yo");
        katakana.put("ラ", "ra");
        katakana.put("リ", "ri");
        katakana.put("ル", "ru");
        katakana.put("レ", "re");
        katakana.put("ロ", "ro");
        katakana.put("ワ", "wa");
        katakana.put("ヲ", "wo");
        katakana.put("ン", "n");
    }

    // Method to start the quiz
    public void start() {
        while (!quizFinished) {
            nextQuestion();
        }
        System.out.println("Quiz Completed! Your final score is " + score + "/10");
        promptRestart();
    }

    // Method to display the next question
    private void nextQuestion() {
        if (questionCount < 10) {
            String romaji = getRandomRomaji();
            correctAnswer = getKeyByValue(romaji);
            ArrayList<String> options = generateOptions(correctAnswer);

            System.out.println("\nWhat is the correct Katakana character for the romaji '" + romaji + "'?");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            int userAnswer = getUserInput(options.size());

            if (options.get(userAnswer - 1).equals(correctAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was '" + correctAnswer + "'");
                markCorrectAnswer(options);
            }

            displayScore();
            questionCount++;
        } else {
            quizFinished = true;
        }
    }

    // Method to get and validate user input
    private int getUserInput(int numOptions) {
        int userAnswer = -1;
        while (userAnswer < 1 || userAnswer > numOptions) {
            System.out.print("Enter the number of your choice: ");
            try {
                userAnswer = Integer.parseInt(scanner.nextLine().trim());
                if (userAnswer < 1 || userAnswer > numOptions) {
                    System.out.println("Invalid choice. Please select a number between 1 and " + numOptions + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return userAnswer;
    }

    // Method to display the current score
    private void displayScore() {
        System.out.println("Score: " + score + "/10");
    }

    // Method to reset the quiz
    private void resetQuiz() {
        score = 0;
        questionCount = 0;
        quizFinished = false;
    }

    // Method to prompt the user to restart the quiz
    private void promptRestart() {
        System.out.print("Do you want to restart the quiz? (yes/no): ");
        String restart = scanner.nextLine().trim().toLowerCase();
        if (restart.equals("yes")) {
            resetQuiz();
            start();
        } else {
            System.out.println("Thank you for playing!");
        }
    }

    // Method to mark the correct answer in the options
    private void markCorrectAnswer(ArrayList<String> options) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(correctAnswer)) {
                System.out.println((i + 1) + ". " + options.get(i) + " *");
            }
        }
    }

    // Method to get a random romaji
    private static String getRandomRomaji() {
        ArrayList<String> values = new ArrayList<>(katakana.values());
        return values.get(random.nextInt(values.size()));
    }

    // Method to get the key (Katakana) for a given value (romaji)
    private static String getKeyByValue(String value) {
        for (Map.Entry<String, String> entry : KatakanaQuizApp.katakana.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    // Method to generate options including the correct answer and three incorrect answers
    private static ArrayList<String> generateOptions(String correctAnswer) {
        ArrayList<String> allAnswers = new ArrayList<>(katakana.keySet());
        allAnswers.remove(correctAnswer);
        Collections.shuffle(allAnswers);

        ArrayList<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add(allAnswers.get(0));
        options.add(allAnswers.get(1));
        options.add(allAnswers.get(2));
        Collections.shuffle(options);

        return options;
    }

    // Main method to run the application
    public static void main(String[] args) {
        KatakanaQuizApp app = new KatakanaQuizApp();
        app.start();
    }
}
