package bin;
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

}
