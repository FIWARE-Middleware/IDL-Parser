package com.eprosima.idl.parser.tree;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface extends ExportContainer implements Definition, Notebook
{
    public Interface(String scopeFile, boolean isInScope, String scope, String name)
    {
        super(scopeFile, isInScope, scope, name);

        m_bases = new HashMap<String, Interface>();
        m_annotations = new HashMap<String, String>();
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
    public boolean isIsModule()
    {
        return false;
    }
    
    @Override
    public boolean isIsInterface()
    {
    	return true;
    }
    
    @Override
    public boolean isIsException()
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
	
	@Override
	public boolean isIsAnnotation()
    {
        return false;
    }

    public boolean addBase(Interface interf)
    {
        Interface prev = m_bases.put(interf.getName(), interf);

        if(prev != null)
            return false;

        return true;
    }

    public ArrayList<Interface> getBases()
    {
        return new ArrayList<Interface>(m_bases.values());
    }
    
    /*!
     * @brief This function returns the exception defined inside the interface.
     */
    public Exception getException(String currentScope, String ename)
    {
    	com.eprosima.idl.parser.tree.Exception exception = null;
    	
    	for(int count = 0; exception == null && count < getExports().size(); ++count)
        {
    		int lastIndex = -1;
    		
    		if(getExports().get(count).isIsException())
    		{
                String tmpname = ((com.eprosima.idl.parser.tree.Exception)getExports().get(count)).getScopedname();
                
                if(tmpname.equals(ename))
                {
                	exception = (com.eprosima.idl.parser.tree.Exception)getExports().get(count);
                }
                else
                {
                	// Probar si no tiene scope, con el scope actual.
                    if(exception == null && ((lastIndex = ename.lastIndexOf("::")) == -1) &&
                    		tmpname.equals(currentScope + ename))
                    {
                    	exception = (com.eprosima.idl.parser.tree.Exception)getExports().get(count);
                    }	
                }
    		}
        }
    	
    	return exception;
    }

    /*!
     * @brief This function returns all operations of the interface.
     * This function is used in the string templates.
     */
    public ArrayList<Operation> getOperations()
    {
        if(m_operations == null)
        {
            m_operations = new ArrayList<Operation>();
            
            // Get own operations.
            for(int count = 0; count < getExports().size(); ++count)
            {
                if(getExports().get(count).isIsOperation())
                {
                    m_operations.add((Operation)getExports().get(count));
                }
            }
        }
        
        return m_operations;
    }

    public ArrayList<Operation> getAll_operations()
    {
        if(m_all_operations == null)
        {
            m_all_operations = new ArrayList<Operation>();

            // Get parent operations.
            for(Interface iface : m_bases.values())
            {
                m_all_operations.addAll(iface.getAll_operations());
            }
            
            // Get own operations.
            for(int count = 0; count < getExports().size(); ++count)
            {
                if(getExports().get(count).isIsOperation())
                {
                    m_all_operations.add((Operation)getExports().get(count));
                }
            }
        }

        return m_all_operations;
    }
    
    /*!
     * @brief This function is used in stringtemplates to not generate module in some cases (Right now in generated RTI idl).
     */
    public boolean isThereAreDeclarations()
    {
        boolean returnedValue = false;
        
        for(int count = 0; !returnedValue && count < getExports().size(); ++count)
        {
            returnedValue = getExports().get(count).isIsTypeDeclaration() ||
                getExports().get(count).isIsConstDeclaration() || getExports().get(count).isIsException();
        }
        
        return returnedValue;
    }
    
    @Override
    public void addAnnotations(HashMap<String, String> annotations)
    {
        m_annotations.putAll(annotations);
    }
    
    @Override
    public void addAnnotation(String key, String value)
    {
        m_annotations.put(key, value);
    }
    
    @Override
    public HashMap<String, String> getAnnotations()
    {
        return m_annotations;
    }

    private Object m_parent = null;
    
    //! Contains all interfaces it inherits from.
    private HashMap<String, Interface> m_bases = null;
    //! Contains all operations.
    private ArrayList<Operation> m_operations = null;
    private ArrayList<Operation> m_all_operations = null;
    //! Map that stores the annotations of the interface.
    HashMap<String, String> m_annotations = null;
}
