package bin;

public class BicubicInterpolation {
    public static Matrix MatrixX(){
        // menghasilkan matrix X (16,16) untuk bicubic spline
        int xVar, yVar;

        Matrix res = new Matrix(16,16);

        // FIRST FOUR ROWS
        int j = 0;
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 16; col++){
                xVar = (row % 4 == 0 || row % 4  == 2) ? 0 : 1;
                yVar = (row % 4 == 0 || row % 4  == 1) ? 0 : 1;
                res.mem[row][col] = Math.pow(xVar, col % 4) * Math.pow(yVar, j);
                if (col > 0 && col % 4 == 3){
                    j++;
                }
            }
            j = 0;
        }

        // SECOND FOUR ROWS;
        for (int row = 4; row < 8; row++){
            for (int col = 0; col < 16; col++){
                xVar = (row % 4 == 0 || row % 4  == 2) ? 0 : 1;
                yVar = (row % 4 == 0 || row % 4  == 1) ? 0 : 1;
                if (col % 4 == 0){
                    res.mem[row][col] = 0;
                } else {
                    res.mem[row][col] = (col % 4) * Math.pow(xVar, (col % 4)-1) * Math.pow(yVar, j);
                }
                if (col > 0 && col % 4 == 3){
                    j++;
                }
            }
            j = 0;
        }

        // THIRD FOUR ROWS
        for (int row = 8; row < 12; row++){
            for (int col = 0; col < 16; col++){
                xVar = (row % 4 == 0 || row % 4  == 2) ? 0 : 1;
                yVar = (row % 4 == 0 || row % 4  == 1) ? 0 : 1;
                if (j == 0){
                    res.mem[row][col] = 0;
                } else {
                    res.mem[row][col] = j * Math.pow(xVar, (col % 4)) * Math.pow(yVar, j-1);
                }
                if (col > 0 && col % 4 == 3){
                    j++;
                }
            }
            j = 0;
        }

        // FOURTH FOUR ROWS
        for (int row = 12; row < 16; row++){
            for (int col = 0; col < 16; col++){
                xVar = (row % 4 == 0 || row % 4  == 2) ? 0 : 1;
                yVar = (row % 4 == 0 || row % 4  == 1) ? 0 : 1;
                if ((col % 4) - 1 == -1 || j-1 == -1){
                    res.mem[row][col] = 0;
                } else {
                    res.mem[row][col] = j * (col % 4) * Math.pow(xVar, (col % 4)-1) * Math.pow(yVar, j-1);
                }
                if (col > 0 && col % 4 == 3){
                    j++;
                }
            }
            j = 0;
        }

        return res;
    }

    public static Matrix InterpolationConstant (Matrix X, Matrix f){
        // menerima matrix X (berupa output dari matrixX), matrix X di declare dan di kalkulasi dulu dan seterusnya di input ke fungsi ini untuk menghindar kalkulasi X berkali-kali
        // matrix terdiri dari f, fx, fy, dan fxy berdimensi (16,1)

        // output berupa konstanta a00 hingga a33 dalam bentuk matrix (16,1)
        Matrix augmentedX = X.Augment(f);
        augmentedX.printMatrix();
        Matrix resMatrix = GaussJordan.GaussJordanElimination(augmentedX);
        Matrix res = resMatrix.GetSubMatrix(0, 0, 16, 1);
        return res;
    }

    public static double InterpolationSolve (Matrix Constant, double x, double y){
        // Input Constant is the output of Interpolation Constant, which is a (16, 1) matrix
        // Using bicubic spline interpolation, solve for a point P(x,y), ex: P(1.5,1.5)
        double res = 0;

        int j = 0;
        for (int i = 0; i < 16; i++){
            res += Constant.mem[i][0] * Math.pow(x, (i % 4)) * Math.pow(y, j);
            if (i > 0 && i % 4 == 3){
                j++;
            }
        }

        return res;
    }
}