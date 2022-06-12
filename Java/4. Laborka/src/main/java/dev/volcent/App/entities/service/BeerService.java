package dev.volcent.App.entities.service;

import dev.volcent.App.entities.entity.Beer;
import dev.volcent.App.entities.entity.Brewery;
import dev.volcent.App.entities.repository.BeerRepository;

import java.util.List;

public class BeerService {

    private final BeerRepository repository;

    public BeerService(BeerRepository repository) {
        this.repository = repository;
    }

    public List<Beer> findAllBeers() {
        return repository.findAll();
    }

    public List<Beer> findAllBeers(int price) {
        return repository.findAllBeersWithPriceLowerThan(price);
    }

    public List<Beer> findAllBeers(Brewery brewery) {
        return repository.findAllBeersWithBrewery(brewery);
    }

    public void delete(Beer beer) {
        repository.delete(beer);
    }

    public void create(Beer beer) {
        repository.add(beer);
    }

    public void listBeers() {
        System.out.println("Beers: ");
        int index = 1;
        for (Beer beer : repository.findAll())  {
            String breweryName = beer.getBrewery() == null ? "no brewery" : beer.getBrewery().getName();
            System.out.println(index + ". Beer " + beer.getName() + ", price: " + beer.getPrice() +  " -> " + breweryName);
            index += 1;
        }
    }
}
