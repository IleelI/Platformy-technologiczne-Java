package dev.volcent.App.repository;

import dev.volcent.App.entities.Beer;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the MageRepository for Mage class
 * We can use it to find, delete and save instances of Mage class
 * To test it we use JUnit.
 */
public class BeerRepository {
    final private Collection<Beer> beerCollection;

    public BeerRepository() {
        this.beerCollection = new ArrayList<>();
    }

    public Optional<Beer> find(String name) {
        Beer foundBeer = null;
        for (Beer beer : beerCollection) {
            if (beer.getName().equals(name)) {
                foundBeer = beer;
                break;
            }
        }
        if (foundBeer == null) {
            return Optional.empty();
        } else {
            return Optional.of(foundBeer);
        }
    }

    public void delete(String name) throws IllegalArgumentException {
        Beer foundBeer = null;
        for (Beer beer : beerCollection) {
            if (beer.getName().equals(name)) {
                foundBeer = beer;
                break;
            }
        }
        if (foundBeer != null) {
            beerCollection.remove(foundBeer);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void save(Beer beer) throws IllegalArgumentException {
        for (Beer currentBeer : beerCollection) {
            if (currentBeer.getName().equals(beer.getName())) {
                throw new IllegalArgumentException();
            }
        }
        beerCollection.add(beer);
    }
}
