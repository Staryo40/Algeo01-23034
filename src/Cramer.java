package src;

public class Cramer {
    // Solve SPL by Cramer's Rule
    public static Matrix cramer(Matrix m){
        /*
         * Untuk persamaan Ax = b
         * 
         */
        Matrix res = new Matrix(m.rowEff, 1);
        Matrix A = m.GetSubMatrix(0, 0, m.rowEff, m.colEff-1);
        Matrix b = m.GetSubMatrix(0, m.colEff-1, m.rowEff, 1);
        Matrix Ai;
        double detA;

        if (A.rowEff!=A.colEff){
            // Cramer valid hanya untuk jumlah pers. sama dengan jumlah variabel
            return Matrix.UNDEFINED;
        }

        System.out.print("Determinan matriks ");
        detA = Determinant.det(A);
        System.out.println(detA);
        if (detA==0){
            // Cramer valid hanya untuk determinan A tak nol
            return Matrix.UNDEFINED;
        }

        // Ai adalah matrix A dengan kolom ke-i diganti dengan b
        Ai = m.GetSubMatrix(0, 0, m.rowEff, m.colEff-1);
        for (int i=0;i<A.colEff;i++){
            Ai = m.GetSubMatrix(0, 0, m.rowEff, m.colEff-1);
            // Modify Ai
            for (int j=0;j<A.rowEff;j++){
                Ai.mem[j][i] = b.mem[j][0];
            }
            res.mem[i][0] = Determinant.det(Ai)/detA;
        }

        return res;
    }
}
