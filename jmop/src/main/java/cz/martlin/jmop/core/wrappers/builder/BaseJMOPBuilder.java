package cz.martlin.jmop.core.wrappers.builder;

import cz.martlin.jmop.core.data.CommandlineData;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;

public interface BaseJMOPBuilder {
	public JMOPPlayer create(CommandlineData data) throws Exception;
}
