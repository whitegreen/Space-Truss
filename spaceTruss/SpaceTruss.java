package spaceTruss;

import java.text.DecimalFormat;

//Hao Hua, Southeast University, whitegreen@163.com

public class SpaceTruss {  
	DecimalFormat df = new DecimalFormat("###.###");
	private static final int DIM = 3;
	private final double[][] nodes;     
	private final int[][] members;   //  members[2] ={6,8} means the 3rd member links 7th node to 9th node.
	private final double[][] loads;
	private double[] Es; // Young's modulus
	private double[] As; // cross-sectional area
	private final boolean[][] constrained;    //constrained[2]={false, true, false} means the 3rd node is  constrained along y-axis.
	
	private Integer[] ni2ai;// 1D node index (DIM*) to active index
	public String[] symbols;
	public int lenA;
	private double[][] member_cossin;
	private double[][][] member_trans;
	private double[] member_length;
	private double[][] KAA;
	private double[] FA;
	public double[] member_stress;
	public double[] UA;  //nodal displacement arranged in a vector: ux1, uy1,uz1, ux2, uy2,uz2,…
	public double[] member_force;//member compression/tension arranged in a vector
	public double max_stress = 0;

	public SpaceTruss(double[][] nodes, int[][] members, double[][] loads, boolean[][] constrained, double[] As, double[] Es) {
		this.nodes = nodes;
		this.members = members;
		this.loads = loads;
		this.constrained = constrained;
		this.As = As;
		this.Es = Es;
		
		preProcess();
		construct_matrices();
		member_stress();
	}

	private void preProcess() {
		ni2ai = new Integer[DIM * nodes.length];
		lenA= ni2ac(constrained, ni2ai);
		member_cossin = new double[members.length][];
		member_length = new double[members.length];
		for (int i = 0; i < members.length; i++) {
			double[] pa = nodes[members[i][0]];
			double[] pb = nodes[members[i][1]];
			double x = pb[0] - pa[0];
			double y = pb[1] - pa[1];
			double z = pb[2] - pa[2];
			double len = Math.sqrt(x * x + y * y + z * z);
			member_cossin[i] = new double[] { x / len, y / len, z / len };
			member_length[i] = len;
		}
		member_trans=new double[members.length][][];
		for (int i = 0; i < members.length; i++) {
			double x = member_cossin[i][0];
			double y = member_cossin[i][1];
			double z = member_cossin[i][2];
			member_trans[i]= new double[][]{ { x, y, z, 0, 0, 0 }, { 0, 0, 0, x, y, z } };
		}
		
		symbols=new String[lenA];
		for (int i = 0; i < ni2ai.length; i++) {
			if (null != ni2ai[i]) 
				symbols[ni2ai[i]]= string3(i);
		}
	}

	private static int ni2ac(boolean[][] constrained, Integer[] lista) {
		int active_count = 0;
		for (int i = 0; i < constrained.length; i++) {
			if (null == constrained[i]) {  //proceed DIM steps
				for (int k = 0; k < DIM; k++)
					lista[DIM * i + k] = active_count + k;
				active_count += DIM;
				continue;
			}
			for (int k = 0; k < DIM; k++) {
				if (!constrained[i][k]) {
					lista[DIM * i + k] = active_count;
					active_count++;
				}
			}
		}
		return active_count;
	}

	private void construct_matrices() {
		KAA = new double[lenA][lenA];
		double[][] kernel = { { 1, -1 }, { -1, 1 } };
		for (int i = 0; i < members.length; i++) {
			int ida = members[i][0];
			int idb = members[i][1];
			if (ida > idb)
				throw new RuntimeException();
			double ael = As[i] * Es[i] / member_length[i];
			
			double[][] rt= member_trans[i];
			double[][] mat= M.mul(M.transpose(rt), M.mul(kernel, rt));
			mat=M.scale(mat, ael);
			
			for (int m = 0; m < DIM; m++) {// David Hutton p80
				for (int n = 0; n < DIM; n++) {
					assemble(DIM * ida + m, DIM * ida + n, ni2ai, mat[m][n], KAA);
					assemble(DIM * ida + m, DIM * idb + n, ni2ai, mat[m][DIM + n], KAA);
					assemble(DIM * idb + m, DIM * ida + n, ni2ai, mat[DIM + m][n], KAA);
					assemble(DIM * idb + m, DIM * idb + n, ni2ai, mat[DIM + m][DIM + n], KAA);
				}
			}
		}
		FA = new double[lenA];
		for (int i = 0; i < loads.length; i++) {
			if (null == loads[i])
				continue;
			for (int k = 0; k < DIM; k++){
				Integer id = ni2ai[i * DIM + k];
				if (null != id)
					FA[id] = loads[i][k];
			}
		}
		UA = M.solve_square_Axb(KAA, FA);  //use Jama to solve. One might replace this method using other linear solvers.
	}

	private static void assemble(int a, int b, Integer[] ni2ai, double v, double[][] KAA) {
		Integer ia = ni2ai[a];
		Integer ib = ni2ai[b];
		if (null != ia && null != ib) 
			KAA[ia][ib] += v;
	}

	private void member_stress() {
		member_stress=new double[members.length];
		member_force=new double[members.length];
		max_stress=0;
		for (int i = 0; i < members.length; i++) {
			int ida = members[i][0];
			int idb = members[i][1];
			double[] u = new double[2 * DIM];

			Integer aid;
			for (int k = 0; k < DIM; k++) {
				aid = ni2ai[DIM * ida + k];
				if (null != aid)
					u[k] = UA[aid];
				aid = ni2ai[DIM * idb + k];
				if (null != aid)
					u[DIM + k] = UA[aid];
			}
			double[] ae = { -Es[i] / member_length[i], Es[i] / member_length[i] };
			double st = M.dot(ae, M.mul(member_trans[i], u));
			member_stress[i] = st;
			member_force[i]= st*As[i];
			if (max_stress < Math.abs(st))
				max_stress = Math.abs(st);

		}
	}

	
	private static String string3(int i) {
		String t;
		if (0 == i % DIM)
			t = "x";
		else if (1 == i % DIM)
			t = "y";
		else
			t = "z";
		t =  "u"+(i / DIM)+ t;
		return t;
	}
}
