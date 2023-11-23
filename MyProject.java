//Aryan Matta (22780982)
// This class implements the 4 given questions. It makes use of a greyscale 2D array, in which 0 is colour black and 255 is colour white with anything in between being a shade of grey
public class MyProject implements Project {

	// performs recursive flood fill operation after checking if the given point is inside the boundaries of the 2D array.
	public int floodFill(int[][] image, int row, int col, int colorToChange) {
		//moves allowed (includes one up, down, left, right from a certain point)
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		
		//fill the image cell with black colour
		image[row][col] = 0;
		
		if(row > 0 && image[row-1][col] == colorToChange) {
			up = floodFill(image, row-1, col, colorToChange);
		}
		
		if(row < image.length-1 && image[row+1][col] == colorToChange) {
			down = floodFill(image, row+1, col, colorToChange);
		}
		
		if(col > 0 && image[row][col-1] == colorToChange) {
			left = floodFill(image, row, col-1, colorToChange);
		}
		
		if(col < image[0].length-1 && image[row][col+1] == colorToChange) {
			right = floodFill(image, row, col+1, colorToChange);
		}
		
		return up + down + left + right + 1;
	}
	
	/**
	 * This function computes the number of changed pixels when a flood-fill is performed of black colour. 
	 * Moves allowed: one up, down, right, left.
	 * 
	 * @param image: grey-scale 2D array
	 * @param row: flood fills from this given row index
	 * @param col: flood fills from this given column index
	 * 
	 * @return: returns the number of pixels changed.
	 */
	public int floodFillCount(int[][]image, int row, int col) {

		//The integer of the colour to be changed to black or 0.
		int colorToChange = image[row][col];

		//If pixel to flood-fill from is already black, we have 0 pixels to fill
		if(colorToChange == 0) {
			return 0;
		}
		
		return floodFill(image, row, col, colorToChange);
	}
	
	/**
	 * This function calculates the total brightness of the brightest exactly 
	 * k*k square that appears in the given image. 
	 * 
	 * @param image: grey-scale 2D array
	 * @param k: the dimension of the squares
	 * @return The total brightness of the brightest square
	 */
	public int brightestSquare(int[][] image, int k) {
		
		int R = image.length;
		int C = image[0].length;

		// fill up sum matrix so that pixelSum[i][j] = sum of pixels in image from (0, 0) to (i, j)
		int[][] pixelSum = new int[R][C];
		pixelSum[0][0] = image[0][0];

		// first row
		for (int j = 1; j < C; j++) {
			pixelSum[0][j] = image[0][j] + pixelSum[0][j - 1];
		}

		// first column
		for (int i = 1; i < R; i++) {
			pixelSum[i][0] = image[i][0] + pixelSum[i - 1][0];
		}

		// fill up rest of the pixelSum
		for (int i = 1; i < R; i++) {
			for (int j = 1; j < C; j++) {
				//pixelSum[i - 1][j] + pixelSum[i][j - 1] contains pixels from row 0 to i-1 and col 0 to j-1 twice,
				// so subtract pixelSum[i - 1][j - 1] to include every pixel only once
				pixelSum[i][j] = image[i][j] + pixelSum[i - 1][j] + pixelSum[i][j - 1] - pixelSum[i - 1][j - 1];
			}
		}

		int squareSum;
		int maxBrightness = Integer.MIN_VALUE;

		// find k*k square having maximum sum.
		// first square will end at (k-1, k-1)
		for (int squareRowEnd = k - 1; squareRowEnd < R; squareRowEnd++)
		{
			for (int squareColEnd = k - 1; squareColEnd < C; squareColEnd++)
			{
				squareSum = pixelSum[squareRowEnd][squareColEnd];

				if (squareRowEnd - k > -1) {
					squareSum = squareSum - pixelSum[squareRowEnd - k][squareColEnd];
				}

				if (squareColEnd - k > -1) {
					squareSum = squareSum - pixelSum[squareRowEnd][squareColEnd - k];
				}

				if (squareRowEnd - k > -1 && squareColEnd - k > -1) {
					squareSum = squareSum + pixelSum[squareRowEnd - k][squareColEnd - k];
				}

				if (squareSum > maxBrightness) {
					maxBrightness = squareSum;
				}
			}
		}

		return maxBrightness;
	}
		
	public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
		return 2;
	}
	
	/**
     * Calculates and returns the answers to the given queries. Queries are a 
     * three element int array {r, l, u} defining a row segment.
     * 
     * 
     * @param image: grey-scale 2D array
     * @param queries: The list of query row segments
     * @return The list of brightest pixels for each query row segment
     */
	public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
		//stores the answers to the given queries
		int[] answer = new int[queries.length];
		
		for(int i = 0; i < queries.length; i++) {
			int row = queries[i][0];
			int scol = queries[i][1];
			int ecol = queries[i][2];

			int maxPixel = Integer.MIN_VALUE;
			//computes the maximum brightness in the given range
			for(int x = scol; x < ecol; x++) {
				if (image[row][x] > maxPixel) {
					maxPixel = image[row][x];
				}
			}

			answer[i] = maxPixel;
		}
		return answer;
	}
}
