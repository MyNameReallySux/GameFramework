package old;

import util.Log;
import util.physics.Vector2f;

/**
 * Created by Shorty on 8/20/2014.
 */
public class Matrix3x3f implements Log{
    private static final int COLUMNS = 3;
    private static final int ROWS = 3;
    private float[][] m;

    public Matrix3x3f(float[][] m){
       this.m = m;
    }

    public Matrix3x3f(){
        m = new float[COLUMNS][ROWS];
    }

    public static void main(String args[]){
        Matrix3x3f matrix1 = new Matrix3x3f();
        matrix1.m[0][0] = 1;
        matrix1.m[0][1] = 1;
        matrix1.m[0][2] = 1;
        matrix1.m[1][0] = 2;
        matrix1.m[1][1] = 2;
        matrix1.m[1][2] = 2;
        matrix1.m[2][0] = 3;
        matrix1.m[2][1] = 3;
        matrix1.m[2][2] = 3;

        Matrix3x3f matrix2 = new Matrix3x3f();
        matrix2.m[0][0] = 1;
        matrix2.m[0][1] = 2;
        matrix2.m[0][2] = 3;
        matrix2.m[1][0] = 1;
        matrix2.m[1][1] = 2;
        matrix2.m[1][2] = 3;
        matrix2.m[2][0] = 1;
        matrix2.m[2][1] = 2;
        matrix2.m[2][2] = 3;


        Matrix3x3f matrix3 = matrix1.mul(matrix2);

        Vector2f vector2f = new Vector2f(1, 1);
        Vector2f vector2f2 = new Vector2f(2, 1);
        Vector2f vector2f3 = new Vector2f(1, 2);
        Vector2f vector2f4 = new Vector2f(2, 2);

        matrix1.mul(vector2f);
        matrix1.mul(vector2f2);
        matrix1.mul(vector2f3);
        matrix1.mul(vector2f4);

        Matrix3x3f.identity().mul(vector2f);
        Matrix3x3f.identity().mul(vector2f2);
        Matrix3x3f.identity().mul(vector2f3);
        Matrix3x3f.identity().mul(vector2f4);
    }

    public Matrix3x3f add(Matrix3x3f b){
        Matrix3x3f a = this;
        Matrix3x3f result = new Matrix3x3f();
        for(int i = 0; i < COLUMNS; i++){
            for(int j = 0; j < ROWS; j++){
                result.m[i][j] = a.m[i][j] + b.m[i][j];
            }
        }
        return result;
    }

    public Matrix3x3f sub(Matrix3x3f b){
        Matrix3x3f a = this;
        Matrix3x3f result = new Matrix3x3f();
        for(int i = 0; i < COLUMNS; i++){
            for(int j = 0; j < ROWS; j++){
                result.m[i][j] = a.m[i][j] - b.m[i][j];
            }
        }
        return result;
    }

    public Matrix3x3f mul(Matrix3x3f b){
        Matrix3x3f a = this;
        Matrix3x3f result = new Matrix3x3f();

        StringBuilder results = new StringBuilder();
        StringBuilder formula = new StringBuilder();
        StringBuilder operation = new StringBuilder();
        StringBuilder cellResult = new StringBuilder();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                cellResult.append("C{");
                for(int k = 0; k < ROWS; k++) {
                    result.m[i][j] += (a.m[i][k] * b.m[k][j]);
                    formula.append("A[").append(i).append("][").append(k).append("] * ").append("B[").append(k).append("][").append(j).append("]\n");
                    operation.append("A[").append(a.m[i][k]).append("] * ").append("B[").append(b.m[k][j]).append("] = ").append(a.m[i][k] * b.m[k][j]).append("\n");
                    cellResult.append(a.m[i][k] * b.m[k][j]).append(" + ");
                }
                cellResult.delete(cellResult.length()- 3, cellResult.length());
                cellResult.append("] = ").append(result.m[i][j]).append("\n\n");
                operation.append(cellResult);
                cellResult.delete(0, cellResult.length());
                results.append("Result[").append(i).append("][").append(j).append("] = ").append(result.m[i][j]).append("\n");
            }

        }
        results.append("\n");
        log(operation.toString());
        log(results.toString());

        return result;
    }

    public static Matrix3x3f zero(){
        return new Matrix3x3f(new float[][]{
                {0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f}
        });
    }

    public static Matrix3x3f identity(){
        return new Matrix3x3f(new float[][]{
                {1.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 1.0f}
        });
    }

    public static Matrix3x3f translate(float x, float y){
        return new Matrix3x3f(new float[][]{
                {1.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f},
                {   x,    y, 1.0f}
        });
    }

    public static Matrix3x3f scale(float x, float y){
        return new Matrix3x3f(new float[][]{
                {x   , 0.0f, 0.0f},
                {0.0f, y   , 0.0f},
                {0.0f,0.0f , 1.0f}
        });
    }

    public static Matrix3x3f shear(float x, float y){
        return new Matrix3x3f(new float[][]{
                {1.0f, y   , 0.0f},
                {x   , 1.0f, 0.0f},
                {0.0f,0.0f , 1.0f}
        });
    }

    public static Matrix3x3f rotate(float rad){
        return new Matrix3x3f(new float[][]{
                {(float) Math.cos(rad), (float)Math.sin(rad) , 0.0f},
                {(float)-Math.sin(rad), (float)Math.cos(rad) , 0.0f},
                {0.0f                 ,0.0f                  , 1.0f}
        });
    }

    public Vector2f mul(Vector2f vector2f){
        float[] vector = new float[]{vector2f.x, vector2f.y, vector2f.w};
        float[] result = new float[3];
        StringBuilder operations = new StringBuilder();
        StringBuilder cellResult = new StringBuilder();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                result[i] += vector[j] * m[j][i];
                operations.append(vector[j]).append(" * ").append(m[j][i]).append(" = ").append(vector[j] * m[j][i]).append("\n");
                cellResult.append(vector[j] * m[j][i] + " + ");
            }
            cellResult.delete(cellResult.length() - 3, cellResult.length());
            cellResult.append(" = ").append(result[i]).append("\n\n");
            operations.append(cellResult);
            cellResult.delete(0, cellResult.length());
        }
        log(operations.toString());
        log("Result: " + new Vector2f(result) + "\n");
        return new Vector2f(result);
    }

    public String toString(){
        return "[" + m[0][0] + ", " + m[0][1] + ", " + m[0][2] + "]\n" +
               "[" + m[1][0] + ", " + m[1][1] + ", " + m[1][2] + "]\n" +
               "["+ m[2][0] + ", " + m[2][1] + ", " + m[2][2] + "]";
    }
}
