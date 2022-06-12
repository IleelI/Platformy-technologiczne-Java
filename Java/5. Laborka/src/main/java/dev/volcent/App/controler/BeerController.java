package dev.volcent.App.controler;

import dev.volcent.App.entities.Beer;
import dev.volcent.App.enums.Response;
import dev.volcent.App.repository.BeerRepository;

/**
 * Represents the controller for Mage Repository
 * We can use it to find, delete and save instances of Mage class inside mageRepository
 * To test it we use Mockito.
 */
public class BeerController {
    final private BeerRepository beerRepository;

    public BeerController(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public String find(String name) {
        try {
            Beer foundBeer = beerRepository.find(name).orElseThrow();
            return foundBeer.toString();
        } catch (Exception e) {
            return Response.NOT_FOUND.message;
        }
    }

    public String delete(String name) {
        try {
            beerRepository.delete(name);
            return Response.DONE.message;
        } catch(IllegalArgumentException e) {
            return Response.NOT_FOUND.message;
        }
    }

    public String save(String name, int level) {
        try {
            beerRepository.save(new Beer(name, level));
            return Response.DONE.message;
        } catch (IllegalArgumentException e) {
            return Response.BAD_REQUEST.message;
        }
    }
}
