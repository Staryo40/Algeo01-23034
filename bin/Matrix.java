package bin;

public class Matrix {
    int[][] mem;
    int rowEff;
    int colEff;

    public Matrix(int nRow,int nCol){
        rowEff = nRow;
        colEff = nCol;
        mem = new int[nRow][nCol]; 
        for (int i = 0; i < nRow; i++){
            for (int j = 0; j < nCol; j++){
                mem[i][j] = 0;
            } 
        }
    }   

    public void printMatrix(){
        for (int i = 0; i < rowEff; i++){
            for (int j = 0; j < colEff; j++){
                System.out.print(mem[i][j]);
                if (j < colEff-1){
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
}


