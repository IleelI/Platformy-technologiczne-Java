import dev.volcent.App.entities.Beer;
import dev.volcent.App.repository.BeerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeerRepositoryTest {
    @Test
    @DisplayName("Tests if adding two object with same primary key throws exception.")
    public void addAlreadyExistent() {
        // Given
        BeerRepository beerRepository = new BeerRepository();
        // When
        beerRepository.save(new Beer("Test1", 10));
        // Then:
        assertThrows(IllegalArgumentException.class, () -> {
            beerRepository.save(new Beer("Test1", 10));
        });
    }

    @Test
    @DisplayName("Tests if removing any object from empty collection throws exception.")
    public void removeNonExistent() {
        // Given
        BeerRepository beerRepository = new BeerRepository();
        // When: mage repository is empty
        // Then:
        assertThrows(IllegalArgumentException.class, () -> {
            beerRepository.delete("Tester");
        });
    }

    @Test
    @DisplayName("Tests if finding object in empty collection return empty optional")
    public void getNonExistent() {
        // Given
        BeerRepository beerRepository = new BeerRepository();
        // When: mage repository is empty
        // Then:
        assert beerRepository.find("Test").isEmpty() : true;
    }

    @Test
    @DisplayName("Tests if finding object in empty collection return non empty optional")
    public void getExistent() {
        // Given
        BeerRepository beerRepository = new BeerRepository();
        // When: mage repository is empty
        beerRepository.save(new Beer("Test", 100));
        // Then:
        assert beerRepository.find("Test").isPresent() : true;
    }
}
