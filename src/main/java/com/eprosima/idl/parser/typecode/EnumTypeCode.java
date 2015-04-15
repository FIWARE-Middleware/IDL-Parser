package com.eprosima.idl.parser.typecode;

import org.antlr.stringtemplate.StringTemplate;

import com.eprosima.idl.util.Pair;

public class EnumTypeCode extends MemberedTypeCode
{
    public EnumTypeCode(String scope, String name)
    {
        super(TypeCode.KIND_ENUM, scope, name);
    }

    @Override
    public boolean isPrimitive() {return true;}
    
    @Override
    public boolean isIsType_c(){return true;}
    
    public void addMember(EnumMember member)
    {
        addMember((Member)member);
    }

    @Override
    public String getCppTypename()
    {
        StringTemplate st = getCppTypenameFromStringTemplate();
        st.setAttribute("name", getScopedname());
        return st.toString();
    }
    
    @Override
    public String getIdlTypename()
    {
        StringTemplate st = getIdlTypenameFromStringTemplate();
        st.setAttribute("name", getScopedname());
        return st.toString();
    }
    
    @Override
    public String getInitialValue()
    {   
        if(getMembers().size() > 0)
        {
            return (getScope() != null ? getScope() + "::" : "") + getMembers().get(0).getName();
        }
        
        return "";
    }
    
    /*public Pair<Integer, Integer> getMaxSerializedSize(int currentSize, int lastDataAligned)
    {
        int size = getSize();
        
        if(size <= lastDataAligned)
        {
            return new Pair<Integer, Integer>(currentSize + size, size);
        }
        else
        {
            int align = (size - (currentSize % size)) & (size - 1);
            return new Pair<Integer, Integer>(currentSize + size + align, size);
        }
    }
    
    public int getMaxSerializedSizeWithoutAlignment(int currentSize)
    {
        return currentSize + getSize();
    }*/
    
    @Override
    public String getSize()
    {    
        return "4";
    }

}
