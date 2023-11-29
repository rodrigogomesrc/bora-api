package br.ufrn.bora.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufrn.bora.domain.Ticket} entity.
 */
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketDTO implements Serializable {

    private String id;

    private Float price;

    private String name;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
            "id='" + getId() + "'" +
            ", price=" + getPrice() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
