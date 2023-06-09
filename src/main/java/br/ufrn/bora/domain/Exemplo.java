package br.ufrn.bora.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
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

    @NotNull
    @Field("idade")
    private Integer idade;

    @Field("teste")
    private String teste;

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

    public Integer getIdade() {
        return this.idade;
    }

    public Exemplo idade(Integer idade) {
        this.setIdade(idade);
        return this;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getTeste() {
        return this.teste;
    }

    public Exemplo teste(String teste) {
        this.setTeste(teste);
        return this;
    }

    public void setTeste(String teste) {
        this.teste = teste;
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
            ", idade=" + getIdade() +
            ", teste='" + getTeste() + "'" +
            "}";
    }
}
