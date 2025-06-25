package fish;

import java.awt.*;

public abstract class ParentFish {

    int x;
    int y;

    abstract void interact();
    abstract boolean checkClick(int x, int y);
}
