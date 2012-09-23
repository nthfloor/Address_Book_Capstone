package capstone;

import javax.swing.text.BadLocationException;

public interface WalkThroughMessenger {
	public void appendWalkThroughMessage(String txt) throws WalkThroughMessangerException;
}
