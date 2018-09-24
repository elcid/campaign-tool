package de.mobile.service.mapper;

import de.mobile.domain.*;
import de.mobile.service.dto.PersonalisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Personalisation and its DTO PersonalisationDTO.
 */
@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface PersonalisationMapper extends EntityMapper<PersonalisationDTO, Personalisation> {

    @Mapping(source = "campaign.id", target = "campaignId")
    @Mapping(source = "manager.id", target = "managerId")
    PersonalisationDTO toDto(Personalisation personalisation);

    @Mapping(source = "campaignId", target = "campaign")
    @Mapping(source = "managerId", target = "manager")
    Personalisation toEntity(PersonalisationDTO personalisationDTO);

    default Personalisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Personalisation personalisation = new Personalisation();
        personalisation.setId(id);
        return personalisation;
    }
}
