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

    // Menghitung determinan matrix dengan determinan faktornya
    public double computeDeterminant(){
        /*
         * Untuk dekomposisi PA = LU berlaku
         * det(P)det(A) = det(L)det(U)
         * det(P) bernilai 1 atau -1
         * det(L) bernilai 1
         * det(U) dapat dievaluasi dengan perkalian elemen diagonal
         * 
         */
        det = 1;
        det *= Determinant.detTriangular(factors[1]);
        det *= Determinant.detKofaktor(factors[2]);     // 1/(-1)==-1 dan 1/1==1
        return det;
    }

    // Mengembalikan determinant tanpa perhitungan
    public double getDeterminant(){
        return det;
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
        Pb = factors[2].rightMultiply(target);
        // Solve Ly = Pb dengan forward substitution
        for (int i=0;i<Pb.rowEff;i++){
            y.mem[i][0] = Pb.mem[i][0];
            for (int j=0;j<i;j++){
                y.mem[i][0] -= factors[0].mem[i][j] * Pb.mem[j][0];
            }
        }
        // Solve Ux = y dengan backward substitution
        for (int i=y.rowEff-1;i>=0;i--){
            x.mem[i][0] = y.mem[i][0];
            for (int j=i+1;j<y.rowEff;j++){
                x.mem[i][0] -= factors[1].mem[i][j] * y.mem[j][0];
            }
            x.mem[i][0] = x.mem[i][0] / factors[1].mem[i][i];
        }
        return x;
    }
}
