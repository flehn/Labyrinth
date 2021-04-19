package de.uniwue.gdp.labyrinth;
import de.uniwue.gdp.labyrinth.examples.Examples;
import de.uniwue.gdp.labyrinth.model.Maze;
import static de.uniwue.gdp.labyrinth.Explorer.GridRichtung.*;

public class Explorer {

    public static void main(String[] args) {
        Maze a1 = Examples.example02();
        Explorer explorer = new Explorer();
        System.out.println(explorer.exploreMaze(a1));
    }

    private String exploreMaze(Maze z) {

        // Array der Maze darstellt in Reihen und Spalten
        Character[][] mazeArray = firstMaze(z);
        int x = 1;
        int y = 1;
        mazeRunner(z, mazeArray, x, y, DOWN);
        String ergebnis = ausgabeMaze(z, mazeArray);
        return ergebnis;
    }

    private String ausgabeMaze(Maze z, Character[][] mazeArray) {

        String ergebnis = "";

        for (int i = 0; i < z.height(); i++) {
            for (int k = 0; k < z.width(); k++) {
                ergebnis += mazeArray[i][k];
            }
            ergebnis += "\n";
        }

        return ergebnis;
    }

    private Character[][] firstMaze(Maze z) {

        // Maze erstellen mit proportionen
        Character[][] maze = new Character[z.height()][z.width()];

        // Fülle alles mit Wänden
        for (int i = 0; i < z.height(); i++) {
            for (int k = 0; k < z.width(); k++) {
                maze[i][k] = '#';
            }
        }
        return maze;
    }

    private void mazeRunner(Maze z, Character[][] mazeArray, int x, int y, int gridRichtung) {


        // Erstellt auf der aktuellen Position einen Gang
        mazeArray[y][x] = ' ';
        // Setzt einen Marker wenn Kreuzung
        boolean marker = false;
        if (checkifKreuzung(z))  {
            marker = true;
        }

        //Wenn Links frei ist, gehe nach Links
        if (!z.isWall(Maze.Direction.LEFT) && (z.marks(Maze.Direction.LEFT) < 2)) {
            //move ist die Laufmethode die als parameter auch den marker(true) enthält, damit die Methode weiß ob marker gesetzt werden sollen
            Tuple neueLaufrichtung = move(z, mazeArray, x, y, 0, marker, gridRichtung);
            if (neueLaufrichtung.GridRichtung == DOWN) {
                y ++;
            }
            else if (neueLaufrichtung.GridRichtung == UP) {
                y --;
            }
            else if (neueLaufrichtung.GridRichtung == RIGHT) {
                x ++;
            }
            else if (neueLaufrichtung.GridRichtung == LEFT) {
                x --;
            }
            System.out.println(ausgabeMaze(z, mazeArray));
            mazeRunner(z, mazeArray, x, y, neueLaufrichtung.GridRichtung);
        }
        else if (!z.isWall(Maze.Direction.AHEAD) && (z.marks(Maze.Direction.AHEAD) < 2)) {
            Tuple neueLaufrichtung = move(z, mazeArray, x, y, 1, marker, gridRichtung);
            if (neueLaufrichtung.GridRichtung == DOWN) {
                y ++;
            }
            else if (neueLaufrichtung.GridRichtung == UP) {
                y --;
            }
            else if (neueLaufrichtung.GridRichtung == RIGHT) {
                x ++;
            }
            else if (neueLaufrichtung.GridRichtung == LEFT) {
                x --;
            }
            System.out.println(ausgabeMaze(z, mazeArray));
            mazeRunner(z, mazeArray, x, y, neueLaufrichtung.GridRichtung);
        }
        else if (!z.isWall(Maze.Direction.RIGHT) && (z.marks(Maze.Direction.RIGHT) < 2)) {
            Tuple neueLaufrichtung = move(z, mazeArray, x, y, 2, marker, gridRichtung);
            if (neueLaufrichtung.GridRichtung == DOWN) {
                y ++;
            }
            else if (neueLaufrichtung.GridRichtung == UP) {
                y --;
            }
            else if (neueLaufrichtung.GridRichtung == RIGHT) {
                x ++;
            }
            else if (neueLaufrichtung.GridRichtung == LEFT) {
                x --;
            }
            System.out.println(ausgabeMaze(z, mazeArray));
            mazeRunner(z, mazeArray, x, y, neueLaufrichtung.GridRichtung);
        }
        else if (!z.isWall(Maze.Direction.BACK) && (z.marks(Maze.Direction.BACK) < 2)) {
            Tuple neueLaufrichtung = move(z, mazeArray, x, y, 3, marker, gridRichtung);
            if (neueLaufrichtung.GridRichtung == DOWN) {
                y ++;
            }
            else if (neueLaufrichtung.GridRichtung == UP) {
                y --;
            }
            else if (neueLaufrichtung.GridRichtung == RIGHT) {
                x ++;
            }
            else if (neueLaufrichtung.GridRichtung == LEFT) {
                x --;
            }
            System.out.println(ausgabeMaze(z, mazeArray));
            mazeRunner(z, mazeArray, x, y, neueLaufrichtung.GridRichtung);
        }

        System.out.println(ausgabeMaze(z, mazeArray));
    }

    private boolean checkifKreuzung(Maze z) {
        //Checke ob Kreuzung um makierung zu setzen
        int help = 0;

        if (!z.isWall(Maze.Direction.LEFT)) {
            help ++;
        }
        if (!z.isWall(Maze.Direction.AHEAD)) {
            help ++;
        }
        if (!z.isWall(Maze.Direction.RIGHT)) {
            help ++;
        }
        if (!z.isWall(Maze.Direction.BACK)) {
            help ++;
        }

        if (help > 2)
            return true;
        else
            return false;
    }

    private Tuple move(Maze z, Character[][] mazeArray, int x, int y, int direction, boolean marker, int gridRichtung) {

        // Gehe nach Links ABER WENN EINE Kreuzung dann setze vorher einen Marker
        if (direction == 0) {
            // Setze Marker nur wenn die Methode MazeRunner das argument true mitgibt, weil dann Kreuzung
            if (marker) {
                z.mark(Maze.Direction.LEFT);
                z.mark(Maze.Direction.BACK);
            }
            z.walk(Maze.Direction.LEFT);
            return new Tuple(compass(Maze.Direction.LEFT, gridRichtung), Maze.Direction.LEFT);

        }
        // Gehe nach Geradeaus
        if (direction == 1) {
            if (marker) {
                z.mark(Maze.Direction.AHEAD);
                z.mark(Maze.Direction.BACK);
            }
            z.walk(Maze.Direction.AHEAD);
            return new Tuple(compass(Maze.Direction.AHEAD, gridRichtung), Maze.Direction.AHEAD);

        }
        // Gehe nach Rechts
        if (direction == 2) {
            if (marker) {
                z.mark(Maze.Direction.RIGHT);
                z.mark(Maze.Direction.BACK);
            }
            z.walk(Maze.Direction.RIGHT);
            return new Tuple(compass(Maze.Direction.RIGHT, gridRichtung), Maze.Direction.RIGHT);

        }
        // Gehe zurück
        if (direction == 3) {
            if (marker) {
                z.mark(Maze.Direction.BACK);
            }
            z.walk(Maze.Direction.BACK);
            return new Tuple(compass(Maze.Direction.BACK, gridRichtung), Maze.Direction.BACK);

        }
        // Abbruchsbedingung
        return new Tuple(5,5);
    }

    private int compass(int blickRichtung, int gridRichtung) {

        if (blickRichtung == Maze.Direction.LEFT) {
            switch(gridRichtung) {
                case DOWN:
                    return RIGHT;
                case UP:
                    return LEFT;
                case RIGHT:
                    return UP;
                case LEFT:
                    return DOWN;
            }
        }

        if (blickRichtung == Maze.Direction.RIGHT) {
            switch(gridRichtung) {
                case DOWN:
                    return LEFT;
                case UP:
                    return RIGHT;
                case RIGHT:
                    return DOWN;
                case LEFT:
                    return UP;
            }
        }

        if (blickRichtung == Maze.Direction.BACK) {
            switch(gridRichtung) {
                case DOWN:
                    return UP;
                case UP:
                    return DOWN;
                case RIGHT:
                    return LEFT;
                case LEFT:
                    return RIGHT;
            }
        }

        return gridRichtung;
    }

    private class Tuple {

        public int GridRichtung;
        public int BlickRichtung;

        Tuple(int gridRichtung, int blickRichtung) {
            GridRichtung = gridRichtung;
            BlickRichtung = blickRichtung;
        }
    }

    // Diese Klasse erstellt die 2D Sichtweise von Oben
    final static class GridRichtung {
        public static final int DOWN = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int LEFT = 3;
    }
}


/*
        Position[1][1]; mazearray[1][3] = 1

        #############
        #           #
        # ###########
        # # #       #
        # # # ##### #
        # #   #   # #
        # ### # # # #
        #   # # # # #
        ### # # # # #
        #   #     # #
        ### ####### #
        #           #
        #############

        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
 */