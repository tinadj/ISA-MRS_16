package org.tim16.booker.model.airline;

import javax.persistence.*;

@Entity
@Table(name = "luggage_prices")
public class LuggagePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    private LuggageType type;

    private Float price;

    public LuggagePrice() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LuggageType getType() {
        return type;
    }

    public void setType(LuggageType type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
