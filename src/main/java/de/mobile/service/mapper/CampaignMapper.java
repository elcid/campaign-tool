package de.mobile.service.mapper;

import de.mobile.domain.*;
import de.mobile.service.dto.CampaignDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Campaign and its DTO CampaignDTO.
 */
@Mapper(componentModel = "spring", uses = {ContentMapper.class})
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {

    @Mapping(source = "content.id", target = "contentId")
    CampaignDTO toDto(Campaign campaign);

    @Mapping(source = "contentId", target = "content")
    @Mapping(target = "personalisations", ignore = true)
    Campaign toEntity(CampaignDTO campaignDTO);

    default Campaign fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campaign campaign = new Campaign();
        campaign.setId(id);
        return campaign;
    }
}
