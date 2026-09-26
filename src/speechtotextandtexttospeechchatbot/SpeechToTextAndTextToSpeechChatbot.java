package speechtotextandtexttospeechchatbot;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SpeechToTextAndTextToSpeechChatbot {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                System.out.println("\n--- KevBot Main Menu ---");
                System.out.println("1. Text to Speech");
                System.out.println("2. Chatbot");
                System.out.println("3. All Available Voices");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Enter text to convert to speech:");
                        String text = scanner.nextLine();
                        textToSpeech(text);
                        break;

                    case 2:
                        chatbot();
                        break;

                    case 3:
                        voices();
                        break;

                    case 4:
                        running = false;
                        System.out.println("Exiting KevBot...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    public static void voices() {

        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory,"
                + "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory");

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        System.out.println("\nAvailable voices:");

        for (Voice v : voices) {
            System.out.println(v.getName());
        }
    }

    public static void textToSpeech(String text) {

        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory,"
                + "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory");

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");

        if (voice != null) {

            voice.allocate();

            voice.setRate(120);
            voice.setPitch(100);
            voice.setVolume(1);

            voice.speak(text);

            voice.deallocate();

        } else {
            System.out.println("Voice not found.");
        }
    }

    public static void chatbot() {

        List<String[]> responses = new ArrayList<>();

        responses.add(new String[]{"hello", "Hello! Nice to meet you."});
        responses.add(new String[]{"hi", "Hi! How can I help you today?"});
        responses.add(new String[]{"how are you",
            "I am doing great. Thank you for asking!"});
        responses.add(new String[]{"what is your name",
            "My name is KevBot. I am a Java chatbot."});
        responses.add(new String[]{"help",
            "I can chat with you and read my responses aloud."});
        responses.add(new String[]{"thank you",
            "You are welcome!"});
        responses.add(new String[]{"bye",
            "Goodbye! Have a great day!"});

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Welcome to KevBot ---");
        System.out.print("What is your name? ");

        String userName = scanner.nextLine();

        String welcomeMessage =
                "Hello " + userName + "! Nice to meet you.";

        System.out.println("Bot: " + welcomeMessage);
        textToSpeech(welcomeMessage);

        System.out.println("\nYou can try:");
        System.out.println("hello");
        System.out.println("how are you");
        System.out.println("what is your name");
        System.out.println("help");
        System.out.println("thank you");
        System.out.println("bye");
        System.out.println("exit");

        while (true) {

            System.out.print("\n" + userName + ": ");
            String userInput = scanner.nextLine().toLowerCase().trim();

            if (userInput.equals("exit")) {

                String goodbye =
                        "Goodbye " + userName + "! See you next time.";

                System.out.println("Bot: " + goodbye);
                textToSpeech(goodbye);
                break;
            }

            String response =
                    "Sorry " + userName
                    + ", I do not understand that yet.";

            for (String[] pair : responses) {

                if (pair[0].equals(userInput)) {
                    response = pair[1];
                    break;
                }
            }

            System.out.println("Bot: " + response);

            // Voice feedback for chatbot responses
            textToSpeech(response);
        }
    }
}