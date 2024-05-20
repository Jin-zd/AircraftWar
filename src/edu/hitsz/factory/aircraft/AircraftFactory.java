package edu.hitsz.factory.aircraft;

import edu.hitsz.aircraft.AbstractAircraft;

public interface AircraftFactory {
    AbstractAircraft createAircraft(int shootNum, int hp);
}
