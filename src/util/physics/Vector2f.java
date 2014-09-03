package util.physics;

/**
 * Created by Shorty on 8/11/2014.
 */
public class Vector2f {
    public float x, y, w;

    public Vector2f(){
        this(0.0f, 0.0f, 1.0f);
    }

    public Vector2f(float x, float y){
        this(x, y, 1.0f);
    }

    public Vector2f(float[] array){
        this(array[0], array[1], array[2]);
    }

    public Vector2f(Vector2f vector){
        this(vector.x, vector.y, vector.w);
    }

    public Vector2f(float x, float y, float w){
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public Vector2f mul(Matrix3x3f matrix){
        Matrix vector = toMatrix();
        Matrix result = vector.mul(matrix);
        return Matrix.toVector(result);
    }

    public void translate(float tx, float ty){
        x += tx;
        y += ty;
    }

    public void scale(float s){
        scale(s, s);
    }

    public void scale(float sx, float sy){
        x *= sx;
        y *= sy;
    }

    public void rotate(float rad){
        float temp = (float)(x*Math.cos(rad) - y*Math.sin(rad));
        y = (float)(x*Math.sin(rad) + y*Math.cos(rad));
        x = temp;
    }

    public void shear(float sx, float sy){
        float temp = x + sx * y;
        y = y + sy * x;
        x = temp;
    }

    public Matrix toMatrix(){
        Matrix matrix = new Matrix(1, 3);
        matrix.fill(x, y, w);
        return matrix;
    }

    public static Matrix toMatrix(Vector2f vector){
        Matrix matrix = new Matrix(1, 3);
        matrix.fill(vector.x, vector.y, vector.w);
        return matrix;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("V[ \t ").append(x).append(" \t ").append(y).append(" \t ").append(w).append(" \t ]");
        return sb.toString();
    }
}
