package de.mobile.service.dto;

import java.io.Serializable;
import java.util.Objects;
import de.mobile.domain.enumeration.AccountType;

/**
 * A DTO for the Personalisation entity.
 */
public class PersonalisationDTO implements Serializable {

    private Long id;

    private Long customerNumber;

    private Long userId;

    private AccountType accountType;

    private Long campaignId;

    private Long managerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long campaignId) {
        this.managerId = campaignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonalisationDTO personalisationDTO = (PersonalisationDTO) o;
        if (personalisationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalisationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalisationDTO{" +
            "id=" + getId() +
            ", customerNumber=" + getCustomerNumber() +
            ", userId=" + getUserId() +
            ", accountType='" + getAccountType() + "'" +
            ", campaign=" + getCampaignId() +
            ", manager=" + getManagerId() +
            "}";
    }
}
