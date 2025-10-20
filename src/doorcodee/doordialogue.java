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
    private static JLabel displayLabel; // Reference to main display
    
    // Dialogues for different resident moods
    private static final HashMap<String, String[]> DIALOGUES = new HashMap<>();
    private static final HashMap<String, String[]> RESPONSES = new HashMap<>();
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
        
        // Resident responses after user choice
        RESPONSES.put("friendly", new String[]{
            "Great! Come on up!",
            "Nice to see you! Door's open!",
            "Welcome! I'll put the tea on!",
            "Perfect timing! Come in!",
            "Awesome! Come right in!",
            "Wonderful! The door is unlocked!"
        });
        
        RESPONSES.put("suspicious", new String[]{
            "Hmm... alright, I'll open the door.",
            "Okay, but make it quick.",
            "I suppose you can come in...",
            "Fine, but I'm watching you.",
            "If you say so... door's open.",
            "Alright, but this better be important."
        });
        
        RESPONSES.put("angry", new String[]{
            "Go to hell! I'm not opening!",
            "Are you kidding me? No way!",
            "Get lost! Don't waste my time!",
            "Buzz off! I'm busy!",
            "Not in a million years! No!",
            "You've got to be joking! Never!",
            "Go away and don't come back!",
            "Screw off! I'm not in the mood!"
        });
        
        // Probability of door opening for each mood
        OPEN_CHANCE.put("friendly", 0.8);
        OPEN_CHANCE.put("suspicious", 0.4);
        OPEN_CHANCE.put("angry", 0.2);
    }

    public doordialogue(JLabel label) {
        this.displayLabel = label;
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
        
        // Show calling message on main display
        displayLabel.setText("Calling Apartment " + apartmentNumber + "...");
        
        // Start timer for resident response
        dialogueTimer.start();
    }

    // Handle resident response
    private void handleDialogueResponse() {
        String[] dialogue = DIALOGUES.get(residentMood);
        
        if (currentDialogueStep == 0) {
            // First resident response - show directly with dropdown
            showDialogueWithDropdown(dialogue[0], getInitialOptions());
        } else if (currentDialogueStep == 1) {
            // Show resident's reaction first
            showResidentReaction();
        }
    }

    // Get initial response options
    private String[] getInitialOptions() {
        return new String[]{
            "I'm a friend, here to visit",
            "Delivery person, I have a package", 
            "Neighbor from downstairs",
            "Technician, checking the meters",
            "Pizza delivery",
            "Just wanted to say hello",
            "Here to fix the plumbing",
            "Mailman with important letter"
        };
    }

    // Show dialogue with dropdown selection
    private void showDialogueWithDropdown(String residentMessage, String[] options) {
        // Show resident question with dropdown for response (with apartment number)
        String userChoice = (String) JOptionPane.showInputDialog(null,
            "Apartment " + currentApartment + " resident: \"" + residentMessage + "\"",
            "Intercom Call",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (userChoice != null) {
            processUserChoice();
        } else {
            // User cancelled or closed the dialog
            displayLabel.setText("Call Cancelled");
            endDialogue(false);
        }
    }

    // Process user choice
    private void processUserChoice() {
        currentDialogueStep++;
        
        // Show "Waiting for response..." on main display
        displayLabel.setText("Waiting for response...");
        
        // Resident thinks about response
        Timer thinkingTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDialogueResponse();
            }
        });
        thinkingTimer.setRepeats(false);
        thinkingTimer.start();
    }

    // Show resident's reaction
    private void showResidentReaction() {
        String[] responses = RESPONSES.get(residentMood);
        String residentResponse = responses[random.nextInt(responses.length)];
        
        // Show resident's reaction in dialog (without apartment number)
        JOptionPane.showMessageDialog(null,
            "\"" + residentResponse + "\"",
            "Resident Response",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Then show final result on main display
        showFinalResult();
    }

    // Show final result
    private void showFinalResult() {
        boolean doorOpened = random.nextDouble() < OPEN_CHANCE.get(residentMood);
        
        if (doorOpened) {
            displayLabel.setText("Door Opened by Resident");
            endDialogue(true);
        } else {
            displayLabel.setText("Resident Refused Access");
            endDialogue(false);
        }
    }

    // End dialogue
    private void endDialogue(boolean doorOpened) {
        inDialogue = false;
        currentDialogueStep = 0;
        
        // Auto-clear the message after 3 seconds
        Timer clearTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inDialogue) {
                    displayLabel.setText(" ");
                }
            }
        });
        clearTimer.setRepeats(false);
        clearTimer.start();
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
            displayLabel.setText("Call Ended");
            
            // Auto-clear the message after 2 seconds
            Timer clearTimer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayLabel.setText(" ");
                }
            });
            clearTimer.setRepeats(false);
            clearTimer.start();
        }
    }
}