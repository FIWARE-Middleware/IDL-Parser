package com.eprosima.idl.parser.tree;

import java.util.ArrayList;
import java.util.List;

import com.eprosima.idl.context.Context;
import com.eprosima.idl.parser.typecode.Member;

public class Exception extends TreeNode implements Export, Definition
{
	public Exception(String scopeFile, boolean isInScope, String scope, String name)
    {
        super(scopeFile, isInScope, scope, name);

        m_members = new ArrayList<Member>();
    }
	
	@Override
    public boolean isIsModule()
    {
        return false;
    }
	
	@Override
	public boolean isIsInterface()
    {
    	return false;
    }
	
	@Override
    public boolean isIsTypeDeclaration()
    {
        return false;
    }

	@Override
    public boolean isIsConstDeclaration()
    {
        return false;
    }
	
	public void setParent(Object obj)
    {
        m_parent = obj;
    }
    
    public Object getParent()
    {
        return m_parent;
    }
    
    @Override
    public boolean isIsOperation()
    {
        return false;
    }
    
    @Override
    public boolean isIsException()
    {
        return true;
    }
	
	@Override
	public boolean isIsAnnotation()
    {
        return false;
    }
    
    public List<Member> getMembers()
    {
        return m_members;
    }
    
    public int addMember(Member member)
    {
        m_members.add(member);
        return m_members.size() - 1;
    }
    
    @Override
    public boolean resolve(Context ctx)
    {
    	return true;
    }
    
    private Object m_parent = null;
    private List<Member> m_members = null;
}
