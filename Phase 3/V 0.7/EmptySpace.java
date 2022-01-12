public class EmptySpace {
    /**
     * Three consecutive numbers Z,Y,X (layers,rows,columns) (eg. 5,8,33)
     */
    public int[] dimensions = new int[3];
    public int volume = 0;
    public Position pos;
    public int xPos = 0;
    public int yPos = 0;
    public int zPos = 0;

    public EmptySpace(int xPos, int yPos, int zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.pos = new Position(xPos, yPos, zPos);
    }

    public EmptySpace(int[] dimensions, int xPos, int yPos, int zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.pos = new Position(xPos, yPos, zPos);
        setDimensions(dimensions);
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
        updateVolume();
    }

    private void updateVolume() {
        volume = dimensions[0] * dimensions[1] * dimensions[2];
    }
}
