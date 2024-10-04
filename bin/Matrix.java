package bin;

public class Matrix {
    public static final Matrix UNDEFINED = new Matrix(-1, -1);

    public float[][] mem;
    public int rowEff;
    public int colEff;

    // Constructor
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

    public Matrix GetIdentity() {
        Matrix newMatrix = new Matrix(colEff, colEff);
        for (int i = 0; i < colEff; i++) {
            newMatrix.mem[i][i] = 1;
        }
        return newMatrix;
    }

    public Matrix GetInverse() {
        Matrix augmentedMatrix = this.Augment(this.GetIdentity());
        GaussJordan gaussJordan = new GaussJordan();
        augmentedMatrix = gaussJordan.GaussJordanElimination(augmentedMatrix);
        Matrix supposedIdentity = augmentedMatrix.GetSubMatrix(0, 0, this.rowEff, this.colEff);

        if (supposedIdentity.IsEqualTo(supposedIdentity.GetIdentity())) {
            return augmentedMatrix.GetSubMatrix(0, this.colEff, this.rowEff, this.colEff);
        }
        else {
            return Matrix.UNDEFINED;
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


