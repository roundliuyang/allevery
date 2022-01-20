package com.yly.bean.convert.mapstruct.dto;

public class TransactionDTO {

    private String uuid;
    /**
     * 这里棘手的部分是将BigDecimal 的美元总额转换为Long totalInCents。
     */
    private Long totalInCents;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(Long totalInCents) {
        this.totalInCents = totalInCents;
    }
}
