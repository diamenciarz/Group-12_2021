public class ParcelPlacement {
    public Parcel parcel;
    public Position pos;

    public ParcelPlacement(Parcel parcel, Position pos) {
        this.parcel = parcel;
        this.pos = pos;
    }

    // region Mutator methods
    public void rotate(int xRots, int yRots, int zRots, int[] blockDimensions) {
        for (int i = 0; i < (xRots % 4); i++) {
            pos = rotateOriginAroundX(pos, parcel.dimensions, blockDimensions);
        }
        for (int i = 0; i < (yRots % 4); i++) {
            pos = rotateOriginAroundY(pos, parcel.dimensions, blockDimensions);
        }
        for (int i = 0; i < (zRots % 4); i++) {
            pos = rotateOriginAroundZ(pos, parcel.dimensions, blockDimensions);
        }
        parcel.rotate(xRots, yRots, zRots);
    }

    // region helper methods
    private Position rotateOriginAroundX(Position pos, int[] parcelDimensions, int[] blockDimensions) {
        int parcelHeight = parcelDimensions[1];
        int blockDepth = blockDimensions[2];

        Position returnPosition = new Position(pos.x, pos.z, blockDepth - pos.y - parcelHeight);
        return returnPosition;
    }

    private Position rotateOriginAroundY(Position pos, int[] parcelDimensions, int[] blockDimensions) {
        int parcelHeight = parcelDimensions[1];
        int blockHeight = blockDimensions[1];

        Position returnPosition = new Position(blockHeight - pos.z - parcelHeight, pos.y, pos.x);
        return returnPosition;
    }

    private Position rotateOriginAroundZ(Position pos, int[] parcelDimensions, int[] blockDimensions) {
        int blockWidth = blockDimensions[0];
        int parcelWidth = parcelDimensions[0];
        Position returnPosition = new Position(pos.y, blockWidth - pos.x - parcelWidth, pos.z);
        return returnPosition;
    }
    // endregion
    // endregion

    // region Accessor methods
    public ParcelPlacement copy() {
        return new ParcelPlacement(parcel.copy(), pos.copy());
    }

    /**
     * Rotates the shape and its position 90 degrees clockwise around each axis the
     * specified number
     * of times.
     * 
     * @param shape
     * @param x
     * @param y
     * @param z
     * @return The rotated shape and position as a new object
     */
    public ParcelPlacement createRotation(int x, int y, int z, int[] blockDimensions) {
        ParcelPlacement returnPlacement = this.copy();
        returnPlacement.rotate(x, y, z, blockDimensions);
        return returnPlacement;
    }
    // endregion
}