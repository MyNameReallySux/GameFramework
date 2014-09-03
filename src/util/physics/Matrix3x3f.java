package util.physics;

/**
 * Created by Shorty on 8/22/2014.
 */
public class Matrix3x3f extends Matrix {
    private final String LOG = "Matrix3x3";

    public Matrix3x3f(){
        super(3, 3);
    }

    public Matrix3x3f(Matrix3x3f matrix){
        cells = matrix.cells;
    }

    public Matrix3x3f(float[][] cells){
        super(cells);
    }

    public Matrix3x3f mul(Matrix3x3f b) throws MatrixMathException {
        Matrix3x3f a = this;
        Matrix3x3f result = new Matrix3x3f();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                for(int k = 0; k < rows; k++) {
                    float cellA, cellB;
                    cellA = a.cells[i][k];
                    cellB = b.cells[k][j];
                    result.cells[i][j] += (cellA * cellB);
                }
            }
        }
        return result;
    }

    public Vector2f mul(Vector2f vector){
        Matrix matrix = vector.toMatrix();
        Matrix result = mul(matrix);
        return Matrix.toVector(result);
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
                {x   ,y    , 1.0f}
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
                {1.0f, x   , 0.0f},
                {y   , 1.0f, 0.0f},
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

    /*
    public Matrix3x3f mul(Matrix b, boolean debug) throws MatrixMathException {
        Matrix3x3f a = this;
        Matrix3x3f result = new Matrix3x3f();

        StringBuilder results = new StringBuilder();
        StringBuilder operation = new StringBuilder();
        StringBuilder cellResult = new StringBuilder();

        for(int i = 0; i < a.rows; i++){
            for(int j = 0; j < b.columns; j++){
                operation.append("------===== C[").append(i).append("][").append(j).append("] ======-----\n");
                cellResult.append("C[").append(i).append("][").append(j).append("] = ");
                for(int k = 0; k < b.rows; k++) {
                    float cellA, cellB;

                    cellA = a.cells[i][k];
                    cellB = b.cells[k][j];
                    result.cells[i][j] += (cellA * cellB);

                    operation.append("A[").append(i).append("][").append(k).append("] * ").append("B[").append(k).append("][").append(j).append("] | ");
                    operation.append(a.cells[i][k]).append(" * ").append(b.cells[k][j]).append(" = ").append(a.cells[i][k] * b.cells[k][j]).append("\n");
                    cellResult.append(a.cells[i][k] * b.cells[k][j]).append(" + ");
                }

                cellResult.delete(cellResult.length()- 3, cellResult.length());
                cellResult.append(" = ").append(result.cells[i][j]).append("\n");
                operation.append(cellResult);
                cellResult.delete(0, cellResult.length());
                results.append("Result[").append(i).append("][").append(j).append("] = ").append(result.cells[i][j]).append("\n");
                operation.append("------===== [").append(result.cells[i][j]).append("] ======-----\n\n");
            }

        }
        results.append("\n");
        log(operation.toString());
        log(results.toString());

        return result;
    }
    */
}
