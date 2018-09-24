package de.mobile.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Content entity.
 */
@ApiModel(description = "The Content entity.")
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "button_text")
    private String buttonText;

    @Column(name = "html")
    private String html;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "images")
    private String images;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getButtonText() {
        return buttonText;
    }

    public Content buttonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHtml() {
        return html;
    }

    public Content html(String html) {
        this.html = html;
        return this;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLink() {
        return link;
    }

    public Content link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImages() {
        return images;
    }

    public Content images(String images) {
        this.images = images;
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        if (content.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), content.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", buttonText='" + getButtonText() + "'" +
            ", html='" + getHtml() + "'" +
            ", link='" + getLink() + "'" +
            ", images='" + getImages() + "'" +
            "}";
    }
}
