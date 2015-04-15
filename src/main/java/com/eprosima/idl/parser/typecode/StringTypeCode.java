package com.eprosima.idl.parser.typecode;

import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

import com.eprosima.idl.util.Pair;

public class StringTypeCode extends TypeCode
{
    public StringTypeCode(int kind, String maxsize)
    {
        super(kind);
        m_maxsize = maxsize;
    }

    @Override
    public boolean isPrimitive() {return true;}
    
    @Override
    public boolean isIsType_d(){return true;}
    
    @Override
    public boolean isString(){return true;}
    
    @Override
    public String getCppTypename()
    {
        return getCppTypenameFromStringTemplate().toString();
    }
    
    @Override
    public String getIdlTypename()
    {
        return getIdlTypenameFromStringTemplate().toString();
    }
    
    @Override
    public String getInitialValue()
    {   
        return getInitialValueFromStringTemplate();
    }
    
    public String getMaxsize()
    {
        if(m_maxsize == null)
            return "255";
        
        return m_maxsize;
    }
    
    public Pair<Integer, Integer> getMaxSerializedSize(int currentSize, int lastDataAligned)
    {
        int lcurrentSize = currentSize;
        
        // Length
        if(4 <= lastDataAligned)
        {
            lcurrentSize += 4;
        }
        else
        {
            int align = (4 - (lcurrentSize % 4)) & (4 - 1);
            lcurrentSize += 4 + align;
        }
        
        if(m_maxsize == null)
        {
            return new Pair<Integer, Integer>(lcurrentSize + 255 + 1, 1);
        }
        else
        {
            return new Pair<Integer, Integer>(lcurrentSize + Integer.parseInt(m_maxsize) + 1, 1);
        }
    }
    
    public int getMaxSerializedSizeWithoutAlignment(int currentSize)
    {
        if(m_maxsize == null)
        {
            return currentSize + 4 + 255 + 1;
        }
        else
        {
            return currentSize + 4 + Integer.parseInt(m_maxsize) + 1;
        }
    }
    
    private String m_maxsize = null;
}
