package br.ufrn.bora.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Exemplo.
 */
@Document(collection = "exemplo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Exemplo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nome")
    private String nome;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Exemplo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Exemplo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exemplo)) {
            return false;
        }
        return id != null && id.equals(((Exemplo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exemplo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
