package dev.volcent.App.entities.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Beer {
    @Id
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Brewery brewery;
}
