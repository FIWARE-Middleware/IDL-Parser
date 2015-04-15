package com.eprosima.idl.parser.exception;

public class ParseException extends RuntimeException
{
	public ParseException()
    {
        super();
    }
    
    public ParseException(String file, int line, String message)
    {
        super("In file " + file + " at line " + line + ": " + message);
    }
}
