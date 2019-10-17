package com.epam.booking.entity;

/**
 * <P>Any class, implementing this interface must have id field in it.
 * This id is used for identifying the object in any storage where it is stored.
 * </P>
 * Id may be null, if an object has been created right in the code and does not have an id.
 * So if id is null and the object is being saved to a storage (eg. database),
 * the storage should generate id for this object by itself.
 */
public interface Identifiable {

    Integer getId();

}
