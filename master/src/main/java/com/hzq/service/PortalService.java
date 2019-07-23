package com.hzq.service;

import com.hzq.domain.BannerImage;
import com.hzq.dto.RepairBookDto;
import com.hzq.dto.SecretaryBookDto;
import com.hzq.dto.WatersuplyDto;
import com.hzq.vo.*;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PortalService {
    List<CommunityNoticeVo> findAllCommunityNoticeVos(Integer pageNum, Integer pageSize);
    List<ProperNoticeVo> findAllProperNoticeVos(Integer pageNum, Integer pageSize);
    List<CommunityNoticeVo> findLatestCommunityNoticeVos(Integer limit);
    List<ProperNoticeVo> findLatestProperNoticeVos(Integer limit);
    String submitRepairInfo(RepairBookDto repairBookDto, BindingResult bindingResult);
    String searchSecretary(SecretaryBookDto bookDto, BindingResult bindingResult);
    List<WaterBrandVo> findAllWaterBrands();
    WaterBookVo bookWaterSuply(WatersuplyDto watersuplyDto, BindingResult bindingResult);
    List<BannerImage> findCarousalImages();
    PortalVo getAllInformation();
    List<RepairBookVo> findAllRepairBookInfo(Integer pageNum, Integer pageSize);
    List<WaterBookVo> findAllWaterBookInfo(Integer pageNum, Integer pageSize);
    List<SecretaryBookVo> findAllSecretaryBookInfo(Integer pageNum, Integer pageSize);
    List<ProductSimpleVo> getRecommendProducts(Integer limit);
}
