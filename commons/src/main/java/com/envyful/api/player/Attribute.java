package com.envyful.api.player;

import com.envyful.api.player.save.SaveManager;

import java.util.concurrent.CompletableFuture;


/**
 *
 * An interface representing data stored about something, typically a player
 *
 * @param <A> The unique identifier type
 * @param <B> The platform player type
 */
@SuppressWarnings({"unused", "unchecked"})
public interface Attribute<A, B> {

    /**
     *
     * Gets the unique identifier for the attribute
     *
     * @return The unique identifier
     */
    CompletableFuture<A> getId();

    /**
     *
     * If the attribute can save to the database at this time
     *
     * @return True if it can save
     */
    default boolean shouldSave() {
        return true;
    }

    /**
     *
     * Loads the data into the attribute from the given id
     *
     * @param id The id to load from
     */
    void load(A id);

    /**
     *
     * Saves the data from the attribute using the given id
     *
     * @param id the id to save to
     */
    void save(A id);

    /**
     *
     * Deletes all data from the attribute
     *
     * @param saveManager The save manager to delete from
     */
    void deleteAll(SaveManager<?> saveManager);

    /**
     *
     * Creates a new instance of the attribute builder
     *
     * @param attributeClass The class of the attribute
     * @return The builder
     * @param <A> The attribute type
     * @param <B> The id type
     * @param <C> The platform player type
     */
    static <A extends Attribute<B, C>, B, C> AttributeBuilder<A, B, C> builder(Class<A> attributeClass) {
        return new AttributeBuilder<A, B, C>().attributeClass(attributeClass);
    }

}
