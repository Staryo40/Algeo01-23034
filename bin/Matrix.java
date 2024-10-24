package bin;

public class Matrix {
    public static final Matrix UNDEFINED = new Matrix(-1, -1);

    public double[][] mem;
    public int rowEff;
    public int colEff;

    // KONSTRUKTOR
    public Matrix(int nRow,int nCol){
        rowEff = nRow;
        colEff = nCol;
        if (rowEff >= 0 && colEff >= 0) {
            mem = new double[nRow][nCol]; 
            for (int i = 0; i < nRow; i++){
                for (int j = 0; j < nCol; j++){
                    mem[i][j] = 0;
                } 
            }
        }
        else {
            mem = new double[0][0];
        }
    }   

    // I/O TERMINAL MATRIX  
    // Function to help format double
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

    // Procedure to print matrix
    public void printMatrix(){
        for (int i = 0; i < rowEff; i++){
            for (int j = 0; j < colEff; j++){

                System.out.print(formatDouble((mem[i][j])));

                if (j < colEff-1){
                    System.out.print(" ");
                }

            }
            System.out.println();
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

    // Fungsi yang mengembalikan transpose matrix
    public Matrix GetTranspose(){
        Matrix res = new Matrix(colEff, rowEff);
        for (int i=0;i<rowEff;i++){
            for (int j=0;j<colEff;j++){
                res.mem[j][i] = this.mem[i][j];
            }
        }
        return res;
    }

    // Invers dari matrix dengan Gauss-Jordan
    public Matrix GetInverse() {
        return Inverse.inverse(this);
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

        int row1=0, joint, col2;
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

        int row1=0, joint, col2;
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
    public double vectorDot(Matrix m){
        double res = 0;
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
        int i=0, j;
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
        double[] temp = mem[row1];
        mem[row1] = mem[row2];
        mem[row2] = temp;
    }

    // Mengali baris
    public void RowMultiply(int targetRow, double mult) {
        for (int i = 0; i < colEff; i++) {
            mem[targetRow][i] *= mult;
        }
    }

    // Menambahkan baris dengan kelipatan baris lain
    public void RowAddition(int targetRow, int additionRow, double mult) {
        for (int i = 0; i < colEff; i++) {
            mem[targetRow][i] = mem[targetRow][i] + mult * mem[additionRow][i]; 
        }
    }

    // Prosedur untuk menghapus 1 kolom
    public void DeleteCol(int colIDX){
        for (int i = 0; i < rowEff; i++){
            for (int j = colIDX; j < colEff-1; j++){
                mem[i][j] = mem[i][j+1];
            }
        }
        colEff--;
    }

    // Prosedur untuk menghapus 1 row
    public void DeleteRow(int rowIDX){
        for (int i = rowIDX; i < rowEff-1; i++){
            mem[i] = mem[i+1];        
        }
        rowEff--;
    }
}


