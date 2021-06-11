import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

class SeznamiUVTest {
    private SeznamiUV uv;

    public SeznamiUVTest() {
    }

    @Before
    public void setUp() {
        uv = new SeznamiUV();
    }

    @BeforeEach
    public void setUv() {
        uv = new SeznamiUV();
    }

    public void addPerson() {
        assertEquals("add> EMSO: ", uv.processInput("add"));
        assertEquals("add> NAME: ", uv.processInput("3105940500232"));
        assertEquals("add> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals("add> AGE: ", uv.processInput("Novak"));
        assertEquals("add> VACCINE: ", uv.processInput("81"));
        assertEquals("OK", uv.processInput("Pfeizer"));
    }

    public void addPersonArguments(String emso, String ime, String priimek, String starost,
                                   String cepivo, String expected) {
        assertEquals("add> EMSO: ", uv.processInput("add"));
        assertEquals("add> NAME: ", uv.processInput(emso));
        assertEquals("add> SURNAME: ", uv.processInput(ime));
        assertEquals("add> AGE: ", uv.processInput(priimek));
        assertEquals("add> VACCINE: ", uv.processInput(starost));
        assertEquals(expected, uv.processInput(cepivo));
    }

    @Test
    public void testAddCorrect() {
        addPerson();
    }

    @Test
    public void testAddMultiple() {
        addPerson();
        addPersonArguments("2811940505322", "Robert", "Horvat",
                "21", "Moderna", "OK");
    }

    @Test
    public void testAddDuplicateName() {
        addPersonArguments("2811940505322", "Robert", "Horvat",
                "21", "Moderna", "OK");
        addPersonArguments("1111940505322", "Robert", "Horvat",
                "21", "AstraZeneca", ">> Person already exists");
    }

    @Test
    public void testAddDuplicateEMSO() {
        addPersonArguments("2811940505322", "Robert", "Horvat",
                "21", "Moderna", "OK");
        addPersonArguments("2811940505322", "Janez", "Novak",
                "21", "Johnson", ">> Person already exists");
    }

    @Test
    public void testAddInvalidData() {
        addPersonArguments("3105940500232", "Name1", "Novak",
                "81", "Pfeizer", ">> Invalid input data");
    }

    @Test
    public void testAddInvalidData2() {
        assertEquals("add> EMSO: ", uv.processInput("add"));
        assertEquals(">> Invalid input data", uv.processInput("add"));
    }


    @Test
    public void testAddInvalidDataLongName() {
        assertEquals("add> EMSO: ", uv.processInput("add"));
        assertEquals("add> NAME: ", uv.processInput("2811940505322"));
        assertEquals(">> Invalid input data", uv.processInput("Robert Robert Robert"));
    }

    @Test
    public void testAddInvalidDataLongSurname() {
        assertEquals("add> EMSO: ", uv.processInput("add"));
        assertEquals("add> NAME: ", uv.processInput("2811940505322"));
        assertEquals("add> SURNAME: ", uv.processInput("Robert"));
        assertEquals(">> Invalid input data", uv.processInput("Horvat Horvat Horvat"));
    }

    @Test
    public void testAddTwoSurnames() {
        addPersonArguments("2811940505322", "Robert", "Horvat Novak",
                "21", "Moderna", "OK");
    }

    @Test
    public void testAddInvalidVaccine() {
        addPersonArguments("2811940505322", "Robert", "Horvat",
                "21", "VACCINE", ">> Invalid input data");
    }

    @Test
    public void testAddPersonAlreadyExists() {
        addPerson();
        assertEquals("add> EMSO: ", uv.processInput("add"));
        assertEquals("add> NAME: ", uv.processInput("3105940500232"));
        assertEquals("add> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals("add> AGE: ", uv.processInput("Novak"));
        assertEquals("add> VACCINE: ", uv.processInput("81"));
        assertEquals(">> Person already exists", uv.processInput("Pfeizer"));
    }

    @Test
    public void testRemovePersonNameOK() {
        addPerson();
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals("remove> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals("OK", uv.processInput("Novak"));
    }

    @Test
    public void testRemovePersonTwoSurnames() {
        addPersonArguments("2811940505322", "Robert", "Horvat Novak",
                "21", "Moderna", "OK");
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals("remove> SURNAME: ", uv.processInput("Robert"));
        assertEquals("OK", uv.processInput("Horvat Novak"));
    }

    @Test
    public void testRemovePersonInvalidDataLongName() {
        addPerson();
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals(">> Invalid input data", uv.processInput("Robert Robert Robert"));
    }

    @Test
    public void testRemovePersonInvalidDataLongSurname() {
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals("remove> SURNAME: ", uv.processInput("Robert"));
        assertEquals(">> Invalid input data", uv.processInput("Horvat Novak Kovаč"));
    }

    @Test
    public void testRemoveInvalidData() {
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals(">> Invalid argument", uv.processInput("remove"));
    }

    @Test
    public void testRemovePersonEMSOOK() {
        addPerson();
        assertEquals("OK", uv.processInput("remove 3105940500232"));
    }

    @Test
    public void testRemovePersonEMSOInvalidData1() {
        addPerson();
        assertEquals(">> Invalid input data", uv.processInput("remove 12"));
    }

    @Test
    public void testInvalidCommand() {
        assertEquals(">> Invalid command", uv.processInput("Hello"));
    }

    @Test
    public void testEmptyCommand() {
        assertEquals(">> Invalid command", uv.processInput(""));
    }

    /*
    @Test
    public void removePersonEMSOInvalidData2() {
        addPerson();
        assertEquals("Invalid input data", uv.processInput("remove  "));
    }
     */

    @Test
    public void testRemovePersonNameInvalidData() {
        addPerson();
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals("remove> SURNAME: ", uv.processInput("Janez22 Albert"));
        assertEquals(">> Invalid input data", uv.processInput("Novak"));
    }

    @Test
    public void testRemovePersonEMSODoesNotExist() {
        assertEquals(">> Person does not exist", uv.processInput("remove 3105940500232"));
    }

    @Test
    public void testRemovePersonNameDoesNotExist() {
        assertEquals("remove> NAME: ", uv.processInput("remove"));
        assertEquals("remove> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals(">> Person does not exist", uv.processInput("Novak"));
    }

    @Test
    public void testSearchEMSOExists() {
        //assertEquals();
        addPerson();
        assertEquals("3105940500232\tNovak, Janez Albert\t81\tPfeizer",
                uv.processInput("search 3105940500232"));
    }

    @Test
    public void testSearchNameExists() {
        addPerson();
        assertEquals("search> NAME: ", uv.processInput("search"));
        assertEquals("search> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals("3105940500232\tNovak, Janez Albert\t81\tPfeizer",
                uv.processInput("Novak"));
    }

    @Test
    public void testSearchTwoSurnames() {
        addPersonArguments("2811940505322", "Robert", "Horvat Novak",
                "21", "Moderna", "OK");
        assertEquals("search> NAME: ", uv.processInput("search"));
        assertEquals("search> SURNAME: ", uv.processInput("Robert"));
        assertEquals("2811940505322\tHorvat Novak, Robert\t21\tModerna", uv.processInput("Horvat Novak"));
    }

    @Test
    public void testSearchEMSONotExist() {
        assertEquals(">> Person does not exist",
                uv.processInput("search 3105940500232"));
    }

    @Test
    public void testSearchNameNotExist() {
        assertEquals("search> NAME: ", uv.processInput("search"));
        assertEquals("search> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals(">> Person does not exist",
                uv.processInput("Novak"));
    }

    @Test
    public void testSearchInvalidDataEMSO() {
        addPerson();
        assertEquals(">> Invalid input data",
                uv.processInput("search 3105940500232a"));
    }

    @Test
    public void testSearchInvalidDataSurname() {
        addPerson();
        assertEquals("search> NAME: ", uv.processInput("search"));
        assertEquals("search> SURNAME: ", uv.processInput("Janez Albert"));
        assertEquals(">> Invalid input data",
                uv.processInput("Novak Test Test Test"));
    }

    @Test
    public void testSearchInvalidDataName() {
        addPerson();
        assertEquals("search> NAME: ", uv.processInput("search"));
        assertEquals(">> Invalid input data", uv.processInput("Janez Janez Janez"));
    }

    @Test
    public void testSearchNumberInName() {
        assertEquals(">> Invalid input data", uv.processInput("search 123 1123"));
    }

    @Test
    public void addAndSave() {
        addPerson();
        addPersonArguments("2811940505322", "Robert", "Horvat",
                "21", "Moderna", "OK");
        addPersonArguments("1603990500192", "Blaž", "Коvač",
                "35", "Pfeizer", "OK");
        assertEquals("OK", uv.processInput("save datoteka.bin"));
    }

    @Test
    public void testSaveOK() {
        addAndSave();
    }

    @Test
    public void testRestoreOK() {
        addAndSave();
        assertEquals("OK", uv.processInput("restore datoteka.bin"));
    }

    @Test
    public void testPrint() {
        addPersonArguments("1310965505091", "Marija", "Svet",
                "56", "Moderna", "OK");
        addPersonArguments("2111985500138", "Jan Vid", "Anderlič",
                "36", "Johnson", "OK");
        addPersonArguments("3105970500232", "Boris", "Novak",
                "51", "AstraZeneca", "OK");

        String printOutput = ">> Number of Persons: 3\n" +
                "2111985500138\tAnderlič, Jan Vid\t36\tJohnson\n"+
                "3105970500232\tNovak, Boris\t51\tAstraZeneca\n" +
                "1310965505091\tSvet, Marija\t56\tModerna";

        assertEquals(printOutput, uv.processInput("print"));
    }

    @Test
    public void testPrintOne() {
        addPersonArguments("1310965505091", "Marija", "Svet",
                "56", "Moderna", "OK");

        String output = ">> Number of Persons: 1\n" +
                "1310965505091\tSvet, Marija\t56\tModerna";
        assertEquals(output, uv.processInput("print"));
    }

    @Test
    public void testPrintZero() {
                    addPersonArguments("1310965505091", "Marija", "Svet",
                    "56", "Moderna", "OK");

            String output = ">> Number of Persons: 1\n" +
                    "1310965505091\tSvet, Marija\t56\tModerna";
            assertEquals(output, uv.processInput("print"));
        }


    @Test
    public void testCount() {
        addPersonArguments("2811940505322", "Robert", "Horvat",
                "21", "Moderna", "OK");
        addPerson();
        assertEquals(">> No. of Persons: 2", uv.processInput("count"));
    }

    @Test
    public void testResetYes() {
        addPerson();
        assertEquals("reset> Are you sure (y|n): ", uv.processInput("reset"));
        assertEquals("OK", uv.processInput("y"));
    }

    @Test
    public void testResetNo() {
        addPerson();
        assertEquals("reset> Are you sure (y|n): ", uv.processInput("reset"));
        assertEquals("OK", uv.processInput("n"));
    }

    @Test
    public void testResetNoAnswer() {
        addPerson();
        assertEquals("reset> Are you sure (y|n): ", uv.processInput("reset"));
        assertEquals("reset> Are you sure (y|n): ", uv.processInput("test"));
        assertEquals("reset> Are you sure (y|n): ", uv.processInput(""));
    }



    @Test
    public void testSaveNoFile() {
        addPerson();
        assertEquals("Error: please specify a file name", uv.processInput("save"));
    }

    @Test
    public void testRestoreNoFile() {
        assertEquals("Error: please specify a file name", uv.processInput("restore"));
    }

    @Test
    public void resetInvalidArgument() {
        assertEquals(">> Invalid argument", uv.processInput("reset test"));
    }

    @Test
    public void testEasyMockSaveNoMemory() {

        SeznamiUV seznamiUVMock = EasyMock.createMock(SeznamiUV.class);
        expect(seznamiUVMock.processInput("save file.bin")).andThrow(new OutOfMemoryError());
        replay(seznamiUVMock);
        OutOfMemoryError error = assertThrows(OutOfMemoryError.class, () -> {
           seznamiUVMock.processInput("save file.bin");
        });
        verify(seznamiUVMock);
    }


    @Test
    public void testEasyMockSaveIO() throws IOException {
        SeznamiUV seznamiUVMock = EasyMock.createMock(SeznamiUV.class);

        expect(seznamiUVMock.processInput("save file.txt")).andAnswer(() -> {
            throw new IOException();
        });

        replay(seznamiUVMock);
        IOException error = assertThrows(IOException.class, () -> {
            seznamiUVMock.processInput("save file.txt");
        });
        verify(seznamiUVMock);
    }

    @Test
    public void testGetters() {
        assertEquals(-1, uv.getAddCounter());
        assertEquals(-1, uv.getSearchCounter());
        assertEquals(-1, uv.getRemoveCounter());
        assertEquals(-1, uv.getResetCounter());
    }

    @Test
    public void testPrintEmpty() {
        assertEquals( ">> Number of Persons: 0\n",uv.processInput("print"));
    }


}