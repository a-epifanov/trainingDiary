package client;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.io.IOException;

import javax.swing.JLabel;

class MyDragGestureListener implements DragGestureListener {

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        JLabelWithId label = (JLabelWithId) event.getComponent();
       /* int id;
        for (ClientExercise clientExercise : mainForm.labelExercises) {
        	if (clientExercise.jlabel == label) {
        		
        	}
        }*/
        final String text = label.getText() + " " + String.valueOf(label.id);
        System.out.println("iddd = " + label.id);

        Transferable transferable = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.stringFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                if (!isDataFlavorSupported(flavor)) {
                    return false;
                }
                return true;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                return text;
            }
        };
        event.startDrag(null, transferable);
    }
}
