import java.io.*;

public class Aplikacija {
    public static void main(String[] args) {
        SeznamiUV seznamiUV = new SeznamiUV();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String output;

        try {
            do {
                boolean cond = seznamiUV.getAddCounter() == - 1 && seznamiUV.getRemoveCounter() == - 1 &&
                        seznamiUV.getSearchCounter() == -1 && seznamiUV.getResetCounter() == -1;
                if (cond) {
                    System.out.print("command>>: ");
                }
                input = br.readLine();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println(">> Bye");
                    System.exit(0);
                }
                output = seznamiUV.processInput(input);
                cond = seznamiUV.getAddCounter() == - 1 && seznamiUV.getRemoveCounter() == - 1 &&
                        seznamiUV.getSearchCounter() == -1 && seznamiUV.getResetCounter() == -1;
                if (cond) {
                    System.out.println(output);
                }
                else {
                    System.out.print(output);
                }
            }
            while (!input.equalsIgnoreCase("exit"));
        }
        catch (IOException e)
        {
            System.err.println("Failed to retrieve the next command.");
            System.exit(1);
        }
    }
}
