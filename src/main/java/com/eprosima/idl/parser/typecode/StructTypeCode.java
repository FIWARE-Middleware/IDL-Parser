package com.eprosima.idl.parser.typecode;

import com.eprosima.idl.util.Pair;

import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

public class StructTypeCode extends MemberedTypeCode
{
    public StructTypeCode(String scope, String name)
    {
        super(TypeCode.KIND_STRUCT, scope, name);
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
    public boolean isIsType_a(){
    	return true;
    }
    
    /*public Pair<Integer, Integer> getMaxSerializedSize(int currentSize, int lastDataAligned)
    {
        List<Member> members = getMembers();
        int lcurrentSize = currentSize;
        int llastDataAligned = lastDataAligned;
        
        for(int count = 0; count < members.size(); ++count)
        {
            Pair<Integer, Integer> pair = members.get(count).getTypecode().getMaxSerializedSize(lcurrentSize, llastDataAligned);
            lcurrentSize = pair.first();
            llastDataAligned = pair.second();
        }
        
        return new Pair<Integer, Integer>(lcurrentSize, llastDataAligned);
    }
    
    public int getMaxSerializedSizeWithoutAlignment(int currentSize)
    {
        List<Member> members = getMembers();
        int lcurrentSize = currentSize;
        
        for(int count = 0; count < members.size(); ++count)
        {
            lcurrentSize = members.get(count).getTypecode().getMaxSerializedSizeWithoutAlignment(lcurrentSize);
        }
        
        return lcurrentSize;
    }
    
    public String getMaxSerializedSize()
    {
        Pair<Integer, Integer> pair = getMaxSerializedSize(0, 0);
        return pair.first().toString();
    }
    
    public String getMaxSerializedSizeWithoutAlignment()
    {
        return Integer.toString(getMaxSerializedSizeWithoutAlignment(0));
    }*/
}
