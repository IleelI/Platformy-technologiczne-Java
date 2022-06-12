package dev.volcent.App.entities.service;

import dev.volcent.App.entities.entity.Brewery;
import dev.volcent.App.entities.repository.BreweryRepository;

import java.util.List;

public class BreweryService {

    private final BreweryRepository repository;

    public BreweryService(BreweryRepository repository) {
        this.repository = repository;
    }

    public List<Brewery> findAllBreweries() {
        return repository.findAll();
    }

    public List<Brewery> findAllBreweries(int worth) {
        return repository.findAllBreweriesWithWorth(worth);
    }

    public void delete(Brewery brewery) {
        repository.delete(brewery);
    }

    public void create(Brewery brewery) {
        repository.add(brewery);
    }

    public void listBreweries() {
        System.out.println("Breweries: ");
        int index = 1;
        for (Brewery brewery : repository.findAll()) {
            System.out.println(index + ". " + brewery.getName() + " brewery.");
            index += 1;
        }
    }
}
