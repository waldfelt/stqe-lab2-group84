package org.group84.test.framework.accept;


import static org.junit.Assert.assertEquals;

import org.jpacman.framework.factory.FactoryException;
import org.jpacman.framework.model.Direction;
import org.jpacman.framework.model.Tile;
import org.jpacman.test.framework.accept.AbstractAcceptanceTest;
import org.junit.Before;
import org.junit.Test;

public class MovePlayerStoryTest extends AbstractAcceptanceTest {
	
	// Emtpy tile on the board next to the player.		
	private Tile emptyTile;

	// Food tile on the board next to the player.
	private Tile foodTile;
	
	
	@Before
	public void setUp() throws FactoryException, InterruptedException {
		super.setUp();
		emptyTile = tileAt(1, 0);
		foodTile = tileAt(0, 1);
	}
	
	/**
	 * Test that a player can move towards an empty tile.
	 */
	@Test
	public void test_S2_1_PlayerMove() {
		// given
		getEngine().start();
		// when
		getUI().getGame().movePlayer(Direction.UP);		
		// then
		assertEquals(emptyTile, getPlayer().getTile());
	}
	
	/**
	 * Test that a player can move over food.
	 */
	@Test
	public void test_S2_2_PlayerFood() {
		// given
		getEngine().start();
		// when
		getUI().getGame().movePlayer(Direction.LEFT);		
		// then
		assertEquals(foodTile, getPlayer().getTile());
		assertEquals(10, getPlayer().getPoints());
	}
	
	/**
	 * Test that a player can die.
	 */
	@Test
	public void test_S2_3_PlayerDies() {
		// given
		getEngine().start();
		// when
		getUI().getGame().movePlayer(Direction.RIGHT);		
		// then
		assertEquals(false, getPlayer().isAlive());
	}
	
	/**
	 * Test that a player cannot move through walls.
	 */
	@Test
	public void test_S2_4_PlayerWall() {
		Tile playerTile = getPlayer().getTile();
		// given
		getEngine().start();
		// when
		getUI().getGame().movePlayer(Direction.DOWN);		
		// then
		assertEquals(playerTile, getPlayer().getTile());
	}
	
	/**
	 * Test that a player can win.
	 */
	@Test
	public void test_S2_5_PlayerWins() {
		//given
		test_S2_2_PlayerFood();
		//when
		getUI().getGame().movePlayer(Direction.RIGHT);
		getUI().getGame().movePlayer(Direction.UP);
		getUI().getGame().movePlayer(Direction.RIGHT);

		assertEquals(true, getUI().getGame().getPointManager().allEaten());
		assertEquals(true, getUI().getGame().won());
		
	}
	

}
