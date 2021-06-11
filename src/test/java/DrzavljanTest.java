import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrzavljanTest {

    @Test
    public void testInvalidEMSO() {
        assertThrows(InvalidDataException.class, ()-> new Drzavljan("1", "Ivan", "Nikolov",
                20, "Moderna"));
    }

    @Test
    public void testInvalidEMSO2() {
        assertThrows(InvalidDataException.class, () -> new Drzavljan("aaaaaaaaaaaaa", "Ivan", "Nikolov",
                20, "Moderna"));
    }

    @Test
    public void testInvalidName() {
        assertThrows(InvalidDataException.class, () -> new Drzavljan("2111985500138", "Ivan2", "Nikolov",
                20, "Moderna"));
    }

    @Test
    public void testLongName() {
        assertThrows(InvalidDataException.class, () -> new Drzavljan("2111985500138", "Ivan Ivan Ivan", "Nikolov",
                20, "Moderna"));
    }

    @Test
    public void testLongSurname() {
        assertThrows(InvalidDataException.class, () -> new Drzavljan("2111985500138", "Ivan", "Nikolov Nikolov Nikolov",
                20, "Moderna"));
    }

    @Test
    public void testNegativeAge() {
        assertThrows(InvalidDataException.class, () -> new Drzavljan("2111985500138", "Ivan", "Nikolov",
                -1, "Moderna"));
    }

    @Test
    public void testInvalidAge() {
        assertThrows(InvalidDataException.class, () -> new Drzavljan("2111985500138", "Ivan", "Nikolov",
                150, "Moderna"));
    }

}