package br.ufrn.bora.service.dto;

import java.util.Objects;

public class EventRegisterDTO {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String eventId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
