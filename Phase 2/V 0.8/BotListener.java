import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Timer for moving the pieces down
class BotListener implements ActionListener {
    ArrayList<Character> keys = new ArrayList<>();

    public void actionPerformed(ActionEvent event) {
        generateKeyPress();
    }

    public void generateNewKeySequence(char[] chars) {
        for (char key : chars) {
            keys.add(key);
        }
    }
    public void generateKeyPresses(){
        for (int i = 0; i < keys.size(); i++) {
            //System.out.println("Key: " + keys.get(i));
            generateKeyPress();
        }
    }

    private void generateKeyPress() {
        if (keys.size() > 0) {
            char c = keys.get(0);
            keys.remove(0);
            BotPentis.pressedKey(c);
        }
    }
}