package ufrgs.maslab.abstractsimulator.core.interfaces;

import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;

public interface Platoon {
	
	void sendRadioMessage(Message mgs) throws SimulatorException;

}
