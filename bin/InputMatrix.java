package bin;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputMatrix {
    public static class BicubicInput {
        public Matrix matrix;
        public double x;
        public double y;

        public BicubicInput(Matrix matrix, double x, double y) {
            this.matrix = matrix;
            this.x = x;
            this.y = y;
        }
    }

    public static BicubicInput InputBicubicKeyBoard(){
        // Input: matrix 4x4 dan pada line 5: x dan y yang ingin ditaksir (sesuai input di spesifikasi)
        // Output: matrix (16,1) yang mengandung input dari keyboard user
        Scanner scanner = new Scanner(System.in);
        Matrix rawInput = new Matrix(4,4);
        System.out.println("Enter per baris dengan spasi antar kolom: ");
        for (int i = 0; i < 4; i++){
            String inputRow = scanner.nextLine();
            String[] sepRow = inputRow.split(" ");

            if (sepRow.length != 4) {
                System.out.println("Error: Expected " + 4 + " columns, but received " + sepRow.length + "." + " FOKUS BUNG/NONA!");
                i--;
                continue; 
            }

            for (int j = 0; j < 4; j++){
                String valueStr = sepRow[j].replace(",", ".");
                rawInput.mem[i][j] = Double.parseDouble(valueStr);
            }
        }

        Matrix FinalInput = new Matrix(16,1);
        int finalIDX = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                FinalInput.mem[finalIDX][0] = rawInput.mem[i][j]; 
                finalIDX++;
            }
        }

        double x = getDoubleInput(scanner, "Enter x value: ");
        double y = getDoubleInput(scanner, "Enter y value: ");

        return new BicubicInput(FinalInput, x, y);
    }

    public static BicubicInput InputBicubicFile(String filename){
        // Method to get input for bicubic spline interpolation via file .txt
        try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        Matrix rawInput = new Matrix(4,4);

        // Getting 4x4 Matrix
        for (int i = 0; i < 4; i++) {
            String line = myReader.nextLine();
            String[] sepRow = line.split(" ");
            
            if (sepRow.length != 4) {
                System.out.println("Error: Expected " + 4 + " columns, but received " + sepRow.length + "." + " FOKUS BUNG/NONA!");
                System.out.println("Input Failed.");
                return null;
            }

            for (int j = 0; j < 4; j++){
                rawInput.mem[i][j] = parseDouble(sepRow[j]);
            }
        }
        
        if (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            String[] sepRow = line.split(" ");
            double x, y;

            if (sepRow.length != 2) {
                System.out.println("Error: Expected " + 2 + " inputs for x and y, but received " + sepRow.length + "." + " FOKUS BUNG/NONA!");
                System.out.println("Input Failed.");
                return null; 
            }

            x = parseDouble(sepRow[0]);
            y = parseDouble(sepRow[1]);

            return new BicubicInput(rawInput, x, y);
        } else {
            System.out.println("Error: Not enough lines in file for x and y inputs.");
            return null; 
        }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
            return null; 
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
            return null; 
        }
    }

    // Function to read matrix via KEYBOARD
    public Matrix InputMatrixKeyBoard(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Jumlah row: ");
        int rowEff = scanner.nextInt();

        System.out.print("Jumlah column: ");
        int colEff = scanner.nextInt();
        scanner.nextLine();

        Matrix inputMatrix = new Matrix(rowEff, colEff);
        System.out.println("Enter per baris dengan spasi antar kolom: ");
        for (int i = 0; i < rowEff; i++){
            String inputRow = scanner.nextLine();
            String[] sepRow = inputRow.split(" ");

            if (sepRow.length != colEff) {
                System.out.println("Error: Expected " + colEff + " columns, but received " + sepRow.length + "." + " FOKUS BUNG/NONA!");
                i--;
                continue; 
            }

            for (int j = 0; j < colEff; j++){
                inputMatrix.mem[i][j] = parseDouble(sepRow[j]);
            }
        }

        return inputMatrix;
    }

    // Function to read matrix via FILE.TXT
    public Matrix InputMatrixFile(String filename){
        try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        
        int tempRowEff = 0;
        int tempColEff = 0;

        // Counting rows and columns first
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            String[] values = line.split(" ");
            
            tempRowEff++;
            if (values.length > tempColEff){
                tempColEff = values.length;
            }
        }

        // Assigning primitive matrix attributes
        int colEff = tempColEff;
        int rowEff = tempRowEff;
        Matrix inputMatrix = new Matrix(rowEff, colEff);

        // Closing and reopening file
        myReader.close();
        myReader = new Scanner(myObj);

        // Assigning values to new array
        int rowCount = 0;
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            String[] values = line.split(" "); 

            for (int i = 0; i < colEff; i++){
                if (i < values.length && !values[i].isEmpty()){
                    inputMatrix.mem[rowCount][i] = parseDouble(values[i]);
                } else {
                    inputMatrix.mem[rowCount][i] = 0;
                }
            }
            rowCount++;
        }
        return inputMatrix;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
            return null; 
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
            return null; 
        }
    }

    // HELPER METHODS
    // getDoubleInput helper, replace double comma with dot 
    static private double parseDouble(String value) {
        value = value.replace(',', '.');

        return Double.parseDouble(value);
    }

    // BicubicInput helper, parse double properly whether using dot or comma
    static private double getDoubleInput(Scanner scanner, String prompt) {
        double value = 0.0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.next(); 

            try {
                value = parseDouble(input); 
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number with a dot or comma as the decimal separator.");
            }
        }
        return value;
    }

}