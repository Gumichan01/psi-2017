package com.nhamil.protInt.server.data;

/** Data that can be converted to Network friendly format*/
public interface NetworkData {
    /**
     * Format the Data according to Protocol
     * @return The String value of an Data object according to protocol
     */
    String format();
}
