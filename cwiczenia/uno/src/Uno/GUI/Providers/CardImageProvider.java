package Uno.GUI.Providers;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.Card.Value;
import Uno.Engine.Deck.Deck;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;

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
                String filename = getCardFilename(card);
                var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                if(stream == null) {
                    stream = new FileInputStream(filename);
                }
                Image cardImage = ImageIO.read(stream)
                        .getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH);

                cardImages.put(card, cardImage);
            } catch (IOException e) {
                System.out.println("could not load card "+ card + " " + e.getMessage());
            }
        });
    }

    private static String getCardFilename(Card card) {
        return String.format("cards/%s_%s_%s.png", card.getValue(), card.getColor(), card.getAction());
    }

    public static  Image get(Card card) {
        if(cardImages != null ){
            return cardImages.get(card);
        }
        return null;
    }

}

