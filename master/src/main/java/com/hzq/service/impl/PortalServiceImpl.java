package com.hzq.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hzq.constant.RedisConstant;
import com.hzq.domain.*;
import com.hzq.dto.PurchaseInfoDto;
import com.hzq.dto.RepairBookDto;
import com.hzq.dto.SecretaryBookDto;
import com.hzq.dto.WatersuplyDto;
import com.hzq.exception.ParamException;
import com.hzq.mappers.*;
import com.hzq.service.PortalService;
import com.hzq.util.AuthenticationInfoUtil;
import com.hzq.util.JsonSerializableUtil;
import com.hzq.util.ParamValidatorUtil;
import com.hzq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class PortalServiceImpl implements PortalService {

    @Autowired
    private CommunityNoticeMapper communityNoticeMapper;

    @Autowired
    private ProperNoticeMapper properNoticeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RepairBookMapper repairBookMapper;

    @Autowired
    private SecretaryBookMapper secretaryBookMapper;

    @Autowired
    private WaterBrandMapper waterBrandMapper;

    @Autowired
    private WatersuplyBookMapper watersuplyBookMapper;

    @Autowired
    private WatersuplyDetailsMapper watersuplyDetailsMapper;

    @Autowired
    private BannerImageMapper bannerImageMapper;



    @Override
    public List<CommunityNoticeVo> findAllCommunityNoticeVos(Integer pageNum, Integer pageSize){
        List<CommunityNoticeVo> communityNoticeVos= Lists.newArrayList();
        try {
            com.hzq.domain.User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            PageHelper.startPage(pageNum,pageSize);
            List<CommunityNotice> communityNotices=communityNoticeMapper.selectByCommunityId(user.getCommunityId());
            communityNotices.stream().forEach(communityNotice -> {
                CommunityNoticeVo vo=new CommunityNoticeVo();
                BeanUtils.copyProperties(communityNotice,vo);
                communityNoticeVos.add(vo);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return communityNoticeVos;
    }

    @Override
    public List<ProperNoticeVo> findAllProperNoticeVos(Integer pageNum, Integer pageSize) {
        List<ProperNoticeVo> properNoticeVos = Lists.newArrayList();
        try {
            com.hzq.domain.User user = AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            PageHelper.startPage(pageNum, pageSize);
            List<ProperNotice> properNotices = properNoticeMapper.selectByUserId(user.getId());
            properNotices.stream().forEach(properNotice -> {
                ProperNoticeVo vo = new ProperNoticeVo();
                BeanUtils.copyProperties(properNotice, vo);
                properNoticeVos.add(vo);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properNoticeVos;
    }

    //最新的记录,用redis来实现,提高效率,数据库加redis
    @Override
    public List<CommunityNoticeVo> findLatestCommunityNoticeVos(Integer limit) {
        List<CommunityNoticeVo> communityNoticeVos=Lists.newArrayList();
        try {
            com.hzq.domain.User user =AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            Set<String> keys = stringRedisTemplate.opsForZSet().reverseRange(RedisConstant.COMMUNITY_NOTICE_ORDER + user.getCommunityId(), 0, limit - 1);
            keys.stream().forEach(key->{
                String communityNoticeJson = (String) stringRedisTemplate.opsForHash().get(RedisConstant.COMMUNITY_NOTICES+user.getCommunityId(), key);
                CommunityNotice communityNotice = JsonSerializableUtil.string2Obj(communityNoticeJson, CommunityNotice.class);
                CommunityNoticeVo communityNoticeVo=new CommunityNoticeVo();
                BeanUtils.copyProperties(communityNotice,communityNoticeVo);
                communityNoticeVos.add(communityNoticeVo);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return communityNoticeVos;
    }

    @Override
    public List<ProperNoticeVo> findLatestProperNoticeVos(Integer limit) {
        List<ProperNoticeVo> properNoticeVos=Lists.newArrayList();
        try {
            com.hzq.domain.User user = AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            Set<String> keys = stringRedisTemplate.opsForZSet().reverseRange(RedisConstant.PROPER_NOTICE_ORDER + user.getId(), 0, limit - 1);
            keys.stream().forEach(key->{
                String properNoticeJson = (String) stringRedisTemplate.opsForHash().get(RedisConstant.PROPER_NOTICES+user.getId(), key);
                ProperNotice properNotice = JsonSerializableUtil.string2Obj(properNoticeJson, ProperNotice.class);
                ProperNoticeVo properNoticeVo=new ProperNoticeVo();
                BeanUtils.copyProperties(properNotice,properNoticeVo);
                properNoticeVos.add(properNoticeVo);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properNoticeVos;
    }

    @Override
    public String submitRepairInfo(RepairBookDto repairBookDto, BindingResult bindingResult) {
        ParamValidatorUtil.validateBindingResult(bindingResult);
        try {
            RepairBook repairBook=new RepairBook();
            BeanUtils.copyProperties(repairBookDto,repairBook);
            User user = AuthenticationInfoUtil.getUser(userMapper, stringRedisTemplate);
            repairBook.setCreateTime(new Date());
            repairBook.setUpdateTime(new Date());
            repairBook.setNickname(user.getNickname());
            repairBook.setAvatar(user.getAvatar());
            repairBookMapper.insert(repairBook);
            return "立即报修提交成功";
        } catch (Exception e) {
            log.error("立即报修提交失败");
        }
        return null;
    }

    @Override
    public String searchSecretary(SecretaryBookDto bookDto, BindingResult bindingResult) {
        ParamValidatorUtil.validateBindingResult(bindingResult);
        try {
            SecretaryBook secretaryBook=new SecretaryBook();
            BeanUtils.copyProperties(bookDto,secretaryBook);
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            secretaryBook.setCreateTime(new Date());
            secretaryBook.setNickname(user.getNickname());
            secretaryBook.setAvatar(user.getAvatar());
            secretaryBookMapper.insert(secretaryBook);
            return "找书记提交成功";
        } catch (Exception e) {
            log.error("找书记提交失败");
        }
        return null;
    }

    @Override
    public List<WaterBrandVo> findAllWaterBrands() {
        List<WaterBrandVo> waterBrandVoList=Lists.newArrayList();
        try {
            List<WaterBrand> waterBrands=waterBrandMapper.selectByUserId(AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate).getId());
            waterBrands.stream().parallel().forEach(waterBrand -> {
                WaterBrandVo waterBrandVo=new WaterBrandVo();
                BeanUtils.copyProperties(waterBrand,waterBrandVo);
                waterBrandVoList.add(waterBrandVo);
            });
            return waterBrandVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WaterBookVo bookWaterSuply(WatersuplyDto watersuplyDto, BindingResult bindingResult) {
        ParamValidatorUtil.validateBindingResult(bindingResult);

        try {
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            WaterBookVo waterBookVo=new WaterBookVo();

            WatersuplyBook watersuplyBook=new WatersuplyBook();
            BeanUtils.copyProperties(watersuplyDto,watersuplyBook);
            watersuplyBook.setNickname(user.getNickname());
            watersuplyBook.setAvatar(user.getAvatar());
            watersuplyBook.setCreateTime(new Date());
            watersuplyBook.setUpdateTime(new Date());
            watersuplyBookMapper.insertSelective(watersuplyBook);

            List<WatersuplyDetails> detailsList = watersuplyDto.getDetailsList();

            List<WatersuplyDetails> watersuplyDetails=Lists.newArrayList();

            if (CollectionUtils.isEmpty(detailsList)){
                throw new ParamException("所购水的数量不正确");
            }

            detailsList.stream().forEach(detail->{
                WatersuplyDetails details=new WatersuplyDetails();
                BeanUtils.copyProperties(detail,details);
                details.setSuplyId(watersuplyBook.getId());
                watersuplyDetailsMapper.insert(details);
                watersuplyDetails.add(details);
            });
            BeanUtils.copyProperties(watersuplyBook,waterBookVo);
            waterBookVo.setDetailsList(watersuplyDetails);
            return waterBookVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BannerImage> findCarousalImages() {
        List<BannerImage> carousalImages = bannerImageMapper.getCarousalImage();
        return carousalImages;
    }

    @Override
    public PortalVo getAllInformation() {
        PortalVo portalVo=new PortalVo();
        portalVo.setCarousals(findCarousalImages());
        portalVo.setCommunityNoticeVos(findLatestCommunityNoticeVos(3));
        portalVo.setProperNoticeVos(findLatestProperNoticeVos(3));
        portalVo.setJournalisms(null);
        portalVo.setSimpleVos(getRecommendProducts(9));
        return portalVo;
    }

    @Override
    public List<RepairBookVo> findAllRepairBookInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<RepairBookVo> repairBookVos=Lists.newArrayList();
        try {
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            List<RepairBook> repairBooks=repairBookMapper.selectByNickname(user.getNickname());
            repairBooks.stream().sorted(Comparator.comparing(RepairBook::getUpdateTime).reversed())
                    .forEach(repairBook -> {
                        RepairBookVo repairBookVo=new RepairBookVo();
                        BeanUtils.copyProperties(repairBook,repairBookVo);
                        repairBookVos.add(repairBookVo);
                    });
            return repairBookVos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<WaterBookVo> findAllWaterBookInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<WaterBookVo> waterBookVos=Lists.newArrayList();
        try {
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            List<WatersuplyBook> watersuplyBooks=watersuplyBookMapper.selectAllByNickname(user.getNickname());
            watersuplyBooks.stream().sorted(Comparator.comparing(WatersuplyBook::getUpdateTime).reversed())
                    .forEach(watersuplyBook -> {
                        WaterBookVo waterBookVo=new WaterBookVo();
                        if (CollectionUtils.isEmpty(waterBookVo.getDetailsList())){
                            List<WatersuplyDetails> watersuplyDetails=Lists.newArrayList();
                            waterBookVo.setDetailsList(watersuplyDetails);
                        }
                        watersuplyBook.getDetails().parallelStream().forEach(watersuplyDetails -> {
                            waterBookVo.getDetailsList().add(watersuplyDetails);
                        });
                        BeanUtils.copyProperties(watersuplyBook,waterBookVo);

                        waterBookVos.add(waterBookVo);
                    });
            return waterBookVos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SecretaryBookVo> findAllSecretaryBookInfo(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<SecretaryBookVo> secretaryBookVos=Lists.newArrayList();
        try {
            User user=AuthenticationInfoUtil.getUser(userMapper,stringRedisTemplate);
            List<SecretaryBook> secretaryBooks=secretaryBookMapper.selectByNickname(user.getNickname());
            secretaryBooks.stream().sorted(Comparator.comparing(SecretaryBook::getCreateTime).reversed())
                    .forEach(secretaryBook -> {
                        SecretaryBookVo secretaryBookVo=new SecretaryBookVo();

                        BeanUtils.copyProperties(secretaryBook,secretaryBookVo);

                        secretaryBookVos.add(secretaryBookVo);
                    });
            return secretaryBookVos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProductSimpleVo> getRecommendProducts(Integer limit) {
        List<ProductSimpleVo> simpleVos=Lists.newArrayList();
//        List<PurchaseInfoVo> purchaseInfo = purchaseInfoService.getPurchaseInfo();
//        purchaseInfo.stream().sorted(Comparator.comparing(PurchaseInfoVo::getSales).reversed())
//                .limit(limit).map(PurchaseInfoVo::getName).forEach(subtitle->{
//            PurchaseInfoDto purchaseInfoDto = purchaseInfoRepository.findByDetail(subtitle);
//            if (purchaseInfoDto != null) {
//                ProductSimpleVo productSimpleVo=new ProductSimpleVo();
//
//                BeanUtils.copyProperties(purchaseInfoDto,productSimpleVo);
//
//                simpleVos.add(productSimpleVo);
//            }
//            });
        return simpleVos;
    }


}
