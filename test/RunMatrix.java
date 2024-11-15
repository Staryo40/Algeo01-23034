package test;
import src.InputMatrix;

public class RunMatrix {
    public static void main(String[] args){
        // InputMatrix inputMatrix = new InputMatrix();
        // InputMatrix.RegressionInput regressionInput = inputMatrix.InputRegressionFile("regressionTest.txt");
        
        // // Output the results
        // regressionInput.sampleMatrix.printMatrix();
        // regressionInput.xMatrix.printMatrix();
        /////////////////////////////////////////////////////////////////////////
        // InputMatrix inputMatrix = new InputMatrix();
        // InputMatrix.RegressionInput regressionInput = inputMatrix.InputRegressionFile("regressionTest.txt");
        
        // // Output the results
        // regressionInput.sampleMatrix.printMatrix();
        // regressionInput.xMatrix.printMatrix();
        /////////////////////////////////////////////////////////////////////////
        // InputMatrix inputMatrix = new InputMatrix();
        // InputMatrix.InterpolationInput interpolationInput = inputMatrix.InputInterpolationKeyBoard();
        
        // // Output the results
        // interpolationInput.matrix.printMatrix();
        // System.out.println("x: " + interpolationInput.x);
        /////////////////////////////////////////////////////////////////////////
        // InputMatrix inputMatrix = new InputMatrix();
        // InputMatrix.InterpolationInput interpolationInput = inputMatrix.InputInterpolationFile("interpolationTest.txt");
        
        // // Output the results
        // interpolationInput.matrix.printMatrix();
        // System.out.println("x: " + interpolationInput.x);
        /////////////////////////////////////////////////////////////////////////
        // InputMatrix inputMatrix = new InputMatrix();
        // Matrix newMatrix = inputMatrix.InputMatrixFile("test.txt");
        
        // // Output the results
        // newMatrix.printMatrix();
        /////////////////////////////////////////////////////////////////////////
        // InputMatrix inputMatrix = new InputMatrix();
        // Matrix newMatrix = inputMatrix.InputMatrixKeyBoard();
        
        // // Output the results
        // newMatrix.printMatrix();
        /////////////////////////////////////////////////////////////////////////
        InputMatrix inputMatrix = new InputMatrix();
        InputMatrix.BicubicInput bicubicInput = inputMatrix.InputBicubicFile("bicubicTest.txt");
        
        // Output the results
        bicubicInput.matrix.printMatrix();
        System.out.println("x: " + bicubicInput.x);
        System.out.println("y: " + bicubicInput.y);
        /////////////////////////////////////////////////////////////////////////
        // InputMatrix inputMatrix = new InputMatrix();
        // InputMatrix.BicubicInput bicubicInput = inputMatrix.InputBicubicKeyBoard();
        
        // // Output the results
        // bicubicInput.matrix.printMatrix();
        // System.out.println("x: " + bicubicInput.x);
        // System.out.println("y: " + bicubicInput.y);
        /////////////////////////////////////////////////////////////////////////
        // Matrix xMatrix = BicubicInterpolation.MatrixX();
        // xMatrix.printMatrix();
        // Matrix fValuesCheck = new Matrix(16, 1);
        // double[] values = {21,98,125,153,51,101,161,59,0,42,72,210,16,12,81,96};
        // for (int i = 0; i < values.length; i++) {
        //     fValuesCheck.mem[i][0] = values[i];
        // }
        // fValuesCheck.printMatrix();
        // Matrix BicubicConstants = BicubicInterpolation.InterpolationConstant(xMatrix, fValuesCheck);
        // BicubicConstants.printMatrix();

        // Matrix newConstant = new Matrix(16, 1);
        // double[] ConstantValues = {21, 51, 28, -2, 0, 16, 82, -56, 240, 217, -1295, 709, -136, -123, 888, -487};
        // for (int i = 0; i < ConstantValues.length; i++) {
        //     newConstant.mem[i][0] = ConstantValues[i];
        // }
        // newConstant.printMatrix();

        // System.out.printf("P(0,0) = %.2f\n", BicubicInterpolation.InterpolationSolve(newConstant, 0, 0));
        // System.out.printf("P(0.5,0.5) = %.2f\n", BicubicInterpolation.InterpolationSolve(newConstant, 0.5, 0.5));
        // System.out.printf("P(0.25,0.75) = %.2f\n", BicubicInterpolation.InterpolationSolve(newConstant, 0.25, 0.75));
        // System.out.printf("P(0.1,0.9) = %.2f\n", BicubicInterpolation.InterpolationSolve(newConstant, 0.1, 0.9));
        /////////////////////////////////////////////////////////////////////////
    }
}
