package de.mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import de.mobile.domain.enumeration.AccountType;

/**
 * The Personalisation entity.
 */
@ApiModel(description = "The Personalisation entity.")
@Entity
@Table(name = "personalisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personalisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_number")
    private Long customerNumber;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @ManyToOne
    @JsonIgnoreProperties("personalisations")
    private Campaign campaign;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Campaign manager;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public Personalisation customerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public Personalisation userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Personalisation accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public Personalisation campaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Campaign getManager() {
        return manager;
    }

    public Personalisation manager(Campaign campaign) {
        this.manager = campaign;
        return this;
    }

    public void setManager(Campaign campaign) {
        this.manager = campaign;
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
        Personalisation personalisation = (Personalisation) o;
        if (personalisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personalisation{" +
            "id=" + getId() +
            ", customerNumber=" + getCustomerNumber() +
            ", userId=" + getUserId() +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
