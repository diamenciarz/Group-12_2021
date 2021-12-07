import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Input extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        int keyCode = event.getKeyCode();

        boolean isInputCorrect = (keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_S) || (keyCode == KeyEvent.VK_D)
                || (keyCode == KeyEvent.VK_Q) || (keyCode == KeyEvent.VK_E) || (keyCode == KeyEvent.VK_O)
                || (keyCode == KeyEvent.VK_SPACE);
        if (isInputCorrect) {
            Pentis.pressedKey(ch);
        }
    }
}