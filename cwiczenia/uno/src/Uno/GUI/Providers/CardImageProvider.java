package Uno.GUI.Providers;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.Card.Value;
import Uno.Engine.Deck.Deck;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CardImageProvider {
    public static final int cardWidth = 134;
    public static final int cardHeight = 200;
    private static HashMap<Card, Image> cardImages = null;
    public static void init() {
        if(cardImages != null ) {
            return;
        }
        cardImages = new HashMap<>();
        HashSet<Card> cards = new HashSet<>(new Deck());
        cards.add(new Card(Color.NONE, Value.NONE, Action.NONE));
        cards.forEach(card -> {
            try {
                Image cardImage = ImageIO.read(new File(String.format("cards/%s_%s_%s.png", card.getValue(), card.getColor(), card.getAction()))).getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH);
                cardImages.put(card, cardImage);
            } catch (IOException e) {
                System.out.println("could not load card "+ card + " " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }


    public static  Image get(Card card) {
        if(cardImages != null ){
            return cardImages.get(card);
        }
        return null;
    }

}

