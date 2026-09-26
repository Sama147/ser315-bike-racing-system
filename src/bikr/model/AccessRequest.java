package bikr.model;

import bikr.model.enums.RequestStatus;

public class AccessRequest {
    private int requestId;
    private int userId;
    private RequestStatus status;

    public AccessRequest() { this.status = RequestStatus.PENDING; }

    public AccessRequest(int userId) {
        this.userId = userId;
        this.status = RequestStatus.PENDING;
    }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
}