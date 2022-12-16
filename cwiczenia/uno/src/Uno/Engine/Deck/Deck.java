package Uno.Engine.Deck;

import Uno.Engine.Card.Action;
import Uno.Engine.Card.Card;
import Uno.Engine.Card.Color;
import Uno.Engine.Card.Value;
import Uno.Engine.Pile.Pile;

import java.util.ArrayList;

public class Deck extends Pile {

    public Deck() {
        super();
        // color
        for(int j = 0 ; j < 4; j++) {
            // add basic cards
            for(int i = 0; i < 10; i ++) {
                // value
                if(i != 0) {
                    this.add(new Card(Color.values()[j], Value.values()[i], Action.NONE));
                }
                this.add(new Card(Color.values()[j], Value.values()[i], Action.NONE));
            }
            // add colored action cards
            for(int i = 0; i < 3; i ++) {
                this.add(new Card(Color.values()[j], Value.NONE, Action.values()[i]));
                this.add(new Card(Color.values()[j], Value.NONE, Action.values()[i]));

            }

        }
        // add wildcards
        for(int i = 0; i < 4; i++) {
            this.add(new Card(Color.NONE, Value.NONE, Action.WILD_CARD));
            this.add(new Card(Color.NONE, Value.NONE, Action.WILD_DRAW_4));
        }



        this.shuffle();

    }


}
