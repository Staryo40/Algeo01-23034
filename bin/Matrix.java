package bin;

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
            System.out.println();
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

    /* Operasi Baris Elementer */
    public void RowSwap(int row1, int row2) {
        float[] temp = mem[row1];
        mem[row1] = mem[row2];
        mem[row2] = temp;
    }

    public void RowMultiply(int targetRow, float mult) {
        for (int i = 0; i < colEff; i++) {
            mem[targetRow][i] *= mult;
        }
    }

    public void RowAddition(int targetRow, int additionRow, float mult) {
        for (int i = 0; i < colEff; i++) {
            mem[targetRow][i] = mem[targetRow][i] + mult * mem[additionRow][i]; 
        }
    }

    // Function to get element from row and column
    public float getElmt(Matrix m, int row, int col){
        return m.mem[row][col];
    }
}


