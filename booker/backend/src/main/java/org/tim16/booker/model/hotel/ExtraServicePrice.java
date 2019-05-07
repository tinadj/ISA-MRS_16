package org.tim16.booker.model.hotel;

import javax.persistence.*;

@Entity
@Table(name = "extra_service_prices")
public class ExtraServicePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    private ExtraService type;

    private Float price;

    public ExtraServicePrice() {}

    public ExtraServicePrice(ExtraService type, Float price)
    {
        this.type = type;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExtraService getType() {
        return type;
    }

    public void setType(ExtraService type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
