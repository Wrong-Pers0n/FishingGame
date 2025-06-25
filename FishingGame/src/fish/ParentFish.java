package fish;

import java.awt.*;

public abstract class ParentFish {

    int x;
    int y;
    // delete this comment <3
    abstract void interact();
    abstract boolean checkClick(int x, int y);
}
