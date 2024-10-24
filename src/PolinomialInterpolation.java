package src;

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

    public static double GetEstimate(Matrix a, double x) {
        double currentX = 1;
        double total = 0;
        for (int i = 0; i < a.rowEff; i++) {
            total += a.mem[i][0] * currentX;
            currentX *= x;
        }
        return total;
    }

    public static void PrintPolinomialInterpolation(Matrix a) {
        System.out.printf("p%d(x) = %s ", a.rowEff-1, Matrix.formatDouble(a.mem[0][0]));
        for (int i = 1; i < a.rowEff; i++) {
            if (a.mem[i][0] > 0) {
                System.out.printf(" + %sx^%d", Matrix.formatDouble(a.mem[i][0]), i);
            } else if (a.mem[i][0] < 0) {
                System.out.printf(" - %sx^%d", Matrix.formatDouble(-a.mem[i][0]), i);
            }
        }
    }
}