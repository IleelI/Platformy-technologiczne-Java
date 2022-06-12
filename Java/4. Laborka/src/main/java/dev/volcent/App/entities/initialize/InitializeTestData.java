package dev.volcent.App.entities.initialize;

import dev.volcent.App.entities.entity.Brewery;
import dev.volcent.App.entities.entity.Beer;
import dev.volcent.App.entities.service.BreweryService;
import dev.volcent.App.entities.service.BeerService;

public class InitializeTestData {

    private final BeerService beerService;
    private final BreweryService breweryService;

    public InitializeTestData(BeerService beerService, BreweryService breweryService) {
        this.breweryService = breweryService;
        this.beerService = beerService;
    }

    public void init() {
        Beer m1 = new Beer();
        m1.setName("Brandon");
        m1.setPrice(14);
        Beer m2 = new Beer();
        m2.setName("Rickard");
        m2.setPrice(43);
        Beer m3 = new Beer();
        m3.setName("Wolfur");
        m3.setPrice(64);
        Beer m4 = new Beer();
        m4.setName("Gristled");
        m4.setPrice(100);

        beerService.create(m1);
        beerService.create(m2);
        beerService.create(m3);
        beerService.create(m4);

        Brewery t1 = new Brewery();
        t1.setName("Sadness");
        t1.setWorth(120);
        Brewery t2 = new Brewery();
        t2.setName("Power");
        t2.setWorth(340);
        Brewery t3 = new Brewery();
        t3.setName("Ambition");
        t3.setWorth(200);
        Brewery t4 = new Brewery();
        t4.setName("Beginning");
        t4.setWorth(80);

        breweryService.create(t1);
        breweryService.create(t2);
        breweryService.create(t3);
        breweryService.create(t4);
    }
}

