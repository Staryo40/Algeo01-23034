package bin;
import java.lang.Math;

public class Decomposition {
    public Matrix[] factors;
    public double det;
    
    public Decomposition(Matrix m){
        /*
         * Matrix m hanya diperlukan untuk dimensi matrix faktor
         */
        factors = new Matrix[3];
        for (int i=0;i<3;i++){
            factors[i] = new Matrix(m.rowEff, m.colEff);
        }
    }

    // Melakukan dekomposisi LUP matrix dan mengembalikan Array berisi matrix L, U, dan P
    public Matrix[] decomposeLUP(Matrix m){
        /*
        * Matrix A didekomposisi menjadi
        * PA = LU
        * P : Matrix permutasi
        * L : Matrix segitiga bawah
        * U : Matrix segitiga atas
        * 
        * Digunakan partial pivoting
        */

        // Matrix L. Diagonal berisi nilai 1
        for (int i=0;i<m.rowEff;i++){
            factors[0].mem[i][i] = 1;
        }
        // Matrix U. Akan dilakukan eliminasi Gauss di matrix ini
        for (int i=0;i<m.rowEff;i++){
            for (int j=0;j<m.colEff;j++){
                factors[1].mem[i][j] = m.mem[i][j];
            }
        }
        // Matrix P. Berupa matrix permutasi. P = I jika tidak terjadi pergantian baris
        factors[2] = m.GetIdentity();

        // ELiminasi Gauss dengan partial pivoting
        double place;
        double mult;
        double pivot;
        int pivotIdx;
        for (int j=0;j<m.colEff-1;j++){
            // Cari pivot, dengan nilai absolut tertinggi
            pivot = Math.abs(factors[1].mem[j][j]);
            pivotIdx = j;
            for (int i=j;i<m.rowEff;i++){
                if (Math.abs(factors[1].mem[i][j])>pivot){
                    pivot = Math.abs(factors[1].mem[i][j]);
                    pivotIdx = i;
                }
            }
            if (pivotIdx!=j){
                // Swap elemen L
                for (int col=0;col<j;col++){
                    place = factors[0].mem[pivotIdx][col];
                    factors[0].mem[pivotIdx][col] = factors[0].mem[j][col];
                    factors[0].mem[j][col] = place;
                }
                // Swap pivot elemen matrix U dan P
                factors[1].RowSwap(pivotIdx, j);
                factors[2].RowSwap(pivotIdx, j);
            }
            // Jika pivot tak nol tidak ditemukan
            if (pivot<0.01){
                System.out.println("LU Decomposition failed");
                return factors;
            }


            // Eliminasi Gauss
            for (int i=j+1;i<m.rowEff;i++){
                mult = factors[1].mem[i][j]/factors[1].mem[j][j];
                factors[1].RowAddition(i, j, -mult);
                factors[0].mem[i][j] = mult;
            }
        }

        return factors;
    }
}
