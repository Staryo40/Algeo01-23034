package bin;

public class QuadraticRegression {
    // Mengembalikan parameter regresi kuadrat multivariat
    public static Matrix regress(Matrix data){
        /*
         * Mengembalikan parameter regresi
         * 
         * data berupa matrix
         * y1 u1 v1 ...
         * y2 u2 v2 ...
         * y3 u3 v3 ...
         * .  .  .
         * Pemodelan berbentuk Ax = b
         * A : matriks pemodelan
         * x : parameter
         * b : target
         * 
         * Contoh untuk kasus dua variabel: regresi memiliki bentuk
         * y = a + bu + cv + du^2 + ev^2 + fuv
         * Fungsi mengembalikan matriks
         * [a, b, c, d, e, f]
         * Contoh untuk kasus tiga variabel: regresi memiliki bentuk
         * y = a + bu + cv + dw + eu^2 + fv^2 + gw^2 + huv + iuw + jvw
         * Fungsi mengembalikan matriks
         * [a, b, c, d, e, f, g, h, i, j]
         */

        int varCount = data.colEff-1;
        // Jumlah parameter untuk seluruh variabel konstan, linear, kuadrat, dan interaksi
        int paramCount = 1 + varCount + varCount + varCount*(varCount-1)/2;
        

        // Matrix paramCount x 1 
        Matrix target = new Matrix(paramCount, 1);
        target = getTarget(data);

        // Matrix pemodelan regresi paramCount x paramCount
        Matrix model = new Matrix(paramCount, paramCount);
        model = getModel(data);

        // Invers matrix pemodelan
        Matrix inverseModel = new Matrix(paramCount, paramCount);
        inverseModel = model.GetInverse();
        
        
        // Ax=b ==> x=inverse(A)b 
        Matrix res = new Matrix(paramCount, 1);
        if (!inverseModel.IsEqualTo(Matrix.UNDEFINED)){
            res = inverseModel.rightMultiply(target);
        }
        else {
            System.out.println("Regression failed");
            res = Matrix.UNDEFINED;
        }

        return res;
    }

    // Mengembalikan matrix model regresi
    public static Matrix getModel(Matrix data){
        int varCount = data.colEff-1;
        // Jumlah parameter untuk seluruh variabel konstan, linear, kuadrat, dan interaksi
        int paramCount = 1 + varCount + varCount + varCount*(varCount-1)/2;

        Matrix[] vars = new Matrix[paramCount];
        vars = getVariables(data);

        Matrix model = new Matrix(paramCount, paramCount);
        int i=0, j=0;
        while (i<paramCount){
            j = i;
            while (j<paramCount){
                model.mem[i][j] = vars[i].vectorDot(vars[j]);
                j++;
            }

            // Jadikan matrix model simetris diagonal
            j = 0;
            while (j<i){
                model.mem[i][j] = model.mem[j][i];
                j++;
            }
            i++;
        }
        
        return model;
    }

    // Mengembalikan matrix kolom target regresi
    public static Matrix getTarget(Matrix data){
        int varCount = data.colEff-1;
        // Jumlah parameter untuk seluruh variabel konstan, linear, kuadrat, dan interaksi
        int paramCount = 1 + varCount + varCount + varCount*(varCount-1)/2;

        Matrix[] vars = new Matrix[paramCount];
        vars = getVariables(data);
        int i=0;
        while (i<paramCount){
            vars[i] = data.GetSubMatrix(0, 0, data.rowEff, 1).multiplyElement(vars[i]);
            i++;
        }
        
        Matrix target = new Matrix(paramCount, 1);
        i=0;
        int j=0;
        while (i<paramCount){
            j=0;
            while (j<data.rowEff){
                target.mem[i][0] += vars[i].mem[0][0];
                j++;
            }
            i++;
        }
        return target;
    }

    // Mengembalikan array matrix kolom setiap variabel
    public static Matrix[] getVariables(Matrix data){
        /*
         * vars[0] : Matrix kolom berisi nilai 1
         * vars[1..varCount] : Matrix kolom berisi nilai variabel linear
         * vars[varCount+1..2*varCount] : Matrix kolom berisi nilai variabel kuadrat
         * vars[1+2*varCount..paramCount-1] : Matrix kolom berisi nilai variabel interaksi
         */
        int varCount = data.colEff-1;
        // Jumlah parameter untuk seluruh variabel konstan, linear, kuadrat, dan interaksi
        int paramCount = 1 + varCount + varCount + varCount*(varCount-1)/2;


        Matrix[] vars = new Matrix[paramCount];
        for (int k=0;k<paramCount;k++){
            vars[k] = new Matrix(data.rowEff, 1);
        }
        int i=0;
        // Variabel konstan
        i=0;
        while (i<data.rowEff){
            vars[0].mem[i][0] = 1;
            i++;
        }
        
        int j=0;
        i=0;
        while (i<varCount){
            // Variabel linear
            vars[i+1] = data.GetSubMatrix(0, 1+i, data.rowEff, 1);
            // Variabel kuadrat
            vars[i+1+varCount] = data.GetSubMatrix(0, 1+i, data.rowEff, 1).multiplyElement((vars[i+1]));
            i++;
        }
        // Variabel interaksi
        int ctr = 1+varCount+varCount;
        i=0;j=0;
        while (i<varCount){
            j = i+1;
            while (j<varCount){
                vars[ctr] = data.GetSubMatrix(0, 1+i, data.rowEff, 1);
                vars[ctr] = data.GetSubMatrix(0, 1+j, data.rowEff, 1).multiplyElement(vars[ctr]);
                ctr++;
                j++;
            }
            i++;
        }

        return vars;
    }
    
}
