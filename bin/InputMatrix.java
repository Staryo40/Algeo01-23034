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

    public static class InterpolationInput {
        public Matrix matrix;
        public double x;

        public InterpolationInput(Matrix matrix, double x) {
            this.matrix = matrix;
            this.x = x;
        }
    }

    public static class RegressionInput {
        public Matrix sampleMatrix;
        public Matrix xMatrix;

        public RegressionInput(Matrix sampleMatrix, Matrix xMatrix) {
            this.sampleMatrix = sampleMatrix;
            this.xMatrix = xMatrix;
        }
    }

    public BicubicInput InputBicubicKeyBoard(){
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

    public BicubicInput InputBicubicFile(String filename){
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

    public InterpolationInput InputInterpolationKeyBoard(){
        // Menghasilkan output matrix (n,2) dan juga nilai-nilai x untuk ditaksir dari input keyboard
        Scanner scanner = new Scanner(System.in);
        System.out.print("Jumlah n (jumlah sampel): ");
        int n = scanner.nextInt();
        scanner.nextLine();

        // assigning to matrix
        Matrix rawInput = new Matrix(n,2);
        System.out.println("Enter per baris dengan spasi antar x dan y: ");
        for (int i = 0; i < n; i++){
            String inputRow = scanner.nextLine();
            String[] sepRow = inputRow.split(" ");

            if (sepRow.length != 2) {
                System.out.println("Error: Expected " + 2 + " columns, but received " + sepRow.length + "." + " FOKUS BUNG/NONA!");
                i--;
                continue; 
            }

            for (int j = 0; j < 2; j++){
                rawInput.mem[i][j] = parseDouble(sepRow[j]);
            }
        }

        System.out.print("Input x yang ingin ditaksir: ");
        String StringInput = scanner.next();
        double x = parseDouble(StringInput);

        return new InterpolationInput(rawInput, x);
    }

    public InterpolationInput InputInterpolationFile(String filename){
        // Menghasilkan output matrix (n,2) dan juga nilai-nilai x untuk ditaksir dari file .txt
        try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        
        int n = 0;
       // Counting samples
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            String[] values = line.split(" ");
            
            if (values.length == 2){
                n++;
            }
        }

        // Assigning primitive matrix attributes
        Matrix inputMatrix = new Matrix(n, 2);

        // Closing and reopening file
        myReader.close();
        myReader = new Scanner(myObj);

        // Assigning values to new array
        int rowCount = 0;
        while (rowCount < n) {
            String line = myReader.nextLine();
            String[] values = line.split(" "); 

            for (int i = 0; i < 2; i++){
                if (i < values.length && !values[i].isEmpty()){
                    inputMatrix.mem[rowCount][i] = parseDouble(values[i]);
                } else {
                    inputMatrix.mem[rowCount][i] = 0;
                }
            }
            rowCount++;
        }

        if (myReader.hasNextLine()){
            String line = myReader.nextLine();
            line = line.trim().replaceAll("\\s+", " ");
            double x = parseDouble(line);

            return new InterpolationInput(inputMatrix, x);
        } else {
            System.out.println("Error: x not inputted properly.");
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

    public RegressionInput InputRegressionKeyBoard(){
        // Input regression dengan pertama menerima n dan m, dan seterusnya menerima matrix dimensi (m, n+1) dengan kolom terakhir berupa y, terakhir adalah input semua nilai x (sebanyak m) untuk taksir y
        Scanner scanner = new Scanner(System.in);
        System.out.print("Jumlah n (jumlah peubah x): ");
        int n = scanner.nextInt();

        System.out.print("Jumlah m (jumlah sampel): ");
        int m = scanner.nextInt();
        scanner.nextLine();

        Matrix inputMatrix = new Matrix(m, n+1);
        System.out.println("Enter per baris dengan spasi setiap x dan y sebagai kolom terakhir: ");
        for (int i = 0; i < m; i++){
            String inputRow = scanner.nextLine();
            String[] sepRow = inputRow.split(" ");

            if (sepRow.length != n+1) {
                System.out.println("Error: Expected " + (n+1) + " columns, but received " + sepRow.length + "." + " FOKUS BUNG/NONA!");
                i--;
                continue; 
            }

            for (int j = 0; j < n+1; j++){
                inputMatrix.mem[i][j] = parseDouble(sepRow[j]);
            }
        }

        Matrix newXMatrix = new Matrix (m,1);
        System.out.printf("Enter dalam satu baris dengan spasi nilai x (%d buah) untuk taksir x: ", m);
        System.out.println("");
        String inputRow = scanner.nextLine();
        String[] sepRow = inputRow.split(" ");
        while (sepRow.length != m){
            System.out.printf("Jumlah x yang diinput tidak sesuai (tidak %d), coba lagi: ", m);
            inputRow = scanner.nextLine();
            sepRow = inputRow.split(" ");
        } 
        for (int i = 0; i < m; i++){
            newXMatrix.mem[i][0] = parseDouble(sepRow[i]);
        }
        
        return new RegressionInput(inputMatrix, newXMatrix);
    }

    public RegressionInput InputRegressionFile (String filename){
        // Menerima matrix (m, n), m jumlah sampel, n adalah banyak peubah x + 1 dengan kolom terakhir berupa nilai y dan juga menerima nilai x untuk menaksir y baru
        try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        
        int m = 0;
        int colEff = 0;
        boolean gotColumn = false;
       // Counting samples
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            String[] values = line.split(" ");
            
            // getting column size from first row
            if (!gotColumn){
                colEff = values.length;
                gotColumn = true;
            }

            if (values.length == colEff){
                m++;
            }
        }

        // Assigning primitive matrix attributes
        Matrix inputMatrix = new Matrix(m, colEff);

        // Closing and reopening file
        myReader.close();
        myReader = new Scanner(myObj);

        // Assigning values to new array
        int rowCount = 0;
        while (rowCount < m) {
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

        Matrix newXMatrix = new Matrix(colEff-1, 1);
        if (myReader.hasNextLine()){
            String line = myReader.nextLine();
            String[] value = line.split(" ");
            
            for (int i = 0; i < colEff-1; i++){
                newXMatrix.mem[i][0] = parseDouble(value[i]);
            }

            return new RegressionInput(inputMatrix, newXMatrix);
        } else {
            System.out.println("Error: x not inputted properly.");
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