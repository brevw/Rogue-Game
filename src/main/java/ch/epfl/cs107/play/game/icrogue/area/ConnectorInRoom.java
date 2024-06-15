package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

public interface ConnectorInRoom {

    /**
     * getter for the connector's index
     * @return index
     */
    int getIndex();

    /**
     * getter for the connector's destination
     * @return destination
     */
    DiscreteCoordinates getDestination();
}