public class BlockPlacement {
    public Block block;
    public Position pos;

    public BlockPlacement(Block block, Position pos) {
        this.block = block;
        this.pos = pos;
    }

    public BlockPlacement copy() {
        return new BlockPlacement(block.copy(), pos.copy());
    }
    public static void main(String[] args) {
    }
}