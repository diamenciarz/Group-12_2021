public class Position {
    public int x;
    public int y;
    public int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(Position pos, int deltaX, int deltaY, int deltaZ) {
        this.x = pos.x + deltaX;
        this.y = pos.y + deltaY;
        this.z = pos.z + deltaZ;
    }
}
