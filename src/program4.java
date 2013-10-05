//Anthony Mace  CSC205AB
//This program is designed to set up two different classes
//a DeckHand class, and a Card class. These two classes are then
//used and tested in the program4 class

import java.util.*; //For Random and Scanner

public class program4 {

    public static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        int a = 0;
        printIntro();
        System.out.println();
        do {
            System.out.println();
            a = mainMenu();
            if (a == 1) {
                testDeck();
            } else if (a == 2) {
                System.out.println("Let's play Go fish!");
                goFish();
            }
        } while (a != 3);
        quitProg();
    }

    //Runs the test DeckHand portion
    public static void testDeck() {
        int b = 0;
        int deckChoice = 0;
        char again = '0';
        DeckHand deck1 = new DeckHand();
        fillDeck(deck1);
        DeckHand deck2 = new DeckHand();
        do {
            b = deckMenu();
            System.out.println();
            if (b != 8) {
                System.out.print("Would you like to use the the first deck or the \n" +
                        "second deck? Enter 1 for the first deck and 2 for \n" +
                        "second deck: ");
                deckChoice = console.nextInt();
                System.out.println();
            }
            while (deckChoice != 1 && deckChoice != 2) {
                System.out.println("You did not pick a valid deck.");
                System.out.print("Enter 1 or 2: ");
                deckChoice = console.nextInt();
                System.out.println();
            }
            DeckHand deckChosen = null;
            switch (deckChoice) {
                case 1:
                    deckChosen = deck1;
                    break;
                case 2:
                    deckChosen = deck2;
                    break;
            }
            switch (b) {
                case 1:
                    System.out.println("A new, empty deck will replace the current" +
                            " deck contents.");
                    if (deckChoice == 1) {
                        deck1 = newDeck(deckChosen);
                    } else if (deckChoice == 2) {
                        deck2 = newDeck(deckChosen);
                    }
                    break;
                case 2:
                    insertCard(deckChosen);
                    break;
                case 3:
                    deleteCard(deckChosen);
                    break;
                case 4:
                    printCount(deckChosen);
                    break;
                case 5:
                    printSize(deckChosen);
                    break;
                case 6:
                    printDeck(deckChosen);
                    break;
                case 7:
                    deleteRand(deckChosen);
                    break;
            }
            System.out.println();
            System.out.println("Would you like to perform more menu" +
                    " options?");
            System.out.print("Yes or No? Enter Y or N: ");
            again = console.next().charAt(0);
            System.out.println();
            while (again != 'Y' && again != 'y'
                    && again != 'N' && again != 'n') {
                System.out.println("You did not enter Y or N");
                System.out.print("Enter Y or N: ");
                again = console.next().charAt(0);
                System.out.println();
            }
        } while (again == 'Y' || again == 'y');
    }

    //Plays Go Fish
    public static void goFish() {
        int face = 0;
        int cardCount = 0;
        Card deletedCard = null;
        boolean playerTurn = true;
        DeckHand stock = new DeckHand();
        fillDeck(stock);
        DeckHand userDeck = new DeckHand();
        DeckHand compDeck = new DeckHand();
        deal(stock, userDeck);
        deal(stock, compDeck);
        System.out.println(userDeck);
        System.out.println(compDeck);
        if (playerTurn) {
            face = askCard(userDeck);
            System.out.println(face);
            hasCard(face, compDeck);
            if (hasCard(face, compDeck)) {
                cardCount = compDeck.count(face);
                for (int i = 0; i < cardCount; i++) {
                    deletedCard = compDeck.popCard(face);
                    userDeck.pushCard(deletedCard);
                }
                System.out.println(compDeck);
                System.out.println(userDeck);
            }
            playerTurn = false;
        }
    }

    //Prints the intro to let the user know what the program
    //does
    public static void printIntro() {
        System.out.println("This program is designed to maniputlate " +
                            "decks of cards \nand allow the user to play " +
                            " the card game Go Fish.");
    }

    //Prints the main menu, acquires menu choice,
    //returns menu choice
    public static int mainMenu() {
        int choice = 0;
        System.out.println("Choose a task number from the following:");
        System.out.println("       1 - Test DeckHand");
        System.out.println("       2 - Play \"Go Fish\"");
        System.out.println("       3 - Quit the program");
        while (choice < 1 || choice > 3) {
            System.out.print("Enter a number between 1 and 3: ");
            choice = console.nextInt();
            System.out.println();
        }
        return choice;

    }
    //Prints the deck menu, acquires menu choice,
    //returns menu choice
    public static int deckMenu() {
        int choice = 0;
        System.out.println("Choose a task number from the following:");
        System.out.println("       1 - Create a new, empty deck");
        System.out.println("       2 - Insert a card in the Deck");
        System.out.println("       3 - Delete one instance of a Card in the Deck");
        System.out.println("       4 - Print the number of times a face value" +
                                    " occurs in Deck");
        System.out.println("       5 - Print the size of the Deck");
        System.out.println("       6 - Print the entire Deck");
        System.out.println("       7 - Delete a random Card");
        System.out.print("Insert menu option: ");
        choice = console.nextInt();
        while (choice < 1 || choice > 7) {
            System.out.print("Enter a number between 1 and 7: ");
            choice = console.nextInt();
        }
        return choice;
    }

    //Fills deck1 to a standard, full poker deck
    public static void fillDeck(DeckHand fullDeck) {
        for (int i = 1; i <= 13; i++) {
            for (int j = 1; j <= 4; j++) {
                fullDeck.pushCard(new Card(i, j));
            }
        }
    }

    //Deals 7 cards to each deck
    public static void deal(DeckHand full, DeckHand deck) {
        int numCards = 7;
        for (int i = 0; i < numCards; i++) {
            Card dealtCard = full.popAny();
            deck.pushCard(dealtCard);
        }
    }

    //Creates a new, empty DeckHand when the user picks
    //menu option 1
    public static DeckHand newDeck(DeckHand deck) {
        deck = new DeckHand();
        return deck;
    }

    public static int askCard(DeckHand deck) {
        int face = 0;
        int count = 0;
        boolean cardInDeck = false;
        System.out.println("Which card from your hand would you like to ask for?");
        System.out.print("Enter the face value: ");
        face = console.nextInt();
        count = deck.count(face);
        while (count == 0) {
            System.out.println();
            System.out.println("No card with the entered face value is in" +
                                " your hand!");
            System.out.print("Please enter a face value that is in your hand: ");
            face = console.nextInt();
            count = deck.count(face);
        }
        System.out.println();
        System.out.println("Can I please have your " + face + "'s");
        return face;
    }

    public static boolean hasCard(int faceValue,DeckHand deck) {
        int count = 0;
        count = deck.count(faceValue);
        return (count != 0);
    }

    //Inserts a card when the user picks menu option 2
    //into the appropriate deck
    public static void insertCard(DeckHand deck) {
        int face = 0;
        int suit = 0;
        System.out.print("Card's face value (1 - 13): ");
        face = console.nextInt();
        System.out.print("Card's suit value (1 - 4): ");
        suit = console.nextInt();
        Card newCard = new Card(face, suit);
        deck.pushCard(newCard);
        System.out.println();
        System.out.println("The card " + newCard + " was inserted.");
    }

    //Deletes a card with a given face value from the
    //appropriate deck when user picks menu option 3
    public static void deleteCard(DeckHand deck) {
        int face = 0;
        int count = 0;
        System.out.print("Card's face value: ");
        face = console.nextInt();
        count = deck.count(face);
        if (count != 0) {
            Card deleted = deck.popCard(face);
            System.out.println();
            checkCard(deleted);
        }
    }

    //Deletes a random card from the appropriate deck
    //when user picks menu option 7. If no card with
    //the randomly generate face value is found in the deck
    //the method lets the user know that no card was deleted
    public static void deleteRand(DeckHand deck) {
        int size = 0;
        size = deck.getSize();
        if (size != 0) {
            Card deleted = deck.popAny();
            checkCard(deleted);
        }
    }

    //Prints the count of the given face value
    //from the appropriate deck when user picks menu
    //option 4
    public static void printCount(DeckHand deck) {
        int count = 0;
        int value = 0;
        System.out.print("Enter face value to count: ");
        value = console.nextInt();
        count = deck.count(value);
        System.out.println();
        System.out.println("The face value " + value + " occurs " + count + " times.");
    }

    //Prints the size of the appropriate deck when
    //user picks menu option 5
    public static void printSize(DeckHand deck) {
        System.out.println("The size of the deck is: " + deck.getSize());
    }

    //Prints the entire deck when user picks
    //menu option 6
    public static void printDeck(DeckHand deck) {
        System.out.println(deck);
    }

    //Checks that the given card is in the deck,
    //if not, lets the user know
    public static void checkCard(Card card) {
        if (card != null) {
            System.out.println("The card " + card + " was deleted.");
        }
    }

    public static void quitProg() {
        System.out.println("You have quit the program.");
    }
}

class Card {

    private int _faceValue;
    private int _suitValue;

    //Named constants
    //Face values
    private static final String[] FACE_VALUES = {"Ace", "Two", "Three", "Four",
                                         "Five", "Six", "Seven", "Eight", "Nine",
                                         "Ten", "Jack", "Queen", "King"};

    //Suit values
    private static final String[] SUIT_VALUES = {" of Hearts", " of Diamonds",
                                                 " of Clubs", " of Spades"};

    //Constructs a card with values passed
    public Card(int faceValue, int suitValue) {
        _faceValue = faceValue;
        _suitValue = suitValue;
    }

    //Returns faceValue int
    public int getFaceValue() {
        return _faceValue;
    }

    //Returns suitValue int
    public int getSuitValue() {
        return _suitValue;
    }

    //Prints out a Card
    public String toString() {
        String face = "";
        String suit = "";
        face = FACE_VALUES[_faceValue - 1];
        suit = SUIT_VALUES[_suitValue - 1];
        return face + suit;
    }
}

class DeckHand {

    private static final int CARDS = 52;
    private static final int FACE_VALUE = 13;
    private static Random generator = new Random();
    private int cardCount;
    private Card[] deck;

    //Constructs a DeckHand
    public DeckHand() {
        cardCount = 0;
        deck = new Card[CARDS];
    }

    //Returns size of DeckHand
    public int getSize() {
        return cardCount;
    }

    //Adds card to DeckHand and creates a larger
    //array and copies contents from old array if
    //expansion is needed
    public void pushCard(Card card) {
        if (cardCount >= deck.length) {
            Card[] temp = new Card[deck.length + 2];
            for (int i = 0; i < cardCount; i++) {
                temp[i] = deck[i];
            }
            deck = temp;
        }
        deck[cardCount] = card;
        cardCount++;
    }

    //Returns the number of times a given value
    //occurs in DeckHand
    public int count(int faceValue) {
        int count = 0;
        for (int i = 0; i < cardCount; i++) {
            if (faceValue == deck[i].getFaceValue()) {
                count++;
            }
        }
        return count;
    }

    //Returns one instance of a Card with given value
    //from DeckHand and replaces instance with Card at
    //the end of the deck
    public Card popCard(int faceValue) {
        return findCard(faceValue);
    }

    public Card popAny() {
        int deletionVal = 0;
        deletionVal = generator.nextInt(FACE_VALUE) + 1;
        return findCard(deletionVal);
    }

    //Prints out entire deck
    public String toString() {
        String currentDeck = "";
        for (int i = 0; i < cardCount; i++) {
                currentDeck += deck[i] + "\n";
        }
        return currentDeck;
    }

    private Card findCard(int faceValue) {
        boolean foundIt = false;
        Card found = null;
        for (int i = 0; i < cardCount; i++) {
            if (faceValue == deck[i].getFaceValue() && !foundIt) {
                found = deck[i];
                foundIt = true;
                if (cardCount > 1) {
                    deck[i] = deck[cardCount - 1];
                } else {
                    deck[i] = null;
                }
            }
        }
        if (foundIt) {
            deck[cardCount - 1] = null;
            cardCount--;
        }
        return found;
    }
}