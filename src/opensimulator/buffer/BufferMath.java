/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opensimulator.buffer;

/**
 *
 * @author azalac
 */
public final class BufferMath {

    public static double[] AsArray(DoubleBuffer b, int index, int n) {

        double[] out = new double[n];

        for (int i = 0; i < n; i++) {
            out[i] = b.get(index + i);
        }

        return out;
    }

    public static void Copy(DoubleBuffer from, int fromindex, double[] to, int toindex, int n) {

        if (toindex + n > to.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < n; i++) {
            to[i + toindex] = from.get(i + fromindex);
        }

    }

    public static void Copy(double[] from, int fromindex, DoubleBuffer to, int toindex, int n) {

        if (fromindex + n > from.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < n; i++) {
            to.set(i + toindex, from[i + fromindex]);
        }

    }

    public static void Put(double[] into, double value) {
        for (int i = 0; i < into.length; i++) {
            into[i] = value;
        }
    }

    public static double[] Duplicate(double[] a) {
        double[] out = new double[a.length];

        System.arraycopy(a, 0, out, 0, a.length);

        return out;
    }

    public static void Normalize(DoubleBuffer b, int index, int n, double[] out) {

        if (n < out.length) {
            throw new IllegalArgumentException("out.length must be >= n");
        }

        double l = 0;

        for (int i = 0; i < n; i++) {
            out[i] = b.get(index + i);
            l += out[i] * out[i];
        }

        // no length, divide by zero = NaN
        if (l == 0) {
            Put(out, Double.NaN);
            return;
        }

        // already normalized
        if (l == 1) {
            return;
        }

        l = Math.sqrt(l);

        for (int i = 0; i < n; i++) {
            out[i] /= l;
        }

    }

    public static void Normalize(double[] b, double[] out) {

        if (b.length != out.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        double l = 0;

        for (int i = 0; i < b.length; i++) {
            l += b[i] * b[i];
        }

        // no length, divide by zero = NaN
        if (l == 0) {
            Put(out, Double.NaN);
            return;
        }

        // already normalized
        if (l == 1) {
            System.arraycopy(b, 0, out, 0, b.length);
            return;
        }

        l = Math.sqrt(l);

        for (int i = 0; i < b.length; i++) {
            out[i] = b[i] / l;
        }

    }

    public static double Dot(DoubleBuffer a, int ia, DoubleBuffer b, int ib, int n) {
        double sum = 0;

        for (int i = 0; i < n; i++) {
            sum += a.get(ia + i) * b.get(ib + i);
        }

        return sum;
    }

    public static double Dot(double[] a, double[] b) {

        double sum = 0;

        if (a.length != b.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }

        return sum;
    }

    public static void Add(DoubleBuffer a, int ia, DoubleBuffer b, int ib, int n, double[] out) {

        if (n < out.length) {
            throw new IllegalArgumentException("out.length must be >= n");
        }

        for (int i = 0; i < n; i++) {
            out[i] = a.get(ia + i) + b.get(ib + i);
        }

    }

    public static void Add(double[] a, double[] b, double[] out) {

        if (a.length != b.length || a.length != out.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            out[i] = a[i] + b[i];
        }

    }

    public static void Sub(DoubleBuffer a, int ia, DoubleBuffer b, int ib, int n, double[] out) {

        if (n < out.length) {
            throw new IllegalArgumentException("out.length must be >= n");
        }

        for (int i = 0; i < n; i++) {
            out[i] = a.get(ia + i) - b.get(ib + i);
        }

    }

    public static void Sub(double[] a, double[] b, double[] out) {

        if (a.length != b.length || a.length != out.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            out[i] = a[i] - b[i];
        }

    }

    /**
     * out = A * B + C
     */
    public static void MultiplyAdd(DoubleBuffer a, int ia, DoubleBuffer b, int ib, DoubleBuffer c, int ic, int n, double[] out) {

        if (n < out.length) {
            throw new IllegalArgumentException("out.length must be >= n");
        }

        for (int i = 0; i < n; i++) {
            out[i] = a.get(ia + i) * b.get(ib + i) + c.get(ic + i);
        }

    }

    /**
     * out = A * B + C
     */
    public static void MultiplyAdd(double[] a, double[] b, double[] c, double[] out) {

        if (a.length != b.length || a.length != c.length || a.length != out.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            out[i] = a[i] * b[i] + c[i];
        }

    }

    /**
     * out = A * B + C
     */
    public static void ScaleAdd(DoubleBuffer a, int ia, double b, DoubleBuffer c, int ic, int n, double[] out) {

        if (n < out.length) {
            throw new IllegalArgumentException("out.length must be >= n");
        }

        for (int i = 0; i < n; i++) {
            out[i] = a.get(ia + i) * b + c.get(ic + i);
        }

    }

    /**
     * out = A * B + C
     */
    public static void ScaleAdd(double[] a, double b, double[] c, double[] out) {

        if (a.length != c.length || a.length != out.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            out[i] = a[i] * b + c[i];
        }

    }

    public static void Scale(DoubleBuffer a, int ia, double s, int n, double[] out) {

        if (n < out.length) {
            throw new IllegalArgumentException("out.length must be >= n");
        }

        for (int i = 0; i < n; i++) {
            out[i] = a.get(ia + i) * s;
        }

    }

    public static void Scale(double[] a, double s, double[] out) {

        if (a.length != out.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            out[i] = a[i] * s;
        }

    }

    /**
     * @return (a - b) * (a - b)
     */
    public static double Length2Sub(double[] a, double[] b) {

        if (a.length != b.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            double x = a[i] - b[i];
            sum += x * x;
        }

        return sum;

    }

    public static double Length2(double[] a) {
        return Dot(a, a);
    }

    public static void Clamp(double[] a, double[] minimum, double[] maximum) {

        if (a.length != minimum.length || a.length != maximum.length) {
            throw new IllegalArgumentException("Invalid input sizes");
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i] < minimum[i]) {
                a[i] = minimum[i];
            }

            if (a[i] > maximum[i]) {
                a[i] = maximum[i];
            }
        }

    }

}
