package src;

public class Inverse {

    public static Matrix inverse(Matrix m){
        return inverseGaussJordan(m);
    }

    // Invers dari matrix dengan Gauss-Jordan
    public static Matrix inverseGaussJordan(Matrix m) {
        Matrix augmentedMatrix = m.Augment(m.GetIdentity());
        augmentedMatrix = GaussJordan.GaussJordanElimination(augmentedMatrix);
        Matrix supposedIdentity = augmentedMatrix.GetSubMatrix(0, 0, m.rowEff, m.colEff);

        if (supposedIdentity.IsEqualTo(supposedIdentity.GetIdentity())) {
            return augmentedMatrix.GetSubMatrix(0, m.colEff, m.rowEff, m.colEff);
        }
        else {
            return Matrix.UNDEFINED;
        }
    }

    // Invers dari matrix dengan matriks adjunct
    public static Matrix inverseAdj(Matrix m){
        Matrix res = new Matrix(m.colEff, m.rowEff);
        // Bentuk matriks kofaktor (C)
        for(int i=0;i<m.rowEff;i++){
            for (int j=0;j<m.colEff;j++){
                res.mem[i][j] = Math.pow(-1, i+j) * Determinant.det(Determinant.minor(m, i, j));
            }
        }

        // adj(A) = transpose(C)
        res = res.GetTranspose();

        // Kalikan dengan 1/det(m)
        double det = Determinant.det(m);
        for(int i=0;i<m.rowEff;i++){
            for (int j=0;j<m.colEff;j++){
                res.mem[i][j] = (1/det)*res.mem[i][j];
            }
        } 

        return res;
    }

    
}
