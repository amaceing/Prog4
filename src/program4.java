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
        boolean gameOver = false;
        int playerBooks = 0;
        int compBooks = 0;
        boolean playerTurn = true;
        DeckHand stock = new DeckHand();
        fillDeck(stock);
        DeckHand userDeck = new DeckHand();
        DeckHand compDeck = new DeckHand();
        deal(stock, userDeck);
        deal(stock, compDeck);
        do {
            if (stock.getSize() == 0) {
                System.out.println("There are no more cards left" +
                                    " in the deck!");
                gameOver = true;
            } else {
                //Runs player turn operations
                if (playerTurn) {
                    if (userDeck.getSize() > 0) {
                        printGameStatus(playerBooks, compBooks, userDeck);
                        int face = askCard(userDeck);
                        if (hasCard(face, compDeck)) {
                            transferCardsToPlayer(userDeck, compDeck, face);
                            playerBooks += books(userDeck, face);
                            playerTurn = true;
                        } else {
                            playerGoFish();
                            Card draw = stock.deleteAny();
                            userDeck.insertCard(draw);
                            System.out.println("Card drawn is " + draw);
                            if (draw.getFaceValue() == face) {
                                playerGotCardAskedFor();
                                playerTurn = true;
                            } else {
                                playerTurn = false;
                            }
                            playerBooks += books(userDeck, draw.getFaceValue());
                        }
                    } else {
                        System.out.println("You have gotten rid of all your cards!");
                        gameOver = true;
                    }
                    System.out.println();
                } else {
                    //Runs computer turn operations
                    if (compDeck.getSize() > 0) {
                        Card askCard = compDeck.deleteAny();
                        int face = askCard.getFaceValue();
                        insertCardBackIntoCompHand(compDeck, askCard);
                        if(hasCard(face, userDeck)) {
                            transferCardsToComp(compDeck, userDeck, face);
                            compBooks += books(compDeck, face);
                            playerTurn = false;
                        } else {
                            compGoFish();
                            Card draw = stock.deleteAny();
                            compDeck.insertCard(draw);
                            if (draw.getFaceValue() == face) {
                                computerGotCardAskedFor();
                                playerTurn = false;
                            } else {
                                playerTurn = true;
                            }
                            compBooks += books(compDeck, face);
                        }
                        System.out.println();
                        continueAfterComp();
                    } else {
                        System.out.println("The computer has gotten rid of all its cards!");
                        gameOver = true;
                    }
                }
                System.out.println();
            }
        } while(!gameOver);
        decideWinner(playerBooks, compBooks);
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
        System.out.print("Enter menu option: ");
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
                fullDeck.insertCard(new Card(i, j));
            }
        }
    }

    public static void printGameStatus(int playerBooks, int compBooks,
                                       DeckHand user) {
        System.out.println("Game Status:");
        System.out.println("Player books - " + playerBooks);
        System.out.println("Computer books - " + compBooks);
        System.out.println("Hand:");
        System.out.println(user);
    }

    public static void decideWinner(int playerBooks, int compBooks) {
        System.out.println("User books - " + playerBooks);
        System.out.println("Computer books - " + compBooks);
        if (playerBooks > compBooks) {
            System.out.println("You have won!");
        } else if (compBooks > playerBooks) {
            System.out.println("The computer has won!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static void continueAfterComp() {
        char cont = '0';
        System.out.print("Enter Y to continue: ");
        cont = console.next().charAt(0);
        while (cont != 'Y' && cont != 'y') {
            System.out.println("You did not enter Y.");
            System.out.print("Enter Y: ");
            cont = console.next().charAt(0);
        }
    }

    public static void transferCardsToPlayer(DeckHand user, DeckHand comp,
                                             int faceValueOfCard) {
        Card deletedCard = null;
        System.out.println("You get the computer's " + faceValueOfCard + "'s");
        int cardCount = comp.count(faceValueOfCard);
        for (int i = 0; i < cardCount; i++) {
            deletedCard = comp.deleteCard(faceValueOfCard);
            user.insertCard(deletedCard);
        }
        System.out.println();
    }

    public static void transferCardsToComp(DeckHand comp, DeckHand user,
                                           int faceValueOfCard) {
        Card deletedCard = null;
        System.out.println("The computer gets your " +
                faceValueOfCard + "'s");
        int cardCount = user.count(faceValueOfCard);
        for (int i = 0; i < cardCount; i++) {
            deletedCard = user.deleteCard(faceValueOfCard);
            comp.insertCard(deletedCard);
        }
    }

    public static void playerGoFish() {
        System.out.println();
        System.out.println("Go Fish!");
        System.out.println("You must draw a card from the deck.");
    }

    public static void compGoFish() {
        System.out.println();
        System.out.println("Go Fish!");
        System.out.println("The computer must draw from the deck!");
    }

    public static void playerGotCardAskedFor() {
        System.out.println();
        System.out.println("The card you've drawn is what you asked!");
        System.out.println("You get another turn!");
    }

    public static void computerGotCardAskedFor() {
        System.out.println();
        System.out.println("The card the computer drew is" +
                " the card it asked for!");
        System.out.println("The computer gets another turn!");
        System.out.println();
    }

    public static void insertCardBackIntoCompHand(DeckHand comp, Card card) {
        System.out.println("The computer is asking for your " +
                card.getFaceValue() + "'s");
        System.out.println();
        comp.insertCard(card);
    }

    //Deals 7 cards to each deck
    public static void deal(DeckHand full, DeckHand deck) {
        int numCards = 7;
        Card dealtCard = null;
        for (int i = 0; i < numCards; i++) {
            dealtCard = full.deleteAny();
            deck.insertCard(dealtCard);
        }
    }

    //Creates a new, empty DeckHand when the user picks
    //menu option 1
    public static DeckHand newDeck(DeckHand deck) {
        deck = new DeckHand();
        return deck;
    }

    //Method that asks the player to choose a card
    //from their hand and checks that it's a valid card.
    //Returns the card's face value
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

    //Checks to see if the user has acquired
    //a book of cards
    public static int books(DeckHand deck, int faceValue) {
        int cardCount = deck.count(faceValue);
        int books = 0;
        Card deletedCard = null;
        if (cardCount == 4) {
            books = 1;
            System.out.println();
            System.out.println("A book has been collected!");
            System.out.println("These cards will now be deleted from the hand.");
            System.out.println();
            for (int i = 4; i > 0; i--) {
                deletedCard = deck.deleteCard(faceValue);
                System.out.println(deletedCard);
            }
        }
        return books;
    }

    //Boolean returning method that shows whether
    //or not a card with parameter faceValue is
    //in the given DeckHand
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
        deck.insertCard(newCard);
        System.out.println();
        System.out.println("The card " + newCard + " was inserted.");
    }

    //Deletes a card with a given face value from the
    //appropriate deck when user picks menu option 3
    public static void deleteCard(DeckHand deck) {
        int face = 0;
        System.out.print("Card's face value: ");
        face = console.nextInt();
        Card deleted = deck.deleteCard(face);
        System.out.println();
        checkCard(deleted);
    }

    //Deletes a random card from the appropriate deck
    //when user picks menu option 7. If no card with
    //the randomly generate face value is found in the deck
    //the method lets the user know that no card was deleted
    public static void deleteRand(DeckHand deck) {
        Card deleted = deck.deleteAny();
        checkCard(deleted);
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
        if (card == null) {
            System.out.println("No card was deleted.");
        } else {
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
    public void insertCard(Card card) {
        if (cardCount >= deck.length) {
            Card[] temp = new Card[deck.length * 2];
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
    public Card deleteCard(int faceValue) {
        boolean foundIt = false;
        Card found = null;
        for (int i = 0; i < cardCount && !foundIt; i++) {
            if (faceValue == deck[i].getFaceValue()) {
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

    //Creates a random integer 1 - 13
    //faces that integer as a face value to
    //findCard to delete a random card
    public Card deleteAny() {
        int deletionVal = 0;
        Card deletedCard = null;
        if (getSize() > 0) {
            deletionVal = generator.nextInt(getSize());
            deletedCard = deck[deletionVal];
            deck[deletionVal] = deck[cardCount -1];
            deck[cardCount - 1] = null;
            cardCount--;
        }
        return deletedCard;
    }

    //Prints out entire deck
    public String toString() {
        String currentDeck = "";
        for (int i = 0; i < cardCount; i++) {
                currentDeck += deck[i] + "\n";
        }
        return currentDeck;
    }
}