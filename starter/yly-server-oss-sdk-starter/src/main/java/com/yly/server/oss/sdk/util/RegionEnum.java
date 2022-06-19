package com.yly.server.oss.sdk.util;

import com.qiniu.storage.Region;

/**
 * 七牛云地区枚举
 *
 * @author yangtao
 * @date : 2021-07-12 15:46
 */
public enum RegionEnum {
    /**
     * 华东
     */
    HUA_DONG("huadong", Region.huadong()),
    /**
     * 华北
     */
    HUA_BEI("huabei", Region.huabei()),
    /**
     * 华南
     */
    HUA_NAN("huanan", Region.huanan()),
    /**
     * 北美
     */
    BEI_MEI("beimei", Region.beimei()),
    /**
     * 东南亚
     */
    XIN_JIA_PO("xinjiapo", Region.xinjiapo());

    String regionName;
    Region region;

    RegionEnum(String regionName, Region region) {
        this.regionName = regionName;
        this.region = region;
    }

    public static Region getRegion(String regionName) {
        RegionEnum[] values = RegionEnum.values();
        for (RegionEnum regionEnum : values) {
            if (regionName.equals(regionEnum.regionName)) {
                return regionEnum.region;
            }
        }
        return null;
    }

}
