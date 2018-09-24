package de.mobile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Content entity.
 */
public class ContentDTO implements Serializable {

    private Long id;

    private String buttonText;

    private String html;

    private String link;

    private String images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentDTO contentDTO = (ContentDTO) o;
        if (contentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + getId() +
            ", buttonText='" + getButtonText() + "'" +
            ", html='" + getHtml() + "'" +
            ", link='" + getLink() + "'" +
            ", images='" + getImages() + "'" +
            "}";
    }
}
