package bin;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrix {
    public static final Matrix UNDEFINED = new Matrix(-1, -1);

    public float[][] mem;
    public int rowEff;
    public int colEff;

    // KONSTRUKTOR
    public Matrix(int nRow,int nCol){
        rowEff = nRow;
        colEff = nCol;
        if (rowEff >= 0 && colEff >= 0) {
            mem = new float[nRow][nCol]; 
            for (int i = 0; i < nRow; i++){
                for (int j = 0; j < nCol; j++){
                    mem[i][j] = 0;
                } 
            }
        }
        else {
            mem = new float[0][0];
        }
    }   

    // I/O TERMINAL MATRIX  
    // Function to help format float
     public static String formatFloat(float value) {
        if (value == 0.0f && Float.floatToIntBits(value) == Float.floatToIntBits(-0.0f)) {
            return "0";
        }
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
            System.out.println();
        }
    }

    // Function to read matrix via KEYBOARD
    public void readMatrixKeyBoard(){
        Scanner scanner = new Scanner(System.in);
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

    // Function to read matrix via FILE.TXT
    public void readMatrixFile(String filename){
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
        colEff = tempColEff;
        rowEff = tempRowEff;
        mem = new float[rowEff][colEff];

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
                    mem[rowCount][i] = Float.parseFloat(values[i]);
                } else {
                    mem[rowCount][i] = 0;
                }
            }
            rowCount++;
        }
        myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numbers.");
            e.printStackTrace();
        }
    }

    // OPERASI BOOLEAN
    public boolean IsEqualTo(Matrix m) {
        if (rowEff != m.rowEff || colEff != m.colEff) {
            return false;
        }
        for (int i = 0; i < rowEff; i++) {
            for (int j = 0; j < colEff; j++) {
                if (mem[i][j] != m.mem[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean IsSquare() {
        return (rowEff == colEff);
    }

    // OPERASI DASAR
    // Membuat matrix identitas dengan dimensi yang sama
    public Matrix GetIdentity() {
        Matrix newMatrix = new Matrix(colEff, colEff);
        for (int i = 0; i < colEff; i++) {
            newMatrix.mem[i][i] = 1;
        }
        return newMatrix;
    }

    // Invers dari matrix dengan Gauss-Jordan
    public Matrix GetInverse() {
        Matrix augmentedMatrix = this.Augment(this.GetIdentity());
        augmentedMatrix = GaussJordan.GaussJordanElimination(augmentedMatrix);
        Matrix supposedIdentity = augmentedMatrix.GetSubMatrix(0, 0, this.rowEff, this.colEff);

        if (supposedIdentity.IsEqualTo(supposedIdentity.GetIdentity())) {
            return augmentedMatrix.GetSubMatrix(0, this.colEff, this.rowEff, this.colEff);
        }
        else {
            return Matrix.UNDEFINED;
        }
    }

    // Fungsi yang mengembalikan hasil augment matrix self ke matrix m
    public Matrix Augment(Matrix m) {
        Matrix newMatrix = new Matrix(rowEff, colEff + m.colEff);
        for (int i = 0; i < newMatrix.rowEff; i++) {
            for (int j = 0; j < newMatrix.colEff; j++) {
                if (j < colEff) {
                    newMatrix.mem[i][j] = mem[i][j];
                }
                else {
                    newMatrix.mem[i][j] = m.mem[i][j-colEff];
                }
            }
        }
        return newMatrix;
    }

    // Fungsi yang mengembalikan sub matrix sesuai parameter
    public Matrix GetSubMatrix(int iStart, int jStart, int nRow, int nCol) {
        Matrix newMatrix = new Matrix(nRow, nCol);
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                newMatrix.mem[i][j] = mem[i + iStart][j + jStart];
            }
        }
        return newMatrix;
    }

    public Matrix rightMultiply(Matrix m){
        Matrix res = new Matrix(this.rowEff, m.colEff);
        if (this.colEff!=m.rowEff){
            return Matrix.UNDEFINED;
        }

        int row1=0, joint=0, col2=0;
        while (row1<this.rowEff){
            col2 = 0;
            while (col2<m.colEff){
               joint = 0;
               while (joint<m.rowEff){
                  res.mem[row1][col2] += this.mem[row1][joint] * m.mem[joint][col2];
                  joint++;
               }
               col2++;
            }
            row1++;
        }

        return res;
    }

    public Matrix leftMultiply(Matrix m){
        Matrix res = new Matrix(this.rowEff, m.colEff);
        if (m.colEff!=this.rowEff){
            return Matrix.UNDEFINED;
        }

        int row1=0, joint=0, col2=0;
        while (row1<m.rowEff){
            col2 = 0;
            while (col2<this.colEff){
               joint = 0;
               while (joint<this.rowEff){
                  res.mem[row1][col2] += m.mem[row1][joint] * this.mem[joint][col2];
                  joint++;
               }
               col2++;
            }
            row1++;
        }

        return res;
    }

    // Perkalian dot vektor/matrix kolom
    public float vectorDot(Matrix m){
        float res = 0;
        for (int i=0;i<rowEff;i++){
            res += this.mem[i][0] * m.mem[i][0];
        }
        return res;
    }

    // Menghasilkan matrix yang setiap elemennya perkalian dari dua elemen matrix sama dimensi
    public Matrix multiplyElement(Matrix m){
        /*
         * Contoh:
         *  1 2 3   x   2 2 2  ->  2 4 6
         *  4 5 6       2 2 2      8 10 12
         * 
         */
        Matrix res = new Matrix(rowEff, colEff);
        int i=0, j=0;
        while (i<m.rowEff){
            j=0;
            while (j<m.colEff){
                res.mem[i][j] = m.mem[i][j] * this.mem[i][j];
                j++;
            }
            i++;
        }

        return res;
    }
    // OPERASI BARIS ELEMENTER
    // Menukar baris
    public void RowSwap(int row1, int row2) {
        float[] temp = mem[row1];
        mem[row1] = mem[row2];
        mem[row2] = temp;
    }

    // Mengali baris
    public void RowMultiply(int targetRow, float mult) {
        for (int i = 0; i < colEff; i++) {
            mem[targetRow][i] *= mult;
        }
    }

    // Menambahkan baris dengan kelipatan baris lain
    public void RowAddition(int targetRow, int additionRow, float mult) {
        for (int i = 0; i < colEff; i++) {
            mem[targetRow][i] = mem[targetRow][i] + mult * mem[additionRow][i]; 
        }
    }
}


