import java.util.Arrays;

class Poker {
    static String[] deck = {
        "cA", "cK", "cQ", "cJ", "c10", "c9", "c8", "c7", "c6", "c5", "c4", "c3", "c2", // Clovers
        "pA", "pK", "pQ", "pJ", "p10", "p9", "p8", "p7", "p6", "p5", "p4", "p3", "p2", // Pickes
        "hA", "hK", "hQ", "hJ", "h10", "h9", "h8", "h7", "h6", "h5", "h4", "h3", "h2", // Hearts
        "dA", "dK", "dQ", "dJ", "d10", "d9", "d8", "d7", "d6", "d5", "d4", "d3", "d2" // Diamonds
    };
    
    static String[][] player_combinations = new String[0][5];
    static String[][] opponent_combinations = new String[0][5];

    public static void main (String[] args) {
        String[] cards = {"c2", "c3", "c4", "c5", "c6", "c7"};
        Poker p = new Poker();
        
        String[] discard = {"c4"};
        String[] newCards = p.discard_cards(cards, discard);
        System.out.println(Arrays.toString(newCards)); 
        
        p.possible_hands(cards, new String[0]);
        System.out.println(Arrays.deepToString(Poker.player_combinations)); 
        
        String[] cardsOnCommunity = {"c2", "c3", "c4"};
        String[] cardsAvailable = {"c2", "c3", "c4", "c5", "c6", "c7"};
        p.possible_hands_opponent(cardsAvailable, cardsOnCommunity, new String[0]);
        System.out.println(Arrays.deepToString(Poker.opponent_combinations)); 
    }
    
    public String[] discard_cards(String[] initialCards, String[] cardsToDiscard) {
        if (cardsToDiscard.length == 0) {
            return initialCards;
        }

        String cardToDiscard = cardsToDiscard[0];
        String[] remainingCards = Arrays.stream(initialCards)
                                        .filter(card -> !card.equals(cardToDiscard))
                                        .toArray(String[]::new);
        
        return discard_cards(remainingCards, Arrays.copyOfRange(cardsToDiscard, 1, cardsToDiscard.length));
    }

    public void possible_hands(String[] cardsAvailable, String[] cardsOnHands) {

        if (cardsOnHands.length == 5) {
            Poker.player_combinations = Arrays.copyOf(Poker.player_combinations, Poker.player_combinations.length + 1);
            Poker.player_combinations[Poker.player_combinations.length - 1] = cardsOnHands;
            return;
        }

        for (int i = 0; i < cardsAvailable.length; i++) {
            String[] newCardsOnHands = Arrays.copyOf(cardsOnHands, cardsOnHands.length + 1);
            newCardsOnHands[cardsOnHands.length] = cardsAvailable[i];

            String[] newCardsAvailable = Arrays.copyOfRange(cardsAvailable, i + 1, cardsAvailable.length);

            possible_hands(newCardsAvailable, newCardsOnHands);
        }
    }

    public void possible_hands_opponent(String[] cardsAvailable, String[] cardsOnCommunity, String[] cardsOnHands) {
        int communityCount = (int) Arrays.stream(cardsOnHands)
                                         .filter(card -> Arrays.asList(cardsOnCommunity).contains(card))
                                         .count();

        if (cardsOnHands.length == 5) {
            if (communityCount >= 3) {
                Poker.opponent_combinations = Arrays.copyOf(Poker.opponent_combinations, Poker.opponent_combinations.length + 1);
                Poker.opponent_combinations[Poker.opponent_combinations.length - 1] = cardsOnHands;
            }
            return;
        }

        for (int i = 0; i < cardsAvailable.length; i++) {
            String[] newCardsOnHands = Arrays.copyOf(cardsOnHands, cardsOnHands.length + 1);
            newCardsOnHands[cardsOnHands.length] = cardsAvailable[i];

            String[] newCardsAvailable = Arrays.copyOfRange(cardsAvailable, i + 1, cardsAvailable.length);

            possible_hands_opponent(newCardsAvailable, cardsOnCommunity, newCardsOnHands);
        }
    }
}
