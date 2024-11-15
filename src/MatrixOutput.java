package src;

public class MatrixOutput {

    // Fungsi yang menentukan hasil int solution type
    // Solution type 0 = tidak ada solusi, 1 = hanya ada satu solusi, 2 = ada banyak solusi
    // Prekondisi : Matrix m adalah matris eselon baris tereduksi
    public static int FindSolutionType(Matrix m) {
        int solutionType = 1;
        int expectedLeadingOne = 0;
        for (int i = 0; i < m.rowEff; i++) {
            boolean leadingZero = true;

            for (int j = 0; j < m.colEff; j++) {
                if (leadingZero && m.mem[i][j] == 0) continue;
                leadingZero = false;
                if (j == m.colEff - 1) return 0;
                if (j != expectedLeadingOne) {
                    solutionType = 2;
                    break;
                }
                expectedLeadingOne++;
                break;
            }

            if (leadingZero) break;
        }

        if (expectedLeadingOne != m.colEff - 1) solutionType = 2;

        return solutionType;
    }

    public static void GetSPLSingleSolution(Matrix m) {
        System.out.println("Solusi SPL:");
        for (int i = 0; i < m.colEff - 1; i++) {
            System.out.printf("x%d = %f\n", (i+1), m.mem[i][m.colEff - 1]);
        }
    }

    public static void GetSPLManySolution(Matrix m) {
        System.out.println("Solusi Parametrik:");
        char[] parameters = new char[m.colEff - 1];
        char currentParameter = 's';
        int expectedLeadingOne = 0;

        // assign variable to xi with
        for (int i = 0; i < m.rowEff; i++) {
            if (expectedLeadingOne == m.colEff - 1) {
                break;
            }
            if (m.mem[i][expectedLeadingOne] != 0) {
                parameters[expectedLeadingOne] = ' ';
                expectedLeadingOne++;
            } else {
                parameters[expectedLeadingOne] = currentParameter;
                currentParameter = (char) (((int) currentParameter - 96) % 26 + 97);
                i--;
                expectedLeadingOne++;
            }
        }
        for (int i = expectedLeadingOne; i < m.colEff - 1; i++) {
            parameters[i] = currentParameter;
            currentParameter = (char) (((int) currentParameter - 96) % 26 + 97);
        }

        expectedLeadingOne = 0;
        for (int i = 0; i < m.rowEff; i++) {
            if (expectedLeadingOne == m.colEff - 1) {
                break;
            }
            if (m.mem[i][expectedLeadingOne] != 0) {
                System.out.printf("x%d = ", (expectedLeadingOne+1));
                if (m.mem[i][m.colEff-1] != 0)
                    System.out.printf("%s", Matrix.formatDouble(m.mem[i][m.colEff-1]));
                boolean HasParameter = false;
                for (int j = expectedLeadingOne + 1; j < m.colEff - 1; j++) {
                    if (m.mem[i][j] < 0) {
                        System.out.printf(" + %s%c", Matrix.formatDouble(-m.mem[i][j]), parameters[j]);
                        HasParameter = true;
                    } else if (m.mem[i][j] > 0) {
                        System.out.printf(" - %s%c", Matrix.formatDouble(m.mem[i][j]), parameters[j]);
                        HasParameter = true;
                    }
                }
                if (!HasParameter)
                    System.out.print("0");
                System.out.println("");
                expectedLeadingOne++;
            } else {
                System.out.printf("x%d = %c\n", (expectedLeadingOne+1), parameters[expectedLeadingOne]);
                i--;
                expectedLeadingOne++;
            }
        }
    }

    public static void GetSPLSolution(Matrix m) {
        int solutionType = FindSolutionType(m);
        switch (solutionType) {
            case 0 -> System.out.println("Tidak ada solusi!");
            case 1 -> GetSPLSingleSolution(m);
            case 2 -> GetSPLManySolution(m);
            default -> throw new AssertionError();
        }
    }

    public static void GetSPLCramerSolution(Matrix m){
        System.out.println("Solusi SPL:");
        for (int i = 0; i < m.rowEff; i++) {
            System.out.printf("x%d = %f\n", (i+1), m.mem[i][0]);
        }
    }
}
