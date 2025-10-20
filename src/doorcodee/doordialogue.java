package doorcodee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

public class doordialogue {
    private static final Random random = new Random();
    private static Timer dialogueTimer;
    private static final int DIALOGUE_DELAY = 1500; // 1.5 seconds until response
    private static boolean inDialogue = false;
    private static int currentApartment;
    private static int currentDialogueStep = 0;
    private static String residentMood; // Resident mood: "friendly", "suspicious", "angry"
    
    // Dialogues for different resident moods
    private static final HashMap<String, String[]> DIALOGUES = new HashMap<>();
    private static final HashMap<String, Double> OPEN_CHANCE = new HashMap<>();
    
    static {
        // Initialize dialogues and door opening chances
        DIALOGUES.put("friendly", new String[]{
            "Hello! Who is there?",
            "Oh, I see! Come in, the door is open.",
            "Sorry, but I'm busy right now. Come back later."
        });
        
        DIALOGUES.put("suspicious", new String[]{
            "Who is this? I don't know you.",
            "What's this about?",
            "Alright, you seem to be telling the truth. Come in.",
            "I'm not convinced. Call another time."
        });
        
        DIALOGUES.put("angry", new String[]{
            "Someone ringing again! What do you want?",
            "Not a good time for visitors! Go away!",
            "Fine, but only for a short time!",
            "No and no again! Don't bother me!"
        });
        
        // Probability of door opening for each mood
        OPEN_CHANCE.put("friendly", 0.8);
        OPEN_CHANCE.put("suspicious", 0.4);
        OPEN_CHANCE.put("angry", 0.2);
    }

    public doordialogue() {
        dialogueTimer = new Timer(DIALOGUE_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDialogueResponse();
            }
        });
        dialogueTimer.setRepeats(false);
    }

    // Start dialogue when calling an apartment
    public void startDialogue(int apartmentNumber) {
        currentApartment = apartmentNumber;
        inDialogue = true;
        currentDialogueStep = 0;
        
        // Determine resident mood randomly
        String[] moods = {"friendly", "suspicious", "angry"};
        residentMood = moods[random.nextInt(moods.length)];
        
        // Show initial message
        showDialogueMessage("Calling apartment " + apartmentNumber + "...");
        
        // Start timer for resident response
        dialogueTimer.start();
    }

    // Handle resident response
    private void handleDialogueResponse() {
        String[] dialogue = DIALOGUES.get(residentMood);
        
        if (currentDialogueStep == 0) {
            // First resident response
            showDialogueWithOptions(dialogue[0], getInitialOptions());
        } else if (currentDialogueStep == 1) {
            // Second dialogue step
            boolean doorOpened = random.nextDouble() < OPEN_CHANCE.get(residentMood);
            if (doorOpened) {
                showDialogueMessage(dialogue[1]);
                endDialogue(true);
            } else {
                showDialogueMessage(dialogue[dialogue.length - 1]);
                endDialogue(false);
            }
        }
    }

    // Get initial response options
    private String[] getInitialOptions() {
        return new String[]{
            "I'm a friend, here to visit",
            "Delivery person, I have a package", 
            "Neighbor from downstairs",
            "Technician, checking the meters"
        };
    }

    // Get follow-up response options
    private String[] getFollowUpOptions() {
        return new String[]{
            "Can I come in?",
            "It's urgent!",
            "I just need to deliver some documents",
            "Okay, sorry to bother you"
        };
    }

    // Show dialogue with response options
    private void showDialogueWithOptions(String residentMessage, String[] options) {
        StringBuilder message = new StringBuilder();
        message.append("Apartment ").append(currentApartment).append(" resident: \"")
               .append(residentMessage).append("\"\n\nYour responses:\n");
        
        for (int i = 0; i < options.length; i++) {
            message.append(i + 1).append(". ").append(options[i]).append("\n");
        }
        
        String userChoice = JOptionPane.showInputDialog(null, 
            message.toString(), 
            "Dialogue with Resident", 
            JOptionPane.PLAIN_MESSAGE);
        
        if (userChoice != null) {
            try {
                int choice = Integer.parseInt(userChoice.trim());
                if (choice >= 1 && choice <= options.length) {
                    processUserChoice(choice - 1, options);
                } else {
                    showDialogueMessage("Resident: *hung up*");
                    endDialogue(false);
                }
            } catch (NumberFormatException e) {
                showDialogueMessage("Resident: *hung up*");
                endDialogue(false);
            }
        } else {
            showDialogueMessage("Resident: *hung up*");
            endDialogue(false);
        }
    }

    // Process user choice
    private void processUserChoice(int choiceIndex, String[] options) {
        currentDialogueStep++;
        
        // Show chosen response
        showDialogueMessage("You: \"" + options[choiceIndex] + "\"");
        
        // Resident thinks about response
        Timer thinkingTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDialogueResponse();
            }
        });
        thinkingTimer.setRepeats(false);
        thinkingTimer.start();
    }

    // Show simple dialogue message
    private void showDialogueMessage(String message) {
        JOptionPane.showMessageDialog(null, 
            message, 
            "Dialogue with Resident", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    // End dialogue
    private void endDialogue(boolean doorOpened) {
        inDialogue = false;
        currentDialogueStep = 0;
        
        if (doorOpened) {
            showDialogueMessage("Door opened! You may enter.");
            // Here you can add door opening logic
        } else {
            showDialogueMessage("Door not opened. Please try again later.");
        }
    }

    // Check if dialogue is currently active
    public boolean isInDialogue() {
        return inDialogue;
    }

    // Force end dialogue
    public void forceEndDialogue() {
        if (inDialogue) {
            dialogueTimer.stop();
            inDialogue = false;
            currentDialogueStep = 0;
        }
    }
}