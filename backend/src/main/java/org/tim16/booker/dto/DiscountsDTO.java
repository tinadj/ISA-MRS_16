package org.tim16.booker.dto;

public class DiscountsDTO {
    private Integer regularDiscount;
    private Integer bronzeMinPts;
    private Integer bronzeDiscount;
    private Integer silverMinPts;
    private Integer silverDiscount;
    private Integer goldMinPts;
    private Integer goldDiscount;

    public DiscountsDTO() {}

    public DiscountsDTO(Integer regularDiscount, Integer bronzeMinPts, Integer bronzeDiscount, Integer silverMinPts,
                        Integer silverDiscount, Integer goldMinPts, Integer goldDiscount) {
        this.regularDiscount = regularDiscount;
        this.bronzeMinPts = bronzeMinPts;
        this.bronzeDiscount = bronzeDiscount;
        this.silverMinPts = silverMinPts;
        this.silverDiscount = silverDiscount;
        this.goldMinPts = goldMinPts;
        this.goldDiscount = goldDiscount;
    }

    public Integer getRegularDiscount() {
        return regularDiscount;
    }

    public void setRegularDiscount(Integer regularDiscount) {
        this.regularDiscount = regularDiscount;
    }

    public Integer getBronzeMinPts() {
        return bronzeMinPts;
    }

    public void setBronzeMinPts(Integer bronzeMinPts) {
        this.bronzeMinPts = bronzeMinPts;
    }

    public Integer getBronzeDiscount() {
        return bronzeDiscount;
    }

    public void setBronzeDiscount(Integer bronzeDiscount) {
        this.bronzeDiscount = bronzeDiscount;
    }

    public Integer getSilverMinPts() {
        return silverMinPts;
    }

    public void setSilverMinPts(Integer silverMinPts) {
        this.silverMinPts = silverMinPts;
    }

    public Integer getSilverDiscount() {
        return silverDiscount;
    }

    public void setSilverDiscount(Integer silverDiscount) {
        this.silverDiscount = silverDiscount;
    }

    public Integer getGoldMinPts() {
        return goldMinPts;
    }

    public void setGoldMinPts(Integer goldMinPts) {
        this.goldMinPts = goldMinPts;
    }

    public Integer getGoldDiscount() {
        return goldDiscount;
    }

    public void setGoldDiscount(Integer goldDiscount) {
        this.goldDiscount = goldDiscount;
    }
}
