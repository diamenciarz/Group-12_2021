import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
 
 //Timer for moving the pieces down
 class TimeListener implements ActionListener {
    public void actionPerformed(ActionEvent event)
    {
        Pentis.pressedKey('s');
    } 
}