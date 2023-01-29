package Uno.Network.Server.Message;

import Uno.Network.Utilities.PromiscousByteArrayOutputStream;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.UUID;

public class Message implements Serializable {

    public static final long serialVersionUID = 2L;

    public static final String MESSAGE_HEADER = "UNOMESSAGEHEADER";
    private MessageType messageType;

    private Object attachment;


    public Message(MessageType messageType, Object attachment) {
        this.messageType = messageType;
        this.attachment = attachment;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getAttachment() {
        return attachment;
    }



    @Override
    public String toString() {
        return "[Message: " + messageType.toString() + "] " + (attachment == null ? "null" : attachment.toString());
    }


    public static ByteBuffer wrapMessage(Message message) {
        try {
            PromiscousByteArrayOutputStream pbos = new PromiscousByteArrayOutputStream();
            pbos.writeBytes(MESSAGE_HEADER.getBytes());
            ObjectOutputStream oos = new ObjectOutputStream(pbos);
            oos.writeObject(message);
            return ByteBuffer.wrap(pbos.getBuf());
        } catch (IOException ex) {
            // should never happen
            throw new IllegalStateException(ex);
        }

    }
}
