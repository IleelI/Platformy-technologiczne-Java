import dev.volcent.App.controler.BeerController;
import dev.volcent.App.entities.Beer;
import dev.volcent.App.enums.Response;
import dev.volcent.App.repository.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class BeerControllerTest {
    private BeerRepository beerRepository;
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        beerRepository = Mockito.mock(BeerRepository.class);
        beerController = new BeerController(beerRepository);
    }

    @Test
    public void deleteNonExistent() {
        doThrow(IllegalArgumentException.class)
                .when(beerRepository)
                .delete(anyString());
        Assertions.assertEquals(Response.NOT_FOUND.message, beerController.delete("test"));
    }

    @Test
    public void deleteExistent() {
        Assertions.assertEquals(Response.DONE.message, beerController.delete("Test"));
    }

    @Test
    public void findNonExistent() {
        when(beerRepository.find(anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals(Response.NOT_FOUND.message, beerController.find(anyString()));
    }

    @Test
    public void findExistent() {
        String beerName = "albrecht";
        int power = 9;
        Beer expected = new Beer(beerName, power);

        when(beerRepository.find(beerName)).thenReturn(Optional.of(expected));

        String actual = beerController.find(beerName);
        Assertions.assertEquals(expected.toString(), actual);
    }

    @Test
    public void saveNonExistent() {
        Assertions.assertDoesNotThrow(() -> beerController.save("Tester", 123));
        Assertions.assertEquals(Response.DONE.message, beerController.save("Tester", 123));
    }

    @Test
    public void saveExistent() {
        doThrow(IllegalArgumentException.class)
                .when(beerRepository)
                .save(any(Beer.class));
        Assertions.assertEquals(Response.BAD_REQUEST.message, beerController.save("Tom", 15));
    }
}
