package cz.martlin.jmop.gui.control;

import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;

public interface RequiresJMOP {

	public void setupJMOP(JMOPPlayer jmop, GuiComplexActionsPerformer actions);
}
