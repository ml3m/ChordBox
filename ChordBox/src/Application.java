import java.util.Arrays;

public class Application {
    private InputDevice inputDevice;
    private OutputDevice outputDevice;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    public void run(Integer roundsToWin) {
        playGame(roundsToWin);
    }

    public void sortNumbers(Integer[] numbers) {
        Arrays.sort(numbers);
    }

    // will return an array with ints that represent the word count, for ex; on [3] the value will be 4 because
    // there are 4 words of length 3
    public Integer[] wordSizeHistorgram(String input_word){
        // since in the string may be any punctuation symbols, we must remove them before split()
        String cleaned_input_word = input_word.replace(".", "");
        String[] words = cleaned_input_word.split(" ");

        // find longest word in order to init histogram
        int largestLengthWord = 0;
        for (String word : words) {
            if (word.length() > largestLengthWord) {
                largestLengthWord = word.length();
            }
        }

        Integer[] historgram_array = new Integer[largestLengthWord + 1];
        Arrays.fill(historgram_array, 0);
        for (String word : words) {
            int word_length = word.length();
            historgram_array[word_length]++;
        }
        return historgram_array;
    }

    // tests the generating and sorting of an array of size N random elements (range 100)
    public void test_randomArraySort() {
        Integer[] test_array = inputDevice.getNumbers(10);
        sortNumbers(test_array);
        outputDevice.printArray(test_array);
    }

    public void playGame(Integer roundsToWin) {
        int player1Wins = 0;
        int player2Wins = 0;
        int rounds = 0;
        boolean overRoundsGameEnd = false;

        while (player1Wins < roundsToWin && player2Wins < roundsToWin && rounds < roundsToWin) {
            /*
           the strategy to win this game as much as possible, to raise your chances
           is to select a high number like 99 or a prime number thus leaving the other player with less likely chnces of
           chosing the right number that will trigger the win on their side.
             */
            int player1Number = inputDevice.nextInt();
            int player2Number = inputDevice.nextInt();

            if (rounds + 1 == roundsToWin) {
                overRoundsGameEnd = true;
                break;
            }

            rounds++;

            outputDevice.writeMessage("Round " + rounds + ": Player 1 choice: " + player1Number +
                    ", Player 2 choice: " + player2Number);


            if (player1Number == player2Number) {
                player1Wins += 2;
                player2Wins += 2;
                outputDevice.writeMessage("Both players choose the same number. Each gets 2 points.");
            } else {
                int higher = Math.max(player1Number, player2Number);
                int lower = Math.min(player1Number, player2Number);

                if (higher % lower == 0 && lower >= 10) {
                    if (lower == player1Number) {
                        player1Wins++;
                        outputDevice.writeMessage("Player 1 wins the round with " + player1Number +
                                " (multiple of " + player2Number + ")");
                    } else {
                        player2Wins++;
                        outputDevice.writeMessage("Player 2 wins the round with " + player2Number +
                                " (multiple of " + player1Number + ")");
                    }
                } else {
                    if (higher == player1Number) {
                        player1Wins++;
                        outputDevice.writeMessage("Player 1 wins the round with " + player1Number);
                    } else {
                        player2Wins++;
                        outputDevice.writeMessage("Player 2 wins the round with " + player2Number);
                    }
                }
            }

            outputDevice.writeMessage("Score: Player 1 - " + player1Wins + ", Player 2 - " + player2Wins);
        }

        if (overRoundsGameEnd) {
            outputDevice.writeMessage("!!!!!!!!!!!!!!!!!!!!!");
            outputDevice.writeMessage("!!! Out Of Rounds !!!");
            outputDevice.writeMessage("!!!!!!!!!!!!!!!!!!!!!");
        }

        outputDevice.writeMessage("Game over! Winner: " +
                (player1Wins >= 5 ? "Player 1" : "Player 2"));
    }

}