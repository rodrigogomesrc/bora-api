package br.ufrn.bora.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.ufrn.bora.domain.Exemplo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExemploDTO implements Serializable {

    private String id;

    private String nome;

    @NotNull
    private Integer idade;

    private String teste;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExemploDTO)) {
            return false;
        }

        ExemploDTO exemploDTO = (ExemploDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, exemploDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExemploDTO{" +
            "id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", idade=" + getIdade() +
            ", teste='" + getTeste() + "'" +
            "}";
    }
}
