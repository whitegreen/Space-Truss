# Space-Truss
This Space Truss analysis package is based on D. Hutton's Fundamentals of Finite Element Analysis.

![alt text](color.png "Description goes here")

## 1. input
double[][] nodes;  //  nodes[2] ={10.2, 2, 7.5} represents the x-y-z coordinates the 3rd node. 

int[][] members;   //  members[2] ={6,8} means the 3rd member links 7th node to 9th node. Here we assame that the start node has a smaller index than the end node.

double[][] loads;  // loads on each node

double[] As; // Young's modulus

double[] Es; // cross-sectional area

boolean[][] constrained;    //constrained[2]={false, true, false} means the 3rd node is constrained along y-axis.

## 2. solve options
The alogirhtm solves a matrix equation Ku=F, where K is the stiffness matrix, u denotes the nodal displacements, and F is the nodal loads.

This libariy use Jama to solve the equation: UA = M.solve_square_Axb(KAA, FA)  in construct_matrices(). 

One might replace this method using other linear solvers (e.g. https://github.com/fommil/matrix-toolkits-java) for a better performance.
