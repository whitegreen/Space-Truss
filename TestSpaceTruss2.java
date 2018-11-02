package test;

import java.text.DecimalFormat;
import spaceTruss.SpaceTruss;

public class TestSpaceTruss2 {
	private DecimalFormat df = new DecimalFormat("###.###");

	public static void main(String[] args) {
		new TestSpaceTruss2();
	}

	public TestSpaceTruss2() {
		double[][] nodes = new double[][] { { 40, 0, 0 }, { 0, 0, 30 }, { 0, 0, -30 }, { 0, -30, 0 } }; // Hutton p81
		int[][] members = new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 } }; // node ids
		double[][] loads = new double[nodes.length][];
		loads[0] = new double[] { 0, -5000, 0 };
		boolean[][] constrained = new boolean[nodes.length][];
		constrained[1] = new boolean[] { true, true, true }; // RX, RY, RZ;
		constrained[2] = new boolean[] { true, true, true };
		constrained[3] = new boolean[] { true, true, true };
		double[] As = new double[members.length]; // Young's modulus
		double[] Es = new double[members.length]; // cross-sectional area
		for (int i = 0; i < members.length; i++) {
			As[i] = 1;
			Es[i] = 1;
		}

		SpaceTruss truss = new SpaceTruss(nodes, members, loads, constrained, As, Es);

		System.out.println("nodal displacement:");
		for (int i = 0; i < truss.lenA; i++) {
			double u = truss.UA[i];
			System.out.println(truss.symbols[i] + "   " + df.format(u));
		}

		System.out.println("member forces:");
		for (int i = 0; i < members.length; i++) {
			System.out.println("(" + members[i][0] + "," + members[i][1] + "):" + df.format(truss.member_force[i]));
		}
	}

}
