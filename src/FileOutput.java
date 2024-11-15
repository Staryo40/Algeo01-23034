package src;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileOutput {
    public static String formatDouble(double value) {
        if (value == 0.0f && Double.doubleToLongBits(value) == Double.doubleToLongBits(-0.0f)) {
            return "0";
        }
        if (value == 0){
            return String.format("%.0f", value);
        } else if (Math.abs(value) < 0.01) {
            return String.format("%.2e", value);
        } else if (Math.abs(value) > 100000){
            return String.format("%.2e", value);
        } else if (value == Math.floor(value)) {
            return String.format("%.0f", value);
        } else {
            return String.format("%.4f", value).replace(",", ".");
        }
    }
    
    public static void OutputGauss(Matrix input, Matrix output, String method){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to save the output to a .txt file? (y/n): ");
        String userResponse = scanner.nextLine().toLowerCase();

        if ("y".equals(userResponse)) {
            System.out.print("Enter the file name (including .txt): ");
            String fileName = scanner.nextLine();

            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            // Create the PrintWriter object to write to the user-provided file
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("Begining matrix:");
                // print matrix
                for (int i = 0; i < input.rowEff; i++){
                    for (int j = 0; j < input.colEff; j++){

                        writer.print(formatDouble((input.mem[i][j])));

                        if (j < input.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                writer.println("End matrix after " + method + " elimination:");
                for (int i = 0; i < output.rowEff; i++){
                    for (int j = 0; j < output.colEff; j++){

                        writer.print(formatDouble((output.mem[i][j])));

                        if (j < output.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                int solutionType = MatrixOutput.FindSolutionType(output);
                if (solutionType == 0) {
                    writer.println("Tidak ada solusi!");
                } else if (solutionType == 1) {
                    writer.println("Solusi SPL:");
                    for (int i = 0; i < output.colEff - 1; i++) {
                        writer.printf("x%d = %f\n", (i+1), output.mem[i][output.colEff - 1]);
                    }
                } else if (solutionType == 2){
                    writer.println("Solusi Parametrik:");
                    char[] parameters = new char[output.colEff - 1];
                    char currentParameter = 's';
                    int expectedLeadingOne = 0;

                    // assign variable to xi with
                    for (int i = 0; i < output.rowEff; i++) {
                        if (expectedLeadingOne == output.colEff - 1) {
                            break;
                        }
                        if (output.mem[i][expectedLeadingOne] != 0) {
                            parameters[expectedLeadingOne] = ' ';
                            expectedLeadingOne++;
                        } else {
                            parameters[expectedLeadingOne] = currentParameter;
                            currentParameter = (char) (((int) currentParameter - 96) % 26 + 97);
                            i--;
                            expectedLeadingOne++;
                        }
                    }
                    for (int i = expectedLeadingOne; i < output.colEff - 1; i++) {
                        parameters[i] = currentParameter;
                        currentParameter = (char) (((int) currentParameter - 96) % 26 + 97);
                    }

                    expectedLeadingOne = 0;
                    for (int i = 0; i < output.rowEff; i++) {
                        if (expectedLeadingOne == output.colEff - 1) {
                            break;
                        }
                        if (output.mem[i][expectedLeadingOne] != 0) {
                            writer.printf("x%d = ", (expectedLeadingOne+1));
                            if (output.mem[i][output.colEff-1] != 0)
                                writer.printf("%s", Matrix.formatDouble(output.mem[i][output.colEff-1]));
                            boolean HasParameter = false;
                            for (int j = expectedLeadingOne + 1; j < output.colEff - 1; j++) {
                                if (output.mem[i][j] < 0) {
                                    writer.printf(" + %s%c", Matrix.formatDouble(-output.mem[i][j]), parameters[j]);
                                    HasParameter = true;
                                } else if (output.mem[i][j] > 0) {
                                    writer.printf(" - %s%c", Matrix.formatDouble(output.mem[i][j]), parameters[j]);
                                    HasParameter = true;
                                }
                            }
                            if (!HasParameter)
                                writer.print("0");
                            writer.println("");
                            expectedLeadingOne++;
                        } else {
                            writer.printf("x%d = %c\n", (expectedLeadingOne+1), parameters[expectedLeadingOne]);
                            i--;
                            expectedLeadingOne++;
                        }
                    }
                } else {
                    throw new AssertionError();
                }
                
                System.out.println("Output has been saved to " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("The output will not be saved to a file.");
        }
        
    }

    public static void OutputInverse(Matrix input, Matrix output, String method){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to save the output to a file? (y/n): ");
        String userResponse = scanner.nextLine().toLowerCase();

        if ("y".equals(userResponse)) {
            // Ask for the file name (including .txt extension)
            System.out.print("Enter the file name (including .txt): ");
            String fileName = scanner.nextLine();

            // Ensure the file name ends with ".txt"
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("Begining matrix:");
                // print matrix
                for (int i = 0; i < input.rowEff; i++){
                    for (int j = 0; j < input.colEff; j++){

                        writer.print(formatDouble((input.mem[i][j])));

                        if (j < input.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                writer.println("Inversed matrix with " + method + " method:");
                for (int i = 0; i < output.rowEff; i++){
                    for (int j = 0; j < output.colEff; j++){

                        writer.print(formatDouble((output.mem[i][j])));

                        if (j < output.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                
                System.out.println("Output has been saved to " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("The output will not be saved to a file.");
        }
    }

    public static void OutputDeterminant(Matrix input, double det, String method){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to save the output to a file? (y/n): ");
        String userResponse = scanner.nextLine().toLowerCase();

        if ("y".equals(userResponse)) {
            // Ask for the file name (including .txt extension)
            System.out.print("Enter the file name (including .txt): ");
            String fileName = scanner.nextLine();

            // Ensure the file name ends with ".txt"
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("Begining matrix:");
                // print matrix
                for (int i = 0; i < input.rowEff; i++){
                    for (int j = 0; j < input.colEff; j++){

                        writer.print(formatDouble((input.mem[i][j])));

                        if (j < input.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                writer.print("Determinant with " + method + " method: ");
                writer.println(formatDouble(det));
                
                System.out.println("Output has been saved to " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("The output will not be saved to a file.");
        }

    }

    public static void OutputInterpolation(Matrix polinomial, double x){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to save the output to a file? (y/n): ");
        String userResponse = scanner.nextLine().toLowerCase();

        if ("y".equals(userResponse)) {
            // Ask for the file name (including .txt extension)
            System.out.print("Enter the file name (including .txt): ");
            String fileName = scanner.nextLine();

            // Ensure the file name ends with ".txt"
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("Polynomial equation:");
                // print matrix
                writer.printf("p%d(x) = %s ", polinomial.rowEff-1, formatDouble(polinomial.mem[0][0]));
                    for (int i = 1; i < polinomial.rowEff; i++) {
                        if (polinomial.mem[i][0] > 0) {
                            writer.printf(" + %sx^%d", Matrix.formatDouble(polinomial.mem[i][0]), i);
                        } else if (polinomial.mem[i][0] < 0) {
                            writer.printf(" - %sx^%d", Matrix.formatDouble(-polinomial.mem[i][0]), i);
                        }
                    }
                
                writer.println("");
                writer.printf("With x = %.2f, p(%.2f): ", x, x);
                writer.println(formatDouble(PolinomialInterpolation.GetEstimate(polinomial, x)));
                
                System.out.println("Output has been saved to " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("The output will not be saved to a file.");
        }
    }

    public static void OutputBicubic(Matrix coefficients, double x, double y){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to save the output to a file? (y/n): ");
        String userResponse = scanner.nextLine().toLowerCase();

        if ("y".equals(userResponse)) {
            // Ask for the file name (including .txt extension)
            System.out.print("Enter the file name (including .txt): ");
            String fileName = scanner.nextLine();

            // Ensure the file name ends with ".txt"
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println("Bicubic constants:");
                // print matrix
                for (int i = 0; i < coefficients.rowEff; i++){
                    for (int j = 0; j < coefficients.colEff; j++){

                        writer.print(formatDouble((coefficients.mem[i][j])));

                        if (j < coefficients.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                writer.printf("Solution of P(%.2f, %.2f):", x, y);
                writer.println(formatDouble(BicubicInterpolation.InterpolationSolve(coefficients, x, y)));
                
                System.out.println("Output has been saved to " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("The output will not be saved to a file.");
        }

    }

    public static void OutputRegression(Matrix coefficients, Matrix x, double solve, String method){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to save the output to a file? (y/n): ");
        String userResponse = scanner.nextLine().toLowerCase();

        if ("y".equals(userResponse)) {
            // Ask for the file name (including .txt extension)
            System.out.print("Enter the file name (including .txt): ");
            String fileName = scanner.nextLine();

            // Ensure the file name ends with ".txt"
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                writer.println(method + "regression coefficients:");
                // print matrix
                for (int i = 0; i < coefficients.rowEff; i++){
                    for (int j = 0; j < coefficients.colEff; j++){

                        writer.print(formatDouble((coefficients.mem[i][j])));

                        if (j < coefficients.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                writer.println("With x values:");
                for (int i = 0; i < x.rowEff; i++){
                    for (int j = 0; j < x.colEff; j++){

                        writer.print(formatDouble((x.mem[i][j])));

                        if (j < x.colEff-1){
                            writer.print(" ");
                        }

                    }
                    writer.println();
                }
                writer.println("");
                writer.printf("Solution: " + formatDouble(solve));
                
                System.out.println("Output has been saved to " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        } else {
            System.out.println("The output will not be saved to a file.");
        }

    }
}