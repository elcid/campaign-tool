package de.mobile.service.mapper;

import de.mobile.domain.*;
import de.mobile.service.dto.CampaignHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CampaignHistory and its DTO CampaignHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface CampaignHistoryMapper extends EntityMapper<CampaignHistoryDTO, CampaignHistory> {

    @Mapping(source = "campaign.id", target = "campaignId")
    CampaignHistoryDTO toDto(CampaignHistory campaignHistory);

    @Mapping(source = "campaignId", target = "campaign")
    CampaignHistory toEntity(CampaignHistoryDTO campaignHistoryDTO);

    default CampaignHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignHistory campaignHistory = new CampaignHistory();
        campaignHistory.setId(id);
        return campaignHistory;
    }
}
