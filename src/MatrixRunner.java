package src;
import java.util.Scanner;

public class MatrixRunner{
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.err.println();
        }
    }

    public static int mainMenu(){
        String welcome = """
       __        __         _                                                                                           
       \\ \\      / /   ___  | |   ___    ___    _ __ ___     ___                                                         
        \\ \\ /\\ / /   / _ \\ | |  / __|  / _ \\  | '_ ` _ \\   / _ \\                                                        
         \\ V  V /   |  __/ | | | (__  | (_) | | | | | | | |  __/                                                        
          \\_/\\_/     \\___| |_|  \\___|  \\___/  |_| |_| |_|  \\___|                                               
            """;

        String title = """
███    ███  █████  ████████ ██████  ██ ██   ██      █████  ██████  ██████                                                        
████  ████ ██   ██    ██    ██   ██ ██  ██ ██      ██   ██ ██   ██ ██   ██                                                       
██ ████ ██ ███████    ██    ██████  ██   ███       ███████ ██████  ██████                                                        
██  ██  ██ ██   ██    ██    ██   ██ ██  ██ ██      ██   ██ ██      ██                                                            
██      ██ ██   ██    ██    ██   ██ ██ ██   ██     ██   ██ ██      ██       
                                                            By Pohon Terbang
        """;
        String menu = """
        Choose from the following menu by typing the corresponding number!
        1. System of Linear Equation
        2. Determinant
        3. Inverse
        4. Polinomial Interpolation
        5. Bicubic Spline Interpolation
        6. Linear and Quadratic Regression
        7. Exit
        """;
        System.out.println(welcome);
        System.out.println(title);
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Pick your poison: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 7){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        return userChoice;
    }

    public static int SPLChoice(){
        String menu = """
        System of Linear Equations: The place to solve all your worries!
        
        Choose a method to solve the system of linear equations:
        1. Gauss Elimination
        2. Gauss-Jordan Elimination
        3. Inverse Method
        4. Cramer Method

        Or...
        5. Back
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 5){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        return userChoice;
    }

    public static void SolveSPL(int solveChoice){
        String menu = """
        Choose how you'll input the augmented matrix:
        1. Keyboard Input
        2. File
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 2){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        clearScreen();
        InputMatrix input = new InputMatrix();
        Matrix inputMatrix = new Matrix(0,0);
        if (userChoice == 1){
            inputMatrix = input.InputMatrixKeyBoard();
        } else if (userChoice == 2){
            System.out.print("Enter your preferred filename with (.txt) and put the text file in directory 'test': ");
            String filename = scanner.next();
            inputMatrix = input.InputMatrixFile("test/" + filename);
        }
        Matrix solvedMatrix;
        switch(solveChoice){
            case 1 -> {
                solvedMatrix = GaussJordan.GaussElimination(inputMatrix);
                System.out.println("Here is the end matrix from Gauss elimination");
                solvedMatrix.printMatrix();
                System.out.println("");
                Matrix res = GaussJordan.GaussJordanElimination(inputMatrix);
                MatrixOutput.GetSPLSolution(res);
            }
            case 2 -> {
                solvedMatrix = GaussJordan.GaussJordanElimination(inputMatrix);
                System.out.println("Here is the end matrix from Gauss-Jordan elimination");
                solvedMatrix.printMatrix();
                System.out.println("");
                MatrixOutput.GetSPLSolution(solvedMatrix);
            }
            case 3 -> {
                System.out.println("Solved by inverse...");
            }
            case 4 -> {
                System.out.println("Solved by Cramer...");
            }
        }
    }

    public static int DeterminantChoice(){
        String menu = """
        Determinants: Why determine you future when determinants can do that for you!
        
        Choose a method to solve the determinants:
        1. Triangular Matrix
        2. Cofactor

        Or...
        3. Back
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 3){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        return userChoice;
    }

    public static void SolveDeterminant(int solveChoice){
        String menu = """
        Choose how you'll input the NxN matrix:
        1. Keyboard Input
        2. File
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 2){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        InputMatrix input = new InputMatrix();
        Matrix inputMatrix = new Matrix(0,0);
        clearScreen();
        if (userChoice == 1){
            inputMatrix = input.InputMatrixKeyBoard();
        } else if (userChoice == 2){
            System.out.print("Enter your preferred filename with (.txt) and put the text file in directory 'test': ");
            String filename = scanner.next();
            inputMatrix = input.InputMatrixFile("test/" + filename);
        }
        switch (solveChoice){
            case 1 -> {
                System.out.print("The determinant of that matrix with triangular matrix is: ");
                double res = Determinant.det(inputMatrix);
                System.out.println(Matrix.formatDouble(res));
            }
            case 2 -> {
                System.out.print("The determinant of that matrix with cofactor is: ");
                double res = Determinant.detKofaktor(inputMatrix);
                System.out.println(Matrix.formatDouble(res));
            }
        }
    }

    public static int InverseChoice(){
        String menu = """
        Inverse: them inverts will turn your life around!
        
        Choose a method to solve the determinants:
        1. Augmented Gauss-Jordan
        2. Adjoint Method

        Or...
        3. Back
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 3){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        return userChoice;
    }

    public static void SolveInverse(int solveChoice){
        String menu = """
        Choose how you'll input the NxN matrix:
        1. Keyboard Input
        2. File
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 2){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        clearScreen();
        InputMatrix input = new InputMatrix();
        Matrix inputMatrix = new Matrix(0,0);
        if (userChoice == 1){
            inputMatrix = input.InputMatrixKeyBoard();
        } else if (userChoice == 2){
            System.out.print("Enter your preferred filename with (.txt) and put the text file in directory 'test': ");
            String filename = scanner.next();
            inputMatrix = input.InputMatrixFile("test/" + filename);
        }
        switch (solveChoice){
            case 1 -> {
                System.out.print("The inverse of the matrix with Gauss-Jordan is: ");
                Matrix res = Inverse.inverseGaussJordan(inputMatrix);
                res.printMatrix();
                System.out.println("");
            }
            case 2 -> {
                System.out.print("The determinant of that matrix is: ");
                double resDet = Determinant.detKofaktor(inputMatrix);
                System.out.println(resDet);
                System.out.print("The inverse of the matrix with adjoint method is: ");
                Matrix res = Inverse.inverseAdj(inputMatrix);
                res.printMatrix();
                System.out.println("");
            }
        }
    }

    public static int RegressionChoice(){
        String menu = """
        Regression: Anything better than regressing throught life?
        
        Choose a method to solve the determinants:
        1. Multiple Linear Interpolation
        2. Multiple Quadratic Interpolation

        Or...
        3. Back
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 3){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        return userChoice;
    }

    public static void SolveRegression(int solveChoice){
        String menu = """
        Choose how you'll input the sample and the x for estimation:
        1. Keyboard Input
        2. File
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 2){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        clearScreen();
        InputMatrix input = new InputMatrix();
        Matrix placeMat = new Matrix(0,0);
        InputMatrix.RegressionInput regressionInput = new InputMatrix.RegressionInput(placeMat, placeMat);

        if (userChoice == 1){
            regressionInput = input.InputRegressionKeyBoard();
        } else if (userChoice == 2){
            System.out.print("Enter your preferred filename with (.txt) and put the text file in directory 'test': ");
            String filename = scanner.next();
            regressionInput = input.InputRegressionFile("test/" + filename);
        }
        Matrix sampleMatrix = regressionInput.sampleMatrix;
        Matrix xEstimation = regressionInput.xMatrix;
        switch (solveChoice){
            case 1 -> {
                System.out.println("The coefficients from multiple linear regression are:");
                Matrix resConstant = LinearRegression.Regression(sampleMatrix);
                for (int i = 0; i < resConstant.rowEff; i++){
                    System.out.printf("a%d: %.2f", i, resConstant.mem[i][0]);
                    System.out.println("");
                }
                System.out.println("");
                System.out.printf("So result of linear regression with x values provided is = %.2f", LinearRegression.Solve(sampleMatrix, xEstimation));
                System.out.println(""); 

            }
            case 2 -> {
                // Quadratic interpolation
                System.out.println("The coefficients from multiple linear regression are:");
                sampleMatrix = sampleMatrix.Augment(sampleMatrix.GetSubMatrix(0, 0, sampleMatrix.rowEff, 1));
                sampleMatrix.DeleteCol(0);
                Matrix resConstant = QuadraticRegression.regress(sampleMatrix);
                for (int i = 0; i < resConstant.rowEff; i++){
                    System.out.printf("a%d: %.2f", i, resConstant.mem[i][0]);
                }
                System.out.println("");
                System.out.printf("So result of linear regression with x values provided is = %.2f", QuadraticRegression.predict(resConstant, xEstimation.GetTranspose()));
                System.out.println(""); 
            }
        }
    }

    public static void SolveInterpolation(){
        String menu = """
        Choose how you'll input x and y samples and also the x that will be estimated:
        1. Keyboard Input
        2. File
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 2){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        clearScreen();
        InputMatrix input = new InputMatrix();
        
        Matrix place = new Matrix(0,0);
        double placex = 0;
        InputMatrix.InterpolationInput inputInterpolation = new InputMatrix.InterpolationInput(place, placex);

        if (userChoice == 1){
            inputInterpolation = input.InputInterpolationKeyBoard();
        } else if (userChoice == 2){
            System.out.print("Enter your preferred filename with (.txt) and put the text file in directory 'test': ");
            String filename = scanner.next();
            inputInterpolation = input.InputInterpolationFile("test/" + filename);
        }
        Matrix sampleMatrix = inputInterpolation.matrix;
        double x = inputInterpolation.x;
        System.out.println("The polynomial equation is: ");
        PolinomialInterpolation.PrintPolinomialInterpolation(sampleMatrix);
        System.out.println("");
        System.out.print("The polynomial equation is: ");
        System.out.printf("%.2f", PolinomialInterpolation.GetEstimate(sampleMatrix, x));
        System.out.println("");
    }

    public static void SolveBicubic(){
        String menu = """
        Choose how you'll input the 4x4 matrix:
        1. Keyboard Input
        2. File
        """;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your preferred method: ");
        int userChoice = scanner.nextInt();
        while (userChoice < 0 || userChoice > 2){
            System.out.print("T'was not a valid choice, try again: ");
            userChoice = scanner.nextInt();
        }
        clearScreen();
        InputMatrix input = new InputMatrix();
        
        Matrix place = new Matrix(0,0);
        double placex = 0;
        double placey = 0;
        InputMatrix.BicubicInput inputBicubic = new InputMatrix.BicubicInput(place, placex, placey);

        if (userChoice == 1){
            inputBicubic = input.InputBicubicKeyBoard();
        } else if (userChoice == 2){
            System.out.print("Enter your preferred filename with (.txt) and put the text file in directory 'test': ");
            String filename = scanner.next();
            inputBicubic = input.InputBicubicFile("test/" + filename);
        }
        Matrix fValues = inputBicubic.matrix;
        double x = inputBicubic.x;
        double y = inputBicubic.y;
        System.out.println("The coefficients from bicubic spline interpolation are: ");
        Matrix xMatrix = BicubicInterpolation.MatrixX();
        Matrix BicubicConstants = BicubicInterpolation.InterpolationConstant(xMatrix, fValues);
        for (int i = 0; i < BicubicConstants.rowEff; i++){
            System.out.printf("x%d: %.2f", i, BicubicConstants.mem[i][0]);
            System.out.println("");
        }
        System.out.println("");
        System.out.printf("So P(%.2f, %.2f) = %.2f", x, y, BicubicInterpolation.InterpolationSolve(BicubicConstants, x, y));
        System.out.println("");
    }

    private static boolean confirmContinue() {
        Scanner scanner = new Scanner(System.in);
        String input;
        
        while (true) { // Infinite loop until a valid input is received
            System.out.println("");
            System.out.print("Do you want to continue? (y/n): ");
            input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false; 
            } else {
                System.out.println("");
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            clearScreen();
            int userChoice = mainMenu();
            clearScreen();
            switch (userChoice) {
                case 1 -> {
                    int splChoice = SPLChoice();
                    clearScreen();
                    switch (splChoice) {
                        case 1 -> SolveSPL(1);
                        case 2 -> SolveSPL(2);
                        case 3 -> SolveSPL(3);
                        case 4 -> SolveSPL(4);
                        case 5 -> {
                            continue; 
                        }
                    }
                }
                case 2 -> {
                    int determinantChoice = DeterminantChoice();
                    clearScreen();
                    switch(determinantChoice){
                        case 1 -> SolveDeterminant(1);
                        case 2 -> SolveDeterminant(2);
                        case 3 -> {
                            continue;
                        }
                    }
                }
                case 3 -> {
                    int inverseChoice = InverseChoice();
                    clearScreen();
                    switch(inverseChoice){
                        case 1 -> SolveInverse(1);
                        case 2 -> SolveInverse(2);
                        case 3 -> {
                            continue;
                        }
                    }
                }
                case 4 -> SolveInterpolation();
                case 5 -> SolveBicubic();
                case 6 -> {
                    int regressionChoice = RegressionChoice();
                    clearScreen();
                    switch (regressionChoice){
                        case 1 -> SolveRegression(1);
                        case 2 -> SolveRegression(2);
                        case 3 -> {
                            continue;
                        }
                    }
                }
                case 7 -> {
                    System.out.println("Exiting the program. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
            if (running) {
                boolean confirmed = confirmContinue();
                if (!confirmed) {
                    System.out.println("Exiting the program. Goodbye!");
                    running = false; // Exit the loop
                }
            }
        }
        scanner.close();
    }
}








