package dev.volcent.App.entities.repository;

import dev.volcent.App.entities.entity.Brewery;
import dev.volcent.App.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BreweryRepository extends JpaRepository<Brewery, String> {
    public BreweryRepository(EntityManagerFactory emf) {
        super(emf, Brewery.class);
    }

    public List<Brewery> findAllBreweriesWithWorth(int worth) {
        EntityManager em = getEmf().createEntityManager();
        List<Brewery> breweries = em.createQuery("select b from Brewery b where b.worth = :worth", Brewery.class)
                .setParameter("worth", worth)
                .getResultList();
        em.close();
        return breweries;
    }
}
