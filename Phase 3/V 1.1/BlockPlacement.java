public class BlockPlacement {
    public Block block;
    public Position pos;

    public BlockPlacement(Block block, Position pos) {
        this.block = block;
        this.pos = pos;
    }

    // region Mutator methods
    public void rotate(int x, int y, int z, int[] cargoDimensions) {
        for (int i = 0; i < (x % 4); i++) {
            pos = rotateOriginAroundX(pos, block.dimensions, cargoDimensions);
        }
        for (int i = 0; i < (y % 4); i++) {
            pos = rotateOriginAroundY(pos, block.dimensions, cargoDimensions);
        }
        for (int i = 0; i < (z % 4); i++) {
            pos = rotateOriginAroundZ(pos, block.dimensions, cargoDimensions);
        }
        block.rotate(x, y, z);
    }

    // region helper methods
    private Position rotateOriginAroundX(Position pos, int[] blockDimensions, int[] cargoDimensions) {
        int blockHeight = blockDimensions[1];
        int cargoDepth = cargoDimensions[2];

        Position returnPosition = new Position(pos.x, pos.z, cargoDepth - pos.y - blockHeight);
        return returnPosition;
    }

    private Position rotateOriginAroundY(Position pos, int[] blockDimensions, int[] cargokDimensions) {
        int blockWidth = blockDimensions[0];
        int cargoWidth = cargokDimensions[0];

        Position returnPosition = new Position(cargoWidth - pos.z, pos.y, pos.x + blockWidth);
        return returnPosition;
    }

    private Position rotateOriginAroundZ(Position pos, int[] blockDimensions, int[] cargoDimensions) {
        int blockWidth = blockDimensions[0];
        int cargoWidth = cargoDimensions[0];
        Position returnPosition = new Position(pos.y, cargoWidth - pos.x - blockWidth, pos.z);
        return returnPosition;
    }
    // endregion
    // endregion

    // region Accessor methods
    public BlockPlacement createRotation(int x, int y, int z, int[] cargoDimensions) {
        BlockPlacement returnPlacement = this.copy();
        returnPlacement.rotate(x, y, z, cargoDimensions);
        return returnPlacement;
    }

    public BlockPlacement copy() {
        return new BlockPlacement(block.copy(), pos.copy());
    }
    // endregion
}