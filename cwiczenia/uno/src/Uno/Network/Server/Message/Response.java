package Uno.Network.Server.Message;

import java.io.Serializable;
import java.util.UUID;

public class Response extends Message {
    public static final long serialVersionUID = 2L;

    private UUID contextUUID;
    private String statusMessage;
    private boolean requestFailed;

    public Response(MessageType messageType, Object attachment, UUID contextUUID, String statusMessage, boolean requestFailed) {
        super(messageType, attachment);
        this.contextUUID = contextUUID;
        this.statusMessage = statusMessage;
        this.requestFailed = requestFailed;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public boolean isRequestFailed() {
        return requestFailed;
    }

    public UUID getContextUUID() {
        return contextUUID;
    }

    @Override
    public String toString() {
        return "Response{" +
                "contextUUID=" + contextUUID +
                ", statusMessage='" + statusMessage + '\'' +
                ", requestFailed=" + requestFailed +
                ", message=" + super.toString() +
                '}';
    }
}
