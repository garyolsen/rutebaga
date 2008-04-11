package rutebaga.model.entity;

/**
 * Entity stores the state related to an Entity in a physical environment. Entities
 * are objects defined by certain anthropomorphic attributes, such as containing
 * stats and an inventory. However, Entities are not necessarily anthropomorphic
 * themselves.
 * 
 * Entity is defined by its {@link EntityType}, to which it forwards most of its
 * behavior. Entity exists only to retain state. Subclasses of Entity that
 * require new operations should defer the execution of those operations to
 * their own subclass of {@link EntityType}.
 * 
 * @author Nick
 * @see EntityType
 */
public class Entity {

}
