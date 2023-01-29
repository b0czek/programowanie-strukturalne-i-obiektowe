package Uno.Network.Server.Chat;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    public static final long serialVersionUID = 1L;

    private String text;
    private String senderName;

    public ChatMessage(String text, String senderName) {
        this.text = text;
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public String getSenderName() {
        return senderName;
    }

    public String formatMessage() {
        return this.senderName + ": " + this.text + "\n";
    }

    @Override
    public String toString() {
        return "ChatMessage - " + senderName + ": " + text;
    }
}
