package br.ufrn.bora.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Ticket.
 */
//@Document(collection = "ticket")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    private String id;

    //@Field("price")
    private Float price;

    //@Field("name")
    private String name;

    //@Field("type")
    private String type;

    //@Field("description")
    private String description;

    //@Field("date_start")
    private LocalDateTime dateStart;

    //@Field("date_end")
    private LocalDateTime dateEnd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Ticket id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getPrice() {
        return this.price;
    }

    public Ticket price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public Ticket name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public Ticket type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateStart() {
		return dateStart;
	}

	public void setDateStart(LocalDateTime dateStart) {
		this.dateStart = dateStart;
	}

	public LocalDateTime getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(LocalDateTime dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
	public String toString() {
		return "Ticket [id=" + id + ", price=" + price + ", name=" + name + ", type=" + type + ", description="
				+ description + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + "]";
	}
}
