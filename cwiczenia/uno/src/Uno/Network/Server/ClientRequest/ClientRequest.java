package Uno.Network.Server.ClientRequest;

import java.io.Serializable;
import java.util.UUID;

public class ClientRequest implements Serializable {
    public static final long serialVersionUID = 1L;

    private final UUID requestUuid = UUID.randomUUID();
    private final RequestType requestType;
    private final Object attachment;

    public ClientRequest(RequestType requestType) {
        this.requestType = requestType;
        this.attachment = null;
    }

    public ClientRequest(RequestType requestType, Object attachment) {
        this.requestType = requestType;
        this.attachment = attachment;
    }

    public boolean isAttachmentValid() {
        if(this.requestType.getAttachmentClass() == null) {
            return this.attachment == null;
        }
        return this.requestType.getAttachmentClass().isInstance(attachment);
    }

    public UUID getRequestUuid() {
        return requestUuid;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Object getAttachment() {
        return attachment;
    }

    public String toString() {
        return "[Request: " + requestType.toString() + "] " + attachment.toString();
    }

}
