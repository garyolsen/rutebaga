package rutebaga.model.entity.effect;

import rutebaga.model.entity.Entity;
import rutebaga.model.entity.EntityEffect;

public class UndoStatEffect extends EntityEffect
{
	private Object undoId;

	public UndoStatEffect(Object undoId)
	{
		super();
		this.undoId = undoId;
	}

	@Override
	protected void affect(Entity entity, Object id)
	{
		entity.getStats().undo(undoId);
	}
}
