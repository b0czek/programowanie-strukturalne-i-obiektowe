package Uno.Network.Server.Message;

import java.io.*;

public class Message implements Serializable {

    public static final long serialVersionUID = 1L;
    private Command command;

    private Object attachment;

    public Message() {}

    public Message(Command command, Object attachment) {
        this.command = command;
        this.attachment = attachment;
    }

    public Command getCommand() {
        return command;
    }

    public Object getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return "[Message: " + command.toString() + "] " + attachment.toString();
    }
}
