package Uno.Network.Server.Chat;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChatHistory extends ArrayList<ChatMessage> {
    public static final long serialVersionUID = 1L;

    public static final int HISTORY_LENGTH = 100;


    @Override
    public boolean add(ChatMessage chatMessage) {
        if(this.size() == HISTORY_LENGTH) {
            super.remove(0);
        }
        return super.add(chatMessage);
    }


    @Override
    public String toString() {
        return this.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
