package com.nhamil.protInt.client.interpreter.commands;

import com.nhamil.protInt.client.utils.Exceptions.BadResponseException;
import com.nhamil.protInt.client.utils.Exceptions.ServerCommunicationException;

import java.util.List;

public interface Command<T> {
    /** Get a string with the description */
    String description();
    /** Returns command syntax*/
    String help();
    /** Execute command*/
    T run(List<String> args) throws ServerCommunicationException, BadResponseException;
}
