/// Grid
///

import java.util.*;

public class Grid extends Observable {
    public enum Result {NONE, WIN, LOSE};

    // Grid of Locations
    private Location[][] location;

    // Battleship length = width, so this works for both dimensions
    private static final int GRIDSIZE = 10;

    // Array for the sizes of each individual ship; will have something to do with location.id
    private static final int[] SHIPSIZES = {5, 4, 3, 3, 2};

    private Random random;

    private boolean[] shipAliveStatus;

    public Grid() {
        location = new Location[GRIDSIZE][GRIDSIZE];
        random = new Random();
        shipAliveStatus = new boolean[5];
		for(boolean b:shipAliveStatus)
			b = true;

        for(int i = 0; i < GRIDSIZE; i++){
            for(int j = 0; j < GRIDSIZE; j++){
                location[i][j] = new Location();
            }
        }

        deployFleet();
    }

    public void deployFleet() {
        for(int j=1;j<=SHIPSIZES.length;j++)
        {
            int x,y;
            boolean vertical;
            do
            {
                x = random.nextInt(10);
                y = random.nextInt(10);
                vertical = random.nextBoolean();
            }
            while(!isValidLocation(y,x,vertical,SHIPSIZES[j]));

            if(vertical)
                for(int i=0;i<SHIPSIZES[j];i++)
                    location[y+i][x].setId(i);
            else
                for(int i=0;i<SHIPSIZES[j];i++)
                    location[y][x+i].setId(i);
        }
    }

    public boolean isValidLocation(int row, int col, boolean vertical, int shipLength) {
        if(!(vertical ? ((row+shipLength)<10) : ((col+shipLength)<10)))
            return false;
        if(vertical){
            for(int i=0;i<shipLength;i++)
                if(location[row+i][col].getId() != 0)
                    return false;
        }
        else{
            for(int i=0;i<shipLength;i++)
                if(location[row][col+i].getId() != 0)
                    return false;
        }
        return true;
    }

    public Result getResult() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if((location[i][j].getId() != 0) && !location[i][j].isFiredOn()) {
                    return Result.NONE;
                }
            }
        }
        return Result.LOSE;
    }

    public Location getLocation(int row, int col) {
        return location[row][col];
    }

    public boolean isHit(int row, int col) {
        return (getLocation(row, col).isFiredOn() && (getLocation(row, col).getId() != 0));
    }

    // for Observable
    public void fireOn(int row, int col) {
        if(location[row][col].isFiredOn()) return;
        else{
            location[row][col].setFiredOn(true);
        }
        setChanged();
        notifyObservers("firedOn:"+row+":"+col);
    }

    // for Observable
    public void checkSunkShip() {
        for(int x=0;x<SHIPSIZES.length;x++)
        {
            boolean b = true;
            for(int i=0;i<10;i++)
                for(int j=0;j<10;j++)
                    if(!location[i][j].isFiredOn() && location[i][j].getId()==x)
                        b = false;
            if(b)
            {
                setChanged();
                notifyObservers("shipSunk:"+SHIPSIZES[x]);
            }
        }
    }
}
