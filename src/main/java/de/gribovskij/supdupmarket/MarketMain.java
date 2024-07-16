package de.gribovskij.supdupmarket;

import java.time.LocalDate;

/**
 * Runner class for the console application
 * @author Eugen Gribovskij
 */
public class MarketMain {

    public static void main(String[] args) {
        MarketService marketService = new MarketService();
        marketService.startMarketSimulation(LocalDate.now(), 120);
    }
}
