package bin;

public class GaussJordan {

    public static int FindPotentialLeadingOne(Matrix m, int idx) {
        for (int i = 0; i < m.rowEff; i++) {
            for (int j = 0; j < m.colEff; j++) {
                if (j < idx && m.mem[i][j] != 0) {
                    break;
                }
                if (j == idx && m.mem[i][j] != 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static Matrix GaussElimination(Matrix m) {
        int leadingIdx = 0;
        int targetIdx;
        for (int i = 0; i < m.rowEff; i++) {
            /* Swap baris ke-i dengan baris yang memiliki non zero di kolom ke-leadingIdx */
            while (true) {
                targetIdx = FindPotentialLeadingOne(m, leadingIdx);
                if (targetIdx != -1) {
                    break;
                }
                if (++leadingIdx == m.colEff) {
                    return m;
                }
            }
            m.RowSwap(i, targetIdx);
            
            /* Kalikan baris ke-i agar mendapatkan baris yang memiliki 1 utama */
            m.RowMultiply(i, 1 / m.mem[i][leadingIdx]);

            /* Kurangkan baris yang bukan ke-i dengan k * i agar baris tersebut memiliki nilai 0 pada kolom ke-leadingIdx */
            for (int j = i + 1; j < m.rowEff; j++) {
                m.RowAddition(j, i, -m.mem[j][leadingIdx]);
            }

            leadingIdx++;
        }

        return m;
    }

    public static Matrix GaussJordanElimination(Matrix m) {
        int leadingIdx = 0;
        int targetIdx;
        for (int i = 0; i < m.rowEff; i++) {
            /* Swap baris ke-i dengan baris yang memiliki non zero di kolom ke-leadingIdx */
            while (true) {
                targetIdx = FindPotentialLeadingOne(m, leadingIdx);
                if (targetIdx != -1) {
                    break;
                }
                if (++leadingIdx == m.colEff) {
                    return m;
                }
            }
            if (targetIdx != i) {
                m.RowSwap(i, targetIdx);
            }

            /* Kalikan baris ke-i agar mendapatkan baris yang memiliki 1 utama */
            m.RowMultiply(i, 1 / m.mem[i][leadingIdx]);
            
            /* Kurangkan baris yang bukan ke-i dengan k * i agar baris tersebut memiliki nilai 0 pada kolom ke-leadingIdx */
            for (int j = 0; j < m.rowEff; j++) {
                if (j != i) {
                    m.RowAddition(j, i, -m.mem[j][leadingIdx]);
                }
            }

            leadingIdx++;
        }

        return m;
    }

    public static Matrix GetGaussJordanSolution(Matrix A, Matrix b){
        Matrix res;
        res = A.Augment(b);
        res = GaussJordanElimination(res);

        if (res.GetSubMatrix(0, 0, A.rowEff, A.colEff).IsEqualTo(A.GetIdentity())){
            return res.GetSubMatrix(0, A.colEff, A.rowEff, 1);
        }
        else {
            return Matrix.UNDEFINED;
        }
    }
}
