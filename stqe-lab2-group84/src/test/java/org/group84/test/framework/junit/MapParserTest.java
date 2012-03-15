package org.group84.test.framework.junit;


import static org.mockito.Mockito.*;

import java.io.IOException;

import org.jpacman.framework.factory.FactoryException;
import org.jpacman.framework.factory.IGameFactory;
import org.jpacman.framework.factory.MapParser;
import org.jpacman.framework.model.Board;
import org.jpacman.framework.model.Food;
import org.jpacman.framework.model.Game;
import org.jpacman.framework.model.Ghost;
import org.jpacman.framework.model.Player;
import org.jpacman.framework.model.Wall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test met mocking van de klasse MapParser.
 */
@RunWith(MockitoJUnitRunner.class)
public class MapParserTest {
	private final int W = 5, H = 5;
	/*private final String[] map = {
			"# G P. " };*/

	private final String[] map = {
			"#####",
			"#   #",
			"#GPG#",
			"#...#",
			"#####"
			};
	private final String fileName = "level1.map"; // bevat kaart hierboven
	
	@Mock private IGameFactory gameFactory;
	private MapParser mapParser;
	
	/**
	 * Maak de MapParser aan.
	 */
	@Before
	public void setUp() {
		gameFactory = mock(IGameFactory.class);
		when(gameFactory.makeBoard(W, H)).thenReturn(new Board(W, H));
		mapParser = new MapParser(gameFactory);
	}
	
	/**
	 * In deze methode wordt geverifieerd dat alle methods
	 * van de GameFactory het juiste aantal keren zijn gecalled.
	 */
	private void verifyFactoryCalls() {
		verify(gameFactory).makeGame();
		verify(gameFactory).makeBoard(W, H);
		
		int nWall = 16, nFood = 3, nGhost = 2, nPlayer = 1;
		verify(gameFactory, times(nWall)).makeWall();
		verify(gameFactory, times(nFood)).makeFood();
		verify(gameFactory, times(nGhost)).makeGhost();
		verify(gameFactory, times(nPlayer)).makePlayer();
		verifyNoMoreInteractions(gameFactory);
	}
	
	/**
	 * Test voor de parseMap methode,
	 * test of alle calls zoals verwachting hebben plaatsgevonden.
	 * @throws FactoryException
	 */
	@Test
	public void testParseMap() throws FactoryException {
		mapParser.parseMap(map);
		verifyFactoryCalls();
	}
	
	/**
	 * Test voor de parseFromFile methode,
	 * test o.a. of de mapParser de I/O goed doet.
	 * @throws FactoryException
	 * @throws IOException
	 */
	@Test
	public void testParseFromFile() throws FactoryException, IOException {
		mapParser.parseFromFile(fileName);
		verifyFactoryCalls();
	}
}