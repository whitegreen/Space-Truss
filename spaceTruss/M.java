package spaceTruss;

import Jama.Matrix;

public class M {
	public static void _add(double[] a, double[] b) {
		for (int i = 0; i < a.length; i++) 
			a[i] += b[i];
	}
	public static double[][] mul(double[][] a, double[][] b) {
		int row = a.length;
		int col = b[0].length;
		double[][] re = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				for (int k = 0; k < b.length; k++)
					re[i][j] += a[i][k] * b[k][j];
			}
		}
		return re;
	}
	public static void _normalize(double[] v) {
		double mag = mag(v);
		for (int i = 0; i < v.length; i++) 
			v[i] /= mag;
	}
	public static double[][] sub(double[][] a, double[][] b) {
		int row = a.length;
		int col = a[0].length;
		double[][] re = new double[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				re[i][j] = a[i][j] - b[i][j];
		return re;
	}
	public static double[][] scale(double[][] a, double s) {
		int row = a.length;
		int col = a[0].length;
		double[][] re = new double[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				re[i][j] = s * a[i][j];
		return re;
	}
	public static void _add(double[][] a, double[][] b) {
		int row = a.length;
		int col = a[0].length;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				a[i][j] += b[i][j];
	}

	public static double[][] add(double[][] a, double[][] b) {
		int row = a.length;
		int col = a[0].length;
		double[][] re = new double[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				re[i][j] = a[i][j] + b[i][j];
		return re;
	}

	public static double[][] transpose(double[][] v) {
		int row = v.length;
		int col = v[0].length;
		double[][] re = new double[col][row];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				re[j][i] = v[i][j];
		return re;
	}
	public static double[] mul(double[][] a, double[] b) {
		double[] re = new double[a.length];
		for (int i = 0; i < re.length; i++) {
			double sum = 0;
			for (int j = 0; j < b.length; j++)
				sum += a[i][j] * b[j];
			re[i] = sum;
		}
		return re;
	}
//	public static double[] mul(double[] a, double[] b) {
//		double sum = 0;
//		for (int i = 0; i < a.length; i++) {
//				sum += a[i] * b[j];
//		}
//		return re;
//	}
	public static double[] solve_square_Axb(double[][] a, double[] b) { // square
		Matrix ma = new Matrix(a);
		Matrix mb = new Matrix(b.length, 1);
		for (int i = 0; i < b.length; i++)
			mb.set(i, 0, b[i]);
		Matrix mx = ma.solve(mb);
		double[] re = new double[b.length];
		for (int i = 0; i < b.length; i++)
			re[i] = mx.get(i, 0);
		return re;
	}

	public static double[] square(double[] c) {
		double x = c[0];
		double y = c[1];
		return new double[] { x * x - y * y, 2 * x * y };
	}

	public static double mag_sq(double[] c) {
		double sum=0;
		for(int i=0;i<c.length;i++)
			sum+=c[i] * c[i];
		return sum;
	}
	
	public static double dist(double[] a, double[] b) {
		double sum=0;
		for(int i=0;i<a.length;i++)
			sum+=(a[i]-b[i]) * (a[i]-b[i]) ;
		return Math.sqrt(sum);
	}

	public static double mag(double[] c) {
		double sum=0;
		for(int i=0;i<c.length;i++)
			sum+=c[i] * c[i];
		return Math.sqrt(sum);
	}

	public static double[] cross(double[] a, double[] b) {
		double x = a[1] * b[2] - a[2] * b[1];
		double y = a[2] * b[0] - a[0] * b[2];
		double z = a[0] * b[1] - a[1] * b[0];
		return new double[] { x, y, z };
	}

	public static double dot(double[] a, double[] b) {
		double sum = 0;
		for (int i = 0; i < a.length; i++)
			sum += a[i] * b[i];
		return sum;
	}
	
	public static double[] add(double[] a,  double[] b) {
		double[] re= new double[a.length];
		for(int i=0;i<a.length;i++)
		  re[i]=a[i]+b[i];
		return re;
	}
	public static double[] sub(double[] a,  double[] b) {
		double[] re= new double[a.length];
		for(int i=0;i<a.length;i++)
		  re[i]=a[i]-b[i];
		return re;
	}
	public static double[] add(double[] a, double s, double[] b, double t) {
		double[] re= new double[a.length];
		for(int i=0;i<a.length;i++)
		  re[i]=a[i]*s+b[i]*t;
		return re;
	}
	
	public static double[] scale(double[] a, double s) {
		double[] re= new double[a.length];
		for(int i=0;i<a.length;i++)
		  re[i]=a[i]*s;
		return re;
	}
	
	public static double[] normalize( double[] a) {
		double[] re= new double[a.length];
		double sum=0;
		for(int i=0;i<a.length;i++)
			sum+=a[i]*a[i];
		double d= Math.sqrt(sum);
		for(int i=0;i<a.length;i++)
			re[i]=a[i]/d;
		return re;
	}
	public static double[] between(double s, double[] a,  double[] b) {
		double[] re= new double[a.length];
		for(int i=0;i<a.length;i++)
		  re[i]=a[i]* (1-s)+b[i]*s;
		return re;
	}
	public static double[] sqRoot(double[] c) {
		double x = c[0];
		double y = c[1];
		double d = Math.sqrt(x * x + y * y);
		double re = Math.sqrt(0.5 * (x + d));
		double im = (y < 0 ? -1 : 1) * Math.sqrt(0.5 * (-x + d));
		return new double[] { re, im };
	}

	public static double[] mul(double[] c1, double[] c2) {
		double a = c1[0];
		double b = c1[1];
		double c = c2[0];
		double d = c2[1];
		return new double[] { a * c - b * d, b * c + a * d };
	}

	public static double[] divide(double[] c1, double[] c2) {
		double a = c1[0];
		double b = c1[1];
		double c = c2[0];
		double d = c2[1];
		double x = (a * c + b * d) / (c * c + d * d);
		double y = (b * c - a * d) / (c * c + d * d);
		return new double[] { x, y };
	}
	public static double[] rot(double cos, double sin, double[] axis, double[] v){
		double[] fst = scale(v, cos);
		double[] scn = scale(axis, (1 - cos) * M.dot(axis, v));
		double[] trd = scale(M.cross(axis, v), sin);
		return add(fst, add(scn, trd));
	}
	public static double[] rot(double ang, double[] axis, double[] v){
		double cos = Math.cos(ang);
		double sin = Math.sin(ang);
		double[] fst = scale(v, cos);
		double[] scn = scale(axis, (1 - cos) * M.dot(axis, v));
		double[] trd = scale(M.cross(axis, v), sin);
		return add(fst, add(scn, trd));
	}
	
	public static double[] ln(double[] z){
		return new double[]{ Math.log(mag(z)),   Math.atan2(z[1], z[0]) };
	}
			
}
