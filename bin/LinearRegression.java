package bin;


public class LinearRegression {
   public static Matrix Regression(Matrix m){
      /* Input: m adalah matriks dengan kolom terakhirnya berupa y sampel dan kolom lainnya adalah nilai x0, x1, ..., xn yang menghasilkan y 
         Output: vektor model parameter yang berisi beta0, beta1, ..., betaN */
      Matrix parameterMatrix = new Matrix(0,0);
      parameterMatrix = AugmentOnesCol(m, 0);
      parameterMatrix = GaussJordan.GaussJordanElimination(parameterMatrix);

      Matrix modelParameter = new Matrix(0,0);
      modelParameter = GaussJordan.GetGaussJordanSolution(parameterMatrix);

      return modelParameter;
   } 

   public static Matrix AugmentOnesCol(Matrix m, int colIdx){
      // Menghasilkan matrix dengan tambahan satu kolom berisi semua 1 pada colIdx
      Matrix res = new Matrix(m.rowEff, m.colEff+1);
      int mCol = 0, mRow = 0;

      for (int i = 0; i < res.rowEff; i++){
         for (int j = 0; j < res.colEff; j++){
               res.mem[i][j] = (j == colIdx) ? 1 : m.mem[mRow][mCol];
               if (j != colIdx){
                  mCol++;
               }
         }
         mRow++;
      }

      return res;
   }

   public static float Solve(Matrix sample, Matrix problem){
      /* "sample" adalah matriks dengan kolom terakhirnya berupa y sampel dan kolom lainnya adalah nilai x1, x2, ..., xn yang menghasilkan y 
         "problem" adalah matriks dimensi (1, n) yang berisi nilai x1, x2, ..., xn yang ingin dihitung untuk memperoleh y dari model regresi linier
         Note: regSolution berdimensi (n, 1), sedangkan problem (1,n) */
      Matrix regSolution = Regression(sample);
      float solution = regSolution.mem[0][0];
      regSolution.DeleteRow(0);
      for (int i = 0; i < problem.colEff; i++){
         solution += regSolution.mem[i][0] * problem.mem[0][i];
      }

      return solution;
   }
}

