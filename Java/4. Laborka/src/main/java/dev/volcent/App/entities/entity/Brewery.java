package dev.volcent.App.entities.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class Brewery {
    @Id
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int worth;

    @Getter
    @Setter
    @OneToMany(mappedBy = "brewery", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Beer> beerList;
}
