package br.ufrn.bora.domain;

//import br.ufrn.bora.config.Constants;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
//import org.hibernate.validator.constraints.Length;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A user.
 */
//@org.springframework.data.mongodb.core.mapping.Document(collection = "jhi_user")
public class User extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Id
    private String id;

    //@NotNull
    //@Pattern(regexp = Constants.LOGIN_REGEX)
    //@Size(min = 1, max = 50)
    //@Indexed
    private String login;

    //@JsonIgnore
    //@NotNull
    //@Size(min = 60, max = 60)
    private String password;

    //@Size(max = 100)
    //@Field("name")
    private String name;

    //@Email
    //@Size(min = 5, max = 254)
    //@Indexed
    private String email;

    //@Length(max = 13, message = "Tamanho máximo de 13 caracteres. Ex: 5584999999999")
    private String telephone;

    //@Field("date_of_birth")
    private LocalDate dateOfBirth;

    private boolean activated = false;

    //@Size(min = 2, max = 10)
    //@Field("lang_key")
    private String langKey;

    //@Size(max = 256)
    //@Field("image_url")
    private String imageUrl;

    //@Size(max = 20)
    //@Field("activation_key")
    //@JsonIgnore
    private String activationKey;

    //@Size(max = 20)
    //@Field("reset_key")
    //@JsonIgnore
    private String resetKey;

    //@Field("reset_date")
    private Instant resetDate = null;

    //@JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
