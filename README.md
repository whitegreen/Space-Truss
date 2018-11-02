# Space-Truss
This Space Truss analysis package is based on D. Hutton's Fundamentals of Finite Element Analysis.

![alt text](color.png "Description goes here")

## input
	private final double[][] nodes;     
	private final int[][] members;   //  members[2] ={6,8} means the 3rd member links 7th node to 9th node.
	private final double[][] loads;
	private double[] As; // Young's modulus
	private double[] Es; // cross-sectional area
	private final boolean[][] constrained;    //constrained[2]={false, true, false} means the 3rd node is  constrained along y-axis.

## solve options
s
