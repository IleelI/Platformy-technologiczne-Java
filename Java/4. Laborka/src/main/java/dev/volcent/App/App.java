package dev.volcent.App;

import dev.volcent.App.entities.entity.Beer;
import dev.volcent.App.entities.entity.Brewery;
import dev.volcent.App.entities.initialize.InitializeTestData;
import dev.volcent.App.entities.repository.BeerRepository;
import dev.volcent.App.entities.repository.BreweryRepository;
import dev.volcent.App.entities.service.BeerService;
import dev.volcent.App.entities.service.BreweryService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class App {
    final public static String persistenceUnitName = "bPu";
    public static void main(String[] args) {
        EntityManagerFactory enf = Persistence.createEntityManagerFactory(persistenceUnitName);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bPu");
        BeerService beerService = new BeerService(new BeerRepository(emf));
        BreweryService breweryService = new BreweryService(new BreweryRepository(emf));

        InitializeTestData initializeTestData = new InitializeTestData(beerService, breweryService);
        initializeTestData.init();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("addBeer | addBrewery | removeBeer | removeBrewery | listBeers | listBreweries | listAll | askQuery | exit");
            System.out.println("Command: ");
            String command = scanner.next();
            switch (command) {
                case "addBeer" -> {
                    Beer newBeer = new Beer();
                    System.out.println("Enter name: ");
                    String name = scanner.next();
                    newBeer.setName(name);
                    System.out.println("Enter price: ");
                    String price = scanner.next();
                    newBeer.setPrice(Integer.parseInt(price));
                    System.out.println("Select brewery (to not choose enter -1)");
                    List<Brewery> breweries = breweryService.findAllBreweries();
                    System.out.println("-1. No brewery");
                    breweryService.listBreweries();
                    int towerIndex = scanner.nextInt();
                    if (towerIndex != -1)
                        newBeer.setBrewery(breweries.get(towerIndex - 1));
                    beerService.create(newBeer);
                }
                case "addBrewery" -> {
                    Brewery brewery = new Brewery();
                    System.out.println("Enter brewery name: ");
                    String name = scanner.next();
                    brewery.setName(name);
                    System.out.println("Enter brewery worth: ");
                    int worth = scanner.nextInt();
                    brewery.setWorth(worth);
                    breweryService.create(brewery);
                }
                case "removeBeer" -> {
                    System.out.println("Select beer to remove");
                    beerService.listBeers();
                    int beerIdx = scanner.nextInt() - 1;
                    Beer beerToDelete = beerService.findAllBeers().get(beerIdx );
                    beerService.delete(beerToDelete);
                }
                case "removeBrewery" -> {
                    System.out.println("Select brewery to remove");
                    breweryService.listBreweries();
                    int breweryIdx = scanner.nextInt() - 1;
                    Brewery breweryToDelete = breweryService.findAllBreweries().get(breweryIdx);
                    breweryService.delete(breweryToDelete);
                }
                case "listBeers" -> {
                    beerService.listBeers();
                }
                case "listBreweries" -> {
                    breweryService.listBreweries();
                }
                case "listAll" -> {
                    beerService.listBeers();
                    breweryService.listBreweries();
                }
                case "askQuery" -> {
                    System.out.println("1. Get beers with price lower than | 2. Get beers from brewery with price higher than ");
                    while (true) {
                        int queryIdx = scanner.nextInt();
                        if (queryIdx == 1) {
                            System.out.println("Enter price");
                            int price = scanner.nextInt();
                            List<Beer> results = beerService.findAllBeers(price);
                            for (Beer beer : results) {
                                System.out.println(beer.getName() + " -> " + beer.getPrice());
                            }
                            break;
                        } else if (queryIdx == 2) {
                            System.out.println("Select brewery");
                            breweryService.listBreweries();
                            int breweryIdx = scanner.nextInt();
                            List<Brewery> breweries = breweryService.findAllBreweries();
                            Brewery selectedBrewery = breweries.get(breweryIdx - 1);
                            System.out.println("Enter price");
                            int enteredPrice = scanner.nextInt();
                            List<Beer> breweryBeers = beerService.findAllBeers(selectedBrewery);
                            for (Beer breweryBeer : breweryBeers) {
                                if (breweryBeer.getPrice() > enteredPrice) {
                                    System.out.println(breweryBeer.getName());
                                }
                            }
                            break;
                        }
                    }
                }
                case "exit" -> {
                    running = false;
                }
                default -> {
                    System.out.println("addBeer | addBrewery | removeBeer | removeBrewery | listBeers | listBreweries | listAll | askQuery | exit");
                }
            }
        }
        enf.close();
    }
}
