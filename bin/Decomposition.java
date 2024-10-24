package bin;
import java.lang.Math;

public class Decomposition {
    public Matrix L, U, P;

    // Melakukan dekomposisi LUP matrix dan mengembalikan Array berisi matrix L, U, dan P
    public void decomposeLUP(Matrix m){
        /*
        * Matrix A didekomposisi menjadi
        * PA = LU
        * P : Matrix permutasi
        * L : Matrix segitiga bawah
        * U : Matrix segitiga atas
        * 
        * Digunakan partial pivoting
        */

        L = new Matrix(m.rowEff, m.colEff);
        U = new Matrix(m.rowEff, m.colEff);
        P = new Matrix(m.rowEff, m.colEff);

        // Matrix L. Diagonal berisi nilai 1
        for (int i=0;i<m.rowEff;i++){
            L.mem[i][i] = 1;
        }
        // Matrix U. Akan dilakukan eliminasi Gauss di matrix ini
        for (int i=0;i<m.rowEff;i++){
            for (int j=0;j<m.colEff;j++){
                U.mem[i][j] = m.mem[i][j];
            }
        }
        // Matrix P. Berupa matrix permutasi. P = I jika tidak terjadi pergantian baris
        P = m.GetIdentity();

        // ELiminasi Gauss dengan partial pivoting
        double place;
        double mult;
        double pivot;
        int pivotIdx;
        for (int j=0;j<m.colEff-1;j++){
            // Cari pivot, dengan nilai absolut tertinggi
            pivot = Math.abs(U.mem[j][j]);
            pivotIdx = j;
            for (int i=j;i<m.rowEff;i++){
                if (Math.abs(U.mem[i][j])>pivot){
                    pivot = Math.abs(U.mem[i][j]);
                    pivotIdx = i;
                }
            }
            if (pivotIdx!=j){
                // Swap elemen L
                for (int col=0;col<j;col++){
                    place = L.mem[pivotIdx][col];
                    L.mem[pivotIdx][col] = L.mem[j][col];
                    L.mem[j][col] = place;
                }
                // Swap pivot elemen matrix U dan P
                U.RowSwap(pivotIdx, j);
                P.RowSwap(pivotIdx, j);
            }
            // Jika pivot tak nol tidak ditemukan
            if (pivot==0){
                System.out.println("LU Decomposition failed");
                return;
            }


            // Eliminasi Gauss
            for (int i=j+1;i<m.rowEff;i++){
                mult = U.mem[i][j]/U.mem[j][j];
                U.RowAddition(i, j, -mult);
                L.mem[i][j] = mult;
            }
        }

        return;
    }

    // Solve SPL dengan matrix terfaktor
    public Matrix solve(Matrix target){
        /*
         * Solve LUx = Pb <=> PAx = Pb <=> Ax = b
         * P : Matrix permutasi
         * L : Matrix segitiga bawah
         * U : Matrix segitiga atas
         * b : vektor target
         * x : Solusi
         * 
         */
        Matrix Pb = new Matrix(target.rowEff, 1);
        Matrix y = new Matrix(target.rowEff, 1);
        Matrix x = new Matrix(target.rowEff, 1);
        // Komputasi Pb
        Pb = P.rightMultiply(target);
        // Solve Ly = Pb dengan forward substitution
        for (int i=0;i<Pb.rowEff;i++){
            y.mem[i][0] = Pb.mem[i][0];
            for (int j=0;j<i;j++){
                y.mem[i][0] -= L.mem[i][j] * Pb.mem[j][0];
            }
        }
        // Solve Ux = y dengan backward substitution
        for (int i=y.rowEff-1;i>=0;i--){
            x.mem[i][0] = y.mem[i][0];
            for (int j=i+1;j<y.rowEff;j++){
                x.mem[i][0] -= U.mem[i][j] * y.mem[j][0];
            }
            x.mem[i][0] = x.mem[i][0] / U.mem[i][i];
        }
        return x;
    }
}
