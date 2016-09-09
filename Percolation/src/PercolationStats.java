
public class PercolationStats { 
	private double[] result;
	
	public PercolationStats(int N, int T) { 
		// perform T independent experiments on an N-by-N grid
		if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
		
		result = new double[T];

		for (int t = 0; t < T; t++) { 
			int count = 0;

			Percolation p = new Percolation(N);
			int[] index = new int[N*N];
			for (int i = 0; i < N*N; i++) { 
				index[i] = i;
			}

			StdRandom.shuffle(index, 0, N*N-1);

			while (!p.percolates()) { 
				int i = index[count] / N + 1;
				int j = index[count] % N + 1;
				count++;
				p.open(i, j);
			}

			result[t] = count*1.0 / (N*N); 
		}
	}

	public double mean() { 
		// sample mean of percolation threshold
		double total = 0;
		for (int i = 0; i < result.length; i++) 
			total += result[i];
		return total / result.length;
	}

	public double stddev() { 
		// sample standard deviation of percolation threshold
		double total = 0.0;
		for (int i = 0; i < result.length; i++) 
			total += (result[i]-mean())*(result[i]-mean());
		double var = total / (result.length - 1);
		return Math.sqrt(var);
	}

	public double confidenceLo() { 
		// low endpoint of 95% confidence interval
		return mean()-1.96*stddev()/Math.sqrt(result.length);
	}

	public double confidenceHi() { 
		// high endpoint of 95% confidence interval
		return mean()+1.96*stddev()/Math.sqrt(result.length);
	}

	public static void main(String[] args) {  
		// test client (described below)
		PercolationStats ps = new PercolationStats(200, 100);
		System.out.println(ps.mean());
		System.out.println(ps.stddev());
		System.out.println(ps.confidenceLo());
		System.out.println(ps.confidenceHi());
	}
}
