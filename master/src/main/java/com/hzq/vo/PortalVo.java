package com.hzq.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hzq.domain.BannerImage;
import com.hzq.domain.Journalism;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PortalVo {
    private List<ProperNoticeVo> properNoticeVos;
    private List<CommunityNoticeVo> communityNoticeVos;
    private List<BannerImage> carousals;
    private List<Journalism> journalisms;
    @JsonProperty(value = "products")
    private List<ProductSimpleVo> simpleVos;
}
