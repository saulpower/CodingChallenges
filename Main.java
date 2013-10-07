package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[][] grid = {{8, 2, 4},
                        {0, 7, 1},
                        {3, 7, 9}};

        int longest = 0;
        
        for (int row = 0; row < grid.length; row++) {

            for (int column = 0; column < grid[row].length; column++) {

                int[][] visited = new int[3][3];
                int path = getLongestPath(grid, visited, row, column, grid[row][column], 1);

                if (path > longest) {
                    longest = path;
                }
            }
        }

        System.out.println(String.format("Longest Path: %d", longest));
    }

    /**
     * Does account for the same lowest number in multiple adjacent cells
     *
     * @param matrix
     * @param row
     * @param column
     * @param previous
     * @return
     */
    private static int getLongestPath(int[][] matrix, int[][] visited, int row, int column, int previous, int position) {

        visited[row][column] = position;

        int length = 0;
        int next = -1;

        int changeRow = -1;
        int changeColumn = -2;

        for (int i = 0; i < 9; i++) {

            if (i % 3 == 0) {
                changeRow = -1;
                changeColumn++;
            }

            int checkRow = row + changeRow;
            int checkColumn = column + changeColumn;

            if (!(checkRow == row && checkColumn == column)) {

                int value = getValue(matrix, checkRow, checkColumn);

                if (value != -1 && visited[checkRow][checkColumn] == 0 && value >= previous && (value < next || next == -1)) {

                    int tempLength = getLongestPath(matrix, cloneArray(visited), checkRow, checkColumn, value, position + 1);

                    if (tempLength > length) {
                        length = tempLength;
                        next = value;
                    }
                }
            }

            changeRow++;
        }

        if (next == -1) {
            System.out.println(String.format("Path: %d = %s", position, Arrays.deepToString(visited)));
            return 1;
        }

        return length + 1;
    }

    private static int getValue(int[][] matrix, int row, int column) {

        if (row < 0 || column < 0 || row >= matrix.length || column >= matrix[row].length) return -1;

        return matrix[row][column];
    }

    public static int[][] cloneArray(int[][] src) {

        int length = src.length;
        int[][] target = new int[length][src[0].length];

        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }

        return target;
    }
}
