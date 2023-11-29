package br.ufrn.bora.domain;

import br.ufrn.bora.domain.enumeration.Status;
//import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Event.
 */
//@Document(collection = "event")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    private String id;

    //@Field("title")
    private String title;

    //@Field("organization")
    private String organization;

    //@Field("status")
    private Status status;

    //@Field("favorite")
    private Boolean favorite;

    //@Field("is_public")
    private Boolean isPublic;

    //@Field("url_image")
    private String urlImage;

    //@Field("date_start")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateStart;

    //@Field("date_end")
    //@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateEnd;

    //@Field("description")
    //@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String description;

    //@DBRef
    //@Field("location")
    private Location location;

    //@DBRef
    //@Field("ticket")
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Event id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return this.organization;
    }

    public Event organization(String organization) {
        this.setOrganization(organization);
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Status getStatus() {
        return this.status;
    }

    public Event status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }

    public Event favorite(Boolean favorite) {
        this.setFavorite(favorite);
        return this;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getUrlImage() {
        return this.urlImage;
    }

    public Event urlImage(String urlImage) {
        this.setUrlImage(urlImage);
        return this;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public LocalDateTime getDateStart() {
        return this.dateStart;
    }

    public Event dateStart(LocalDateTime dateStart) {
        this.setDateStart(dateStart);
        return this;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return this.dateEnd;
    }

    public Event dateEnd(LocalDateTime dateEnd) {
        this.setDateEnd(dateEnd);
        return this;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Event location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Event ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Event [id=" +
            id +
            ", title=" +
            title +
            ", organization=" +
            organization +
            ", status=" +
            status +
            ", favorite=" +
            favorite +
            ", isPublic=" +
            isPublic +
            ", urlImage=" +
            urlImage +
            ", dateStart=" +
            dateStart +
            ", dateEnd=" +
            dateEnd +
            ", description=" +
            description +
            ", location=" +
            location +
            ", ticket=" +
            ticket +
            "]"
        );
    }
}
