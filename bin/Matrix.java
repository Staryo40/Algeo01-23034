package bin;
import java.util.Scanner;

public class Matrix {
    float[][] mem;
    int rowEff;
    int colEff;

    // Constructor
    public Matrix(int nRow,int nCol){
        rowEff = nRow;
        colEff = nCol;
        mem = new float[nRow][nCol]; 
        for (int i = 0; i < nRow; i++){
            for (int j = 0; j < nCol; j++){
                mem[i][j] = 0;
            } 
        }
    }   

    // Function to help format float
     public static String formatFloat(float value) {
        if (value == Math.floor(value)) {
            return String.format("%.0f", value);
        } else {
            return String.format("%.1f", value);
        }
    }

    // Procedure to print matrix
    public void printMatrix(){
        for (int i = 0; i < rowEff; i++){
            for (int j = 0; j < colEff; j++){

                System.out.print(formatFloat((mem[i][j])));

                if (j < colEff-1){
                    System.out.print(" ");
                }

            }
            System.out.println("");
        }
    }

    // Function to read matrix
    Scanner scanner = new Scanner(System.in);
    public void readMatrix(){
        System.out.print("Jumlah row: ");
        rowEff = scanner.nextInt();

        System.out.print("Jumlah column: ");
        colEff = scanner.nextInt();
        scanner.nextLine();

        mem = new float[rowEff][colEff];
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
                mem[i][j] = Float.parseFloat(sepRow[j]);
            }
        }
    }

    // Function to get element from row and column
    public float getElmt(Matrix m, int row, int col){
        return m.mem[row][col];
    }
}


