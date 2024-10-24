package bin;

public class Determinant {
    // Toleransi komparasi double
    static double tol = 0.0001;

    public static double det(Matrix m){
        if (m.rowEff==m.colEff){
            Decomposition factoring = new Decomposition(m);
            factoring.decomposeLUP(m);
            double det = factoring.computeDeterminant();
            return det;
        }
        else {
            return 0;               // Undefined behaviour
        }
    }

    // Fungsi pembantu detKofaktor
    public static Matrix minor(Matrix m, int row, int col){
        /*
            Mengembalikan matriks yang merupakan minor
            dari matriks m pada baris row dan kolom col
        */

        Matrix res = new Matrix(m.rowEff - 1, m.colEff - 1);

        int i = 0, j = 0;
        int ires = 0, jres = 0;
        while (i < m.rowEff){
            if (i != row){
                j = 0;
                jres = 0;
                while (j < m.colEff){
                    if (j != col){
                        res.mem[ires][jres] = m.mem[i][j];
                        jres++;
                    }
                    j++;
                }
                ires++;
            }
            i++;
        }

        return res;
    }

    public static double detKofaktor(Matrix m){
        // Determinan matriks 1x1
        if (m.rowEff==1){
            return m.mem[0][0];
        }
    
        // Ekspansi kofaktor di baris pertama
        double res = 0;
        int j = 0;
        double sign = 1;
        while (j<m.colEff){
            if (m.mem[0][j]>tol || -tol>m.mem[0][j]){
                // Agar jika mendekati nol, tidak perlu komputasi detKofaktor
                res += sign * m.mem[0][j] * detKofaktor(minor(m, 0, j));
            }
            sign *= -1;
            j++;
        }
        return res;
    }

    // Mengembalikan determinan matriks segitiga
    public static double detTriangular(Matrix m){
        /*
         * Matrix m segitiga atas atau segitiga bawah
         */
        int i = 0;
        int res = 1;
        while (i<m.rowEff){
            res *= m.mem[i][i];
            i++;
        }
        return res;
    }
}