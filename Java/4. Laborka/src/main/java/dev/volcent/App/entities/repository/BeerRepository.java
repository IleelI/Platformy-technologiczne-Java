package dev.volcent.App.entities.repository;

import dev.volcent.App.entities.entity.Beer;
import dev.volcent.App.entities.entity.Brewery;
import dev.volcent.App.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BeerRepository extends JpaRepository<Beer, String> {
    public BeerRepository(EntityManagerFactory emf) {
        super(emf, Beer.class);
    }

    public List<Beer> findAllBeersWithPriceLowerThan(int price) {
        EntityManager em = getEmf().createEntityManager();
        List<Beer> beers = em.createQuery("select p from Beer p where p.price < :price", Beer.class)
                .setParameter("price", price)
                .getResultList();
        em.close();
        return beers;
    }

    public List<Beer> findAllBeersWithBrewery(Brewery brewery) {
        EntityManager em = getEmf().createEntityManager();
        List<Beer> beers = em.createQuery("select b from Beer b where b.brewery = :brewery", Beer.class)
                .setParameter("brewery", brewery)
                .getResultList();
        em.close();
        return beers;
    }
}
