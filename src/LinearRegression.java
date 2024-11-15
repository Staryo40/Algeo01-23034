package src;

public class LinearRegression {
   public static Matrix Regression(Matrix m){
      /* Input: m adalah matriks dengan kolom terakhirnya berupa y sampel dan kolom lainnya adalah nilai x0, x1, ..., xn yang menghasilkan y 
         Output: vektor model parameter yang berisi beta0, beta1, ..., betaN */
      Matrix parameterMatrix = AugmentOnesCol(m, 0);
      if (parameterMatrix.rowEff > parameterMatrix.colEff-1){
         parameterMatrix = parameterMatrix.GetSubMatrix(0, 0, parameterMatrix.colEff-1, parameterMatrix.colEff);
      }
      parameterMatrix = GaussJordan.GaussJordanElimination(parameterMatrix);

      Matrix modelParameter = parameterMatrix.GetSubMatrix(0, parameterMatrix.colEff-1, parameterMatrix.rowEff, 1);

      return modelParameter;
   } 

   public static Matrix AugmentOnesCol(Matrix m, int colIdx){
      // Menghasilkan matrix dengan tambahan satu kolom berisi semua 1 pada colIdx
      Matrix res = new Matrix(m.rowEff, m.colEff + 1);  // Create a new matrix with one additional column
      int mCol;

      // Loop through each row of the original matrix
      for (int i = 0; i < res.rowEff; i++) {
         mCol = 0; // Reset the column index of the original matrix
         for (int j = 0; j < res.colEff; j++) {
               if (j == colIdx) {
                  res.mem[i][j] = 1; // Insert 1 at the colIdx position
               } else {
                  res.mem[i][j] = m.mem[i][mCol]; // Copy the original matrix values
                  mCol++; // Move to the next column of the original matrix
               }
         }
      }

      return res;
   }

   public static double Solve(Matrix sample, Matrix problem){
      /* "sample" adalah matriks dengan kolom terakhirnya berupa y sampel dan kolom lainnya adalah nilai x1, x2, ..., xn yang menghasilkan y 
         "problem" adalah matriks dimensi (1, n) yang berisi nilai x1, x2, ..., xn yang ingin dihitung untuk memperoleh y dari model regresi linier
         Note: regSolution berdimensi (n, 1), sedangkan problem (n,1) */
      Matrix regSolution = Regression(sample);
      double solution = regSolution.mem[0][0];
      int probId = 0;
      for (int i = 1; i < regSolution.rowEff; i++){
         solution += regSolution.mem[i][0] * problem.mem[probId][0];
         probId++;
      }

      return solution;
   }
}

