import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SeznamiUV {

    private Drevo23<Drzavljan> seznamPoEMSO;
    private Drevo23<Drzavljan> seznamPoImenu;
    private int addCounter;
    private int removeCounter;
    private String emso, ime, priimek, cepivo, starost;
    private int resetCounter;
    private int searchCounter;

    static String memoryError = "Error: not enough memory, operation failed";

    public SeznamiUV() {
        seznamPoEMSO = new Drevo23<Drzavljan>(new DrzavljanPrimerjajPoEMSO());
        seznamPoImenu = new Drevo23<Drzavljan>(new DrzavljanPrimerjajPoImenu());
        addCounter = -1;
        removeCounter = -1;
        emso = "";
        ime = "";
        priimek = "";
        cepivo = "";
        starost = "";
        resetCounter = -1;
        searchCounter = -1;
    }

    private String[] makeFileName(String file) {
        String[] nameSplit =  file.split("\\.");
        //if file has more than one dot
        String fileExtension = nameSplit[nameSplit.length-1];
        String fileName = String.join(".",
                Arrays.copyOfRange(nameSplit, 0, nameSplit.length - 1));
        return new String[] {fileName + "_p.bin", fileName + "_e.bin"};
    }

    private String printList(List<Drzavljan> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Drzavljan drzavljan = list.get(i);
            result.append(drzavljan.emso).append("\t").append(drzavljan.priimek).
                    append(", ").append(drzavljan.ime).append("\t")
                    .append(drzavljan.starost).append("\t").append(drzavljan.cepivo);
            if (i != list.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public String processInput(String input) {
        String token;
        String result = "OK";
        String[] params = input.split(" ");


        //moramo preverjati za primer praznega Niza
        if (params.length == 0) {
            return "Invalid command";
            //never reached
        } else {
            token = params[0];
        }
        try {

            if ((token.equals("add") || addCounter > -1)) {
                result = "add>";
                if (token.equals("add") && params.length == 1 && addCounter == -1) {
                    addCounter++;
                    result += " EMSO: ";
                } else if (addCounter > -1 && !token.equals("add")) {
                    switch (addCounter) {
                        case 0:
                            result += " NAME: ";
                            emso = params[0];
                            addCounter++;
                            break;
                        case 1:
                            result += " SURNAME: ";
                            if (params.length == 2) {
                                ime = params[0] + " " + params[1];
                            }
                            else if (params.length == 1) {
                                ime = params[0];
                            }
                            else {
                                addCounter = -1;
                                throw new InvalidDataException();
                            }
                            addCounter++;
                            break;
                        case 2:
                            result += " AGE: ";
                            if (params.length == 2) {
                                priimek = params[0] + " " + params[1];
                            }
                            else if (params.length == 1) {
                                priimek = params[0];
                            }
                            else {
                                addCounter = -1;
                                throw new InvalidDataException();
                            }
                            addCounter++;
                            break;
                        case 3:
                            result += " VACCINE: ";
                            starost = params[0];
                            addCounter++;
                            break;
                        case 4:
                            addCounter = -1;
                            cepivo = params[0];
                            seznamPoEMSO.add(
                                    new Drzavljan(emso, ime, priimek, Integer.parseInt(starost), cepivo));
                            seznamPoImenu.add(
                                    new Drzavljan(emso, ime, priimek, Integer.parseInt(starost), cepivo)
                            );
                            result = "OK";
                            break;
                    }
                }
                else {
                    result = ">> Invalid input data";
                }
            }
            else if (token.equals("remove") || removeCounter > -1) {
                result = "remove> ";
                if (token.equals("remove") && params.length == 2)  {
                    Drzavljan drzavljan = seznamPoEMSO.getElement(new Drzavljan(params[1]));
                    if (drzavljan == null) {
                        throw new java.util.NoSuchElementException();
                    }
                    seznamPoEMSO.remove(drzavljan);
                    seznamPoImenu.remove(drzavljan);
                    result = "OK";
                }
                else if (token.equals("remove") && params.length == 1 && removeCounter == -1) {
                    removeCounter = 0;
                    result += "NAME: ";

                }
                else if (removeCounter > - 1 && !token.equals("remove")) {
                    if (removeCounter == 0) {
                        if (params.length == 2) {
                            ime = params[0] + " " + params[1];
                        }
                        else if (params.length == 1) {
                            ime = params[0];
                        } else {
                            removeCounter = -1;
                            throw new InvalidDataException();
                        }
                        removeCounter++;
                        result += "SURNAME: ";
                    }
                    else if (removeCounter == 1) {
                        removeCounter = -1;
                        if (params.length == 2) {
                            priimek = params[0] + " " + params[1];
                        }
                        else if (params.length == 1) {
                            priimek = params[0];
                        }
                        else {
                            removeCounter = -1;
                            throw new InvalidDataException();
                        }

                        Drzavljan drzavljan = seznamPoImenu.getElement(new Drzavljan(ime, priimek));
                        if (drzavljan == null) {
                            throw new java.util.NoSuchElementException();
                        }
                        seznamPoImenu.remove(drzavljan);
                        seznamPoEMSO.remove(drzavljan);
                        result = "OK";

                    }
                }
                else
                    result = ">> Invalid argument";
            }
            else if (token.equals("count")){
                result = ">> No. of Persons: " + seznamPoImenu.size() + "";
            }
            else if (token.equals("reset") || resetCounter > -1) {
                if (params.length != 1 && resetCounter == -1) {
                    return ">> Invalid argument";
                }
                if (token.equals("reset")) {
                    result = "reset> Are you sure (y|n): ";
                    resetCounter = 1;
                }
                else if (resetCounter == 1) {
                    if (params[0].equals("y")) {
                        resetCounter = -1;
                        seznamPoEMSO.reset();
                        seznamPoImenu.reset();
                        result = "OK";
                    }
                    else if (params[0].equals("n")) {
                        resetCounter = -1;
                    }
                    else {
                        result = "reset> Are you sure (y|n): ";
                    }
                }
            }
            else if (token.equals("print")) {
                result = ">> Number of Persons: " + seznamPoImenu.size() + "\n";
                List<Drzavljan> sortedList = seznamPoImenu.inorder();
                result += printList(sortedList);
            }
            else if (token.equals("save")){
                if (params.length == 2) {
                    String[] fileNames = makeFileName(params[1]);
                    seznamPoImenu.save(new FileOutputStream(fileNames[0]));
                    seznamPoEMSO.save(new FileOutputStream(fileNames[1]));
                } else {
                    result = "Error: please specify a file name";
                }
            }
            else if (token.equals("restore")){
                if (params.length == 2) {
                    String[] fileNames = makeFileName(params[1]);

                    seznamPoImenu.restore(new FileInputStream(fileNames[0]));
                    seznamPoEMSO.restore(new FileInputStream(fileNames[1]));
                } else {
                    result = "Error: please specify a file name";
                }
            }
            else if (token.equals("search") || searchCounter > -1) {
                result = "search> ";
                if (token.equals("search") && params.length == 2)  {
                    Drzavljan drzavljan = seznamPoEMSO.getElement(new Drzavljan(params[1]));

                    if (drzavljan == null) {
                        throw new java.util.NoSuchElementException();
                    }
                    ArrayList<Drzavljan> temp = new ArrayList<>();
                    temp.add(drzavljan);
                    result = printList(temp);
                }
                else if (token.equals("search") && params.length == 1 && searchCounter == -1) {
                    searchCounter = 0;
                    result += "NAME: ";

                }
                else if (searchCounter > - 1 && !token.equals("search")) {
                    if (searchCounter == 0) {
                        if (params.length == 2) {
                            ime = params[0] + " " + params[1];
                        } else if (params.length == 1) {
                            ime = params[0];
                        } else throw new InvalidDataException();
                        searchCounter++;
                        result += "SURNAME: ";
                    } else if (searchCounter == 1) {
                        searchCounter = -1;
                        if (params.length == 2) {
                            priimek = params[0] + " " + params[1];
                        } else if (params.length == 1) {
                            priimek = params[0];
                        } else throw new InvalidDataException();

                        Drzavljan drzavljan = seznamPoImenu.getElement(new Drzavljan(ime, priimek));
                        if (drzavljan == null) {
                            throw new java.util.NoSuchElementException();
                        }
                        ArrayList<Drzavljan> temp = new ArrayList<>();
                        temp.add(drzavljan);
                        result = printList(temp);

                    }
                }
                else {
                    result = ">> Invalid input data";
                }

            }
            else {
                result = ">> Invalid command";
            }


        } catch (UnsupportedOperationException e) {
            result = ">> Error: Operation not supported";
        } catch (IllegalArgumentException e) {
            result = ">> Person already exists";
        } catch (java.util.NoSuchElementException e) {
            result = ">> Person does not exist";
        } catch (IOException e) {
            result = ">> Error: IO error " + e.getMessage();
        } catch (ClassNotFoundException e) {
            result = ">> Error: Unknown format";
        } catch (OutOfMemoryError e) {
            return memoryError;
        }
        catch (InvalidDataException e) {
            result = ">> Invalid input data";
        }

        return result;
    }

    public int getAddCounter() {
        return addCounter;
    }

    public int getRemoveCounter() {
        return removeCounter;
    }

    public int getResetCounter() {
        return resetCounter;
    }

    public int getSearchCounter() {
        return searchCounter;
    }
}
