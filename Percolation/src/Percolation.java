import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
	private boolean[][] grid;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	
	public Percolation(int N) {
		// create N-by-N grid, with all sites blocked
		if (N <= 0) throw new java.lang.IllegalArgumentException("invalid N");
		grid = new boolean[N][N];
		uf = new WeightedQuickUnionUF(N*N+2);
		uf2 = new WeightedQuickUnionUF(N*N+1);
	}
	
	public void open(int i, int j) {
		// open site (row i, column j) if it is not open already
		int width = grid[0].length;
		if (i < 1 || i > width) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j < 1 || j > width) throw new IndexOutOfBoundsException("row index j out of bounds");

		grid[i-1][j-1] = true;
		
		if (i != 1) if (isOpen(i-1, j)) uf.union((i-1)*width+j-1, (i-2)*width+j-1);
		if (i != width) if (isOpen(i+1, j)) uf.union((i-1)*width+j-1, (i)*width+j-1);
		if (j != 1) if (isOpen(i, j-1)) uf.union((i-1)*width+j-1, (i-1)*width+j-2);
		if (j != width) if (isOpen(i, j+1)) uf.union((i-1)*width+j-1, (i-1)*width+j);
		
		if (i != 1) if (isOpen(i-1, j)) uf2.union((i-1)*width+j-1, (i-2)*width+j-1);
		if (i != width) if (isOpen(i+1, j)) uf2.union((i-1)*width+j-1, (i)*width+j-1);
		if (j != 1) if (isOpen(i, j-1)) uf2.union((i-1)*width+j-1, (i-1)*width+j-2);
		if (j != width) if (isOpen(i, j+1)) uf2.union((i-1)*width+j-1, (i-1)*width+j);
		
		if (i == 1) {
			uf.union((i-1)*width+j-1, width*width);
			uf2.union((i-1)*width+j-1, width*width);
		}
		if (i == width) uf.union((i-1)*width+j-1, width*width+1);
	}
	
	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		if (i < 1 || i > grid[0].length) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j < 1 || j > grid[0].length) throw new IndexOutOfBoundsException("row index j out of bounds");

		return grid[i-1][j-1];
	}
	
	public boolean isFull(int i, int j) { 
		// is site (row i, column j) full?
		int width = grid[0].length;
		if (i < 1 || i > width) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j < 1 || j > width) throw new IndexOutOfBoundsException("row index j out of bounds");
		
		return uf2.connected((i-1)*width+j-1, width*width);
	}
	
	public boolean percolates() { 
		// does the system percolate?
		int width = grid[0].length;
		return uf.connected(width*width, width*width+1);
	}
	
	public static void main(String[] args) { 
		// test client (optional)
		Percolation p = new Percolation(8);
		p.open(1, 1);
		p.open(2, 1);
		p.open(3, 1);
		p.open(3, 2);
		p.open(3, 3);
		p.open(4, 3);
		p.open(5, 3);
		p.open(6, 3);
		p.open(7, 3);
		p.open(8, 3);


		System.out.println(p.isOpen(3, 3));
		System.out.println(p.isOpen(3, 4));

		System.out.println(p.isFull(3, 3));
		System.out.println(p.isFull(3, 4));

		System.out.println(p.percolates());
	}
}
