package com.eprosima.idl.parser.typecode;

import java.util.List;
import java.util.ArrayList;

public class UnionMember extends Member
{
    public UnionMember()
    {
        super();
        m_labels = new ArrayList<String>();
    }
    
    public UnionMember(TypeCode typecode, String name)
    {
        super(typecode, name);
        m_labels = new ArrayList<String>();
    }
    
    public void addLabel(String label)
    {
    	if (label.startsWith("::")) {
    		m_labels.add(label.substring(2));
    	} else {
    		m_labels.add(label);
    	}
    }
    
    public List<String> getLabels()
    {
        return m_labels;
    }
    
    public boolean isDefault()
    {
        return m_default;
    }
    
    public void setDefault(boolean b)
    {
        m_default = b;
    }
    
    private List<String> m_labels = null;
    
    private boolean m_default = false;
}
