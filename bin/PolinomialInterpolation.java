package bin;

public class PolinomialInterpolation {
    public static Matrix GetPolinomialInterpolation(Matrix m){
        Matrix x = new Matrix(m.rowEff, m.rowEff);

        for (int i = 0; i < x.colEff; i++) {
            for (int j = 0; j < x.rowEff; j++) {
                if (i == 0)
                    x.mem[j][i] = 1;
                else
                    x.mem[j][i] = x.mem[j][i - 1] * m.mem[j][0];
            }
        }
        
        Matrix y = new Matrix(m.rowEff, 1);

        for (int i = 0; i < y.rowEff; i++) {
            y.mem[i][1] = m.mem[i][1];
        }

        Matrix augmented = x.Augment(y);

        Matrix a = GaussJordan.GaussJordanElimination(augmented).GetSubMatrix(0, augmented.colEff-1, augmented.rowEff, 1);

        return a;
    }

    public static double GetEstimate(Matrix a, int x) {
        double currentX = 1;
        double total = 0;
        for (int i = 0; i < a.rowEff; i++) {
            total += a.mem[i][0] * currentX;
            currentX *= x;
        }
        return total;
    }
}