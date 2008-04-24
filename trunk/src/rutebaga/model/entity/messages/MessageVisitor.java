package rutebaga.model.entity.messages;

public interface MessageVisitor
{
	void visitEntityStatEffectMessage(EntityStatEffectMessage message);

	void visitStatChangeMessage(StatChangeMessage statChangeMessage);
}
