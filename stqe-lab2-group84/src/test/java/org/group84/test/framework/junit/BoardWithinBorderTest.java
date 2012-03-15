package org.group84.test.framework.junit;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.jpacman.framework.model.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test voor de Board.withinBorders methode,
 * met de Parametrized data methode.
 */
@RunWith(Parameterized.class)
public class BoardWithinBorderTest {
	private static final int WIDTH = 5, HEIGHT = 3;
	
	private Board board;
	private int x;
	private int y;
	private boolean isWithinBorder;

	public BoardWithinBorderTest(int x, int y, boolean isWithinBorder) {
		this.x = x;
		this.y = y;
		this.isWithinBorder = isWithinBorder;
		board = new Board(WIDTH, HEIGHT);
	}
	
	@Test
	public void testWithinBorders() {
		assertEquals(board.withinBorders(x, y), isWithinBorder);
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		/*
		 * 3-Tuples of target coordinates (x, y),
		 * and the expected result of the within-borders check.
		 */
		Object[][] data = new Object[][] {
				{  0,  1, true},
				{ -1,  1, false},
				{  5,  1, false},
				{  4,  1, true},
				{  1,  0, true},
				{  1, -1, false},
				{  1,  3, false},
				{  1,  2, true},
		};
		
		return Arrays.asList(data);
	}
}