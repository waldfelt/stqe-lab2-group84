package org.group84.test.framework.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jpacman.framework.model.Board;
import org.jpacman.framework.model.Direction;
import org.jpacman.framework.model.Food;
import org.jpacman.framework.model.Ghost;
import org.jpacman.framework.model.IBoardInspector.SpriteType;
import org.jpacman.framework.model.Tile;
import org.jpacman.framework.model.Wall;
import org.junit.Before;
import org.junit.Test;

// bij alle methods van JPacman is de volgorde van coordinaten (x, y).

/**
 * Tests voor alle publieke methods van klasse model.Board.
 */
public class BoardTest {
	private static final int W = 5, H = 3; // board width, height
	
	private Board board;
	
	/**
	 * De configuratie wordt:
	 * (zie factory.MapParser voor mapping chars->sprite types)
	 * [#   .]
	 * [#    ]
	 * [##   ]
	 */
	@Before
	public void setUp() {
		board = new Board(W, H);
		board.put(new Wall(), 0, 0);
		board.put(new Wall(), 0, 1);
		board.put(new Wall(), 0, 2);
		board.put(new Wall(), 1, 2);
		board.put(new Food(), 4, 0);
	}

	/**
	 * Test die correctheid verifieert van getters getWidth, getHeight.
	 */
	@Test
	public void testGetWidthAndHeight() {
		assertEquals(board.getWidth(),  W);
		assertEquals(board.getHeight(), H);
	}

	/**
	 * Test voor methode withinBorders, zie ook BoardWithinBorderTest.
	 */
	@Test
	public void testWithinBorders() {
		// hoekpunten liggen op het bord
		assertTrue(board.withinBorders(    0,     0));
		assertTrue(board.withinBorders(    0, H - 1));
		assertTrue(board.withinBorders(W - 1,     0));
		assertTrue(board.withinBorders(W - 1, H - 1));
		// x=-1 of x=W of y=-1 of y=H => buiten het bord
		assertFalse(board.withinBorders( 0,  H));
		assertFalse(board.withinBorders( 0, -1));
		assertFalse(board.withinBorders( W,  0));
		assertFalse(board.withinBorders(-1,  0));
	}
	
	/**
	 * Test voor methode spriteAt.
	 */
	@Test
	public void testSpriteAt() {
		assertEquals(board.spriteAt(0, 0).getSpriteType(),
				SpriteType.WALL);
		assertEquals(board.spriteAt(0, 1).getSpriteType(),
				SpriteType.WALL);
		assertEquals(board.spriteAt(0, 2).getSpriteType(),
				SpriteType.WALL);
		
		assertTrue(board.spriteAt(2, 2) == null);
		assertTrue(board.spriteAt(3, 2) == null);
		
		assertEquals(board.spriteAt(4, 0).getSpriteType(),
				SpriteType.FOOD);
	}

	/**
	 * Basic test voor methode spriteTypeAt.
	 */
	@Test
	public void testSpriteTypeAt() {
		assertEquals(board.spriteTypeAt(0, 0), SpriteType.WALL);
		assertEquals(board.spriteTypeAt(0, 1), SpriteType.WALL);
		assertEquals(board.spriteTypeAt(0, 2), SpriteType.WALL);
		assertEquals(board.spriteTypeAt(1, 2), SpriteType.WALL);
		
		assertEquals(board.spriteTypeAt(2, 2), SpriteType.EMPTY);
		
		assertEquals(board.spriteTypeAt(4, 0), SpriteType.FOOD);
	}
	
	/**
	 * Test of spriteTypeAt juist omgaat met meerdere sprites per tile.
	 */
	@Test
	public void testSpriteTypeAt_replace() {
		// nieuwe sprite op deze positie wordt de bovenste
		board.put(new Ghost(), 4, 0);
		assertEquals(board.spriteTypeAt(4, 0), SpriteType.GHOST);
	}

	/**
	 * Test of methode tileAt de juiste tiles retourneert.
	 */
	@Test
	public void testTileAt() {
		Tile tile = board.tileAt(0, 1);
		assertEquals(tile.getX(), 0);
		assertEquals(tile.getY(), 1);
		assertEquals(tile.topSprite().getSpriteType(),
				SpriteType.WALL);
	}

	/**
	 * Test of wrap-around goed werkt bij methode tileAtOffset.
	 */
	@Test
	public void testTileAtOffset() {
		Tile tile1 = board.tileAtOffset(new Tile(2, 1), 2, 2);
		assertEquals(tile1.getX(), 4);
		assertEquals(tile1.getY(), 0);
		assertEquals(tile1.topSprite().getSpriteType(),
				SpriteType.FOOD);
		Tile tile2 = board.tileAtOffset(new Tile(0, 0), 0,-1);
		assertEquals(tile2.getX(), 0);
		assertEquals(tile2.getY(), 2);
		assertEquals(tile2.topSprite().getSpriteType(),
				SpriteType.WALL);
	}

	/**
	 * Test of wrap-around goed werkt bij methode tileAtDirection.
	 */
	@Test
	public void testTileAtDirection() {
		Tile tile = board.tileAtDirection(new Tile(0, 2),
				Direction.DOWN);
		assertEquals(tile.getX(), 0);
		assertEquals(tile.getY(), 0);
		assertEquals(tile.topSprite().getSpriteType(),
				SpriteType.WALL);
	}

}