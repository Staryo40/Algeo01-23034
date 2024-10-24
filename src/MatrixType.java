package src;

public class MatrixType {

    // Fungsi yang mengembalikan matrix identitas n x n
    public static Matrix Identity(int n) {
        Matrix m = new Matrix(n, n);
        m.GetIdentity();
        return m;
    }

    // Fungsi yang mengembalikan matrix hilbert n x n
    public static Matrix Hilbert(int n) {
        Matrix m = new Matrix(n , n);
        
        for (int i = 0; i < m.rowEff; i++) {
            for (int j = 0; j < m.colEff; j++) {
                m.mem[i][j] = 1 / (i + j + 1);
            }
        }

        return m;
    }
}
