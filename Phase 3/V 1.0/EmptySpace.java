public class EmptySpace {
    /**
     * Three consecutive numbers X,Y,Z (layers,rows,columns) (eg. 5,8,33)
     */
    public int[] dimensions = new int[3];
    public int volume = 0;
    public Position pos;

    public EmptySpace() {
        this.pos = new Position(0, 0, 0);
    }

    public EmptySpace(Position pos) {
        this.pos = pos;
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
        updateVolume();
    }

    private void updateVolume() {
        volume = dimensions[0] * dimensions[1] * dimensions[2];
    }
}
