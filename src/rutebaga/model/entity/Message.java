package rutebaga.model.entity;

import rutebaga.model.entity.messages.MessageVisitor;

public abstract class Message
{
	public abstract String asString();
	public abstract void accept(MessageVisitor visitor);
}
