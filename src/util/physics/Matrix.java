package util.physics;

import util.Log;

import java.util.Random;

/**
 * Created by Shorty on 8/22/2014.
 */
public class Matrix implements Log {
    private final String LOG = "Matrix";

    protected int rows;
    protected int columns;
    protected float[][] cells;

    public Matrix() {
        cells = Matrix.identity(2).cells;
    }

    public Matrix(float[][] cells){
        this.cells = cells;
        this.rows = getRows();
        this.columns = getColumns();
    }

    public Matrix(Matrix matrix){
        this(matrix.cells);
    }

    public Matrix(int size){
        this(new float[size][size]);
    }

    public Matrix(int rows, int columns){
        this(new float[rows][columns]);
    }

    public int getRows(){
        return this.cells.length;
    }

    public int getColumns(){
        return this.cells[0].length;
    }

    public static void main(String[] args){
        Matrix3x3f m = new Matrix3x3f();
        Matrix3x3f n = new Matrix3x3f();
        Vector2f v = new Vector2f(2, 3);

        Log.print(m.getRows() + "X" + m.getColumns());

        m.fill(1, 1, 1, 1, 3, 1, 1, 1, 1);
        n.randomFill(3);

        Vector2f x = m.mul(v);

        m.show();
        n.show();
    }

    public void fill(float... args){
        int i = 0, j = 0;
        for(float num: args){
            if(cells[i][j] == 0) {
                cells[i][j] = num;
            }
            j++;
            if(j >= columns){
                j = 0;
                i++;
            }
            if(i >= rows){
                break;
            }
        }
    }
    public void fillAll(float value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = value;
            }
        }
    }
    public void randomFill(int max){
        Random random = new Random();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                cells[i][j] = random.nextInt(max + 1);
            }
        }
    }

    public boolean isSquare(){
        return getColumns() == getRows();
    }
    public boolean isSameSize(Matrix matrix){
        return this.getColumns() == matrix.getColumns() && this.getRows() == matrix.getRows();
    }
    public boolean isCrossSize(Matrix matrix){
        return this.getColumns() == matrix.getRows();
    }

    public Matrix add(Matrix b) throws MatrixMathException {
        Matrix a = this;
        if(!a.isSameSize(b)) throw new MatrixMathException("Matrices must be same size to be added together.");
        Matrix result = new Matrix(a.getRows(), b.getColumns());
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                float cellA, cellB, cellC;

                cellA = a.cells[i][j];
                cellB = b.cells[i][j];
                cellC = cellA + cellB;
                result.cells[i][j] = cellC;
            }
        }
        return result;
    }

    public Matrix sub(Matrix b) throws MatrixMathException {
        Matrix a = this;
        if(!a.isSameSize(b)) throw new MatrixMathException("Matrices must be same size to be subtracted from.");
        Matrix result = new Matrix(a.getRows(), b.getColumns());
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                float cellA, cellB;

                cellA = a.cells[i][j];
                cellB = b.cells[i][j];
                result.cells[i][j] = cellA - cellB;
            }
        }
        return result;
    }

    public Matrix mul(Matrix b) throws MatrixMathException {
        Matrix a = this;
        if(!a.isCrossSize(b)) throw new MatrixMathException("First Matrix's rows must be equal to second Matrix's columns to be multiplied..");
        Matrix result = new Matrix(a.getRows(), b.getColumns());

        for(int i = 0; i < a.rows; i++){
            for(int j = 0; j < b.columns; j++){
                for(int k = 0; k < b.rows; k++) {
                    float cellA, cellB;
                    cellA = a.cells[i][k];
                    cellB = b.cells[k][j];
                    result.cells[i][j] += (cellA * cellB);
                }
            }
        }
        return result;
    }

    public static Matrix zero(int size){
        return zero(size, size);
    }

    public static Matrix zero(int rows, int columns){
        Matrix matrix = new Matrix(new float[columns][rows]);
        matrix.fillAll(0);
        return matrix;
    }

    public static Matrix identity(int size){
        Matrix matrix = new Matrix(new float[size][size]);
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i == j) matrix.cells[i][j] = 1;
            }
        }
        return matrix;
    }

    public static Matrix translate(int size, float... params){
        Matrix matrix = Matrix.identity(size);
        if(params.length != size) throw new MatrixMathException("Parameters must be equal to 1 less than the size of the Matrix");
        for(int i = size - 1; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix.cells[i][j] = params[j];
            }
        }
        return matrix;
    }

    public static Matrix scale(int size, float... params){
        Matrix matrix = Matrix.identity(size);
        if(params.length != size) throw new MatrixMathException("Parameters must be equal to 1 less than the size of the Matrix");
        for(int i = 0, k = 0; i < size - 1; i++){
            for(int j = 0; j < size; j++){
                if(i == j) {
                    matrix.cells[i][j] = params[k];
                    k++;
                }
            }
        }
        return matrix;
    }

    public static Matrix shear(float x, float y){
        return new Matrix(new float[][]{
                {1.0f, y   , 0.0f},
                {x   , 1.0f, 0.0f},
                {0.0f,0.0f , 1.0f}
        });
    }

    public static Matrix rotate(float rad){
        return new Matrix(new float[][]{
                {(float) Math.cos(rad), (float)Math.sin(rad) , 0.0f},
                {(float)-Math.sin(rad), (float)Math.cos(rad) , 0.0f},
                {0.0f                 ,0.0f                  , 1.0f}
        });
    }

    public void show(){
        StringBuilder results = new StringBuilder();
        results.append("|");
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                float cell = cells[i][j];
                results.append(" \t ").append(cell);
            }
            results.append(" \t |\n|");
        }
        results.delete(results.length() - 1, results.length());
        log(results.toString());
    }

    public static Vector2f toVector(Matrix matrix) {
        if (matrix.getRows() == 1 && matrix.getColumns() == 3) {
            return new Vector2f(matrix.cells[0][0], matrix.cells[0][1], matrix.cells[0][2]);
        } else {
            return new Vector2f();
        }
    }

    public String toString(){
        StringBuilder results = new StringBuilder();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                float cell = cells[i][j];
                results.append("C[").append(i).append("][").append(j).append("] = ").append(cell).append("\n");
            }
        }
        return results.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Matrix)) return false;
        Matrix matrix = (Matrix)obj;
        if(!isSameSize(matrix)) return false;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(cells[i][j] != matrix.cells[i][j]) return false;
            }
        }
        return true;
    }

    /*
    public Matrix add(Matrix b, boolean debug) throws MatrixMathException {
        StringBuilder results = new StringBuilder();
        StringBuilder operation = new StringBuilder();
        StringBuilder cellResult = new StringBuilder();

        Matrix a = this;
        if(!a.isSameSize(b)) throw new MatrixMathException("Matrices must be same size to be added together.");
        Matrix result = new Matrix(a.getRows(), b.getColumns());
        for(int i = 0; i < columns; i++){
            cellResult.append("C{");
            for(int j = 0; j < rows; j++){
                float cellA, cellB;

                cellA = a.cells[i][j];
                cellB = b.cells[i][j];
                result.cells[i][j] = cellA + cellB;

                operation.append("A[").append(i).append("][").append(j).append("] + ").append("B[").append(i).append("][").append(j).append("] | ");
                operation.append(cellA).append(" + ").append(cellB).append(" = ").append(cellA + cellB).append("\n");
                results.append("C[").append(i).append("][").append(j).append("] = ").append(result.cells[i][j]).append("\n");
            }
        }
        log(operation.toString());
        log(results.toString());
        return result;
    }
    public Matrix mul(Matrix b, boolean debug) throws MatrixMathException {
        Matrix a = this;
        if(!a.isCrossSize(b)) throw new MatrixMathException("Matrices must be cross sized to be multiplied.");
        Matrix result = new Matrix(a.getColumns(), b.getRows());

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
