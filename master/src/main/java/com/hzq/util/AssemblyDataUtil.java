package com.hzq.util;

import com.hzq.constant.RedisConstant;
import com.hzq.domain.PurchaseCategory;
import com.hzq.domain.PurchaseProductSku;
import com.hzq.dto.PurchaseInfoDto;
import org.springframework.data.redis.core.StringRedisTemplate;

public class AssemblyDataUtil {
    public static PurchaseInfoDto assemblyPurchaseInfo(PurchaseProductSku productSku, Integer count, PurchaseCategory category, StringRedisTemplate stringRedisTemplate){
        PurchaseInfoDto purchaseInfoDto=new PurchaseInfoDto();
        purchaseInfoDto.setId(productSku.getId());
        purchaseInfoDto.setName(productSku.getPurchaseProduct().getName());
        purchaseInfoDto.setSubtitle(productSku.getPurchaseProduct().getName()+" "+productSku.getAttributeName());
        purchaseInfoDto.setDetail(productSku.getPurchaseProduct().getName()+" "+productSku.getAttributeName());
        purchaseInfoDto.setCategory(category.getName());

        purchaseInfoDto.setGoodRatio(productSku.getPurchaseProduct().getGoodRatio());
        purchaseInfoDto.setMainImage(productSku.getPurchaseProduct().getMainImage());
        purchaseInfoDto.setSubImages(productSku.getPurchaseProduct().getSubImages());
        purchaseInfoDto.setSales(stringRedisTemplate.opsForValue().increment(RedisConstant.SALES_PURCHASEINFO+productSku.getId(),count).intValue());
        purchaseInfoDto.setPrice(productSku.getPrice());
        purchaseInfoDto.setSpellPrice(productSku.getSpellPrice());
        return purchaseInfoDto;
    }
}
