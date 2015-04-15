package com.eprosima.idl.parser.typecode;

import java.util.ArrayList;

public abstract class ContainerTypeCode extends TypeCode
{
    protected ContainerTypeCode(int kind)
    {
        super(kind);
    }
    
    @Override
    public abstract String getCppTypename();
    
    @Override
    public abstract String getIdlTypename();
    
    @Override
    public boolean isContainer(){return true;}
    
    public TypeCode getContentTypeCode()
    {
        return m_contentTypeCode;
    }
    
    public void setContentTypeCode(TypeCode contentTypeCode)
    {
        m_contentTypeCode = contentTypeCode;
    }
    
    public int getDepth() {
    	int ret = 1;
    	
    	if (m_contentTypeCode.isPrimitive()) {
    		return ret;
    	} else {
    		if (m_contentTypeCode instanceof ContainerTypeCode) {
    			ret += ((ContainerTypeCode) m_contentTypeCode).getDepth();
    		}
    	}
    	
    	return ret;
    }
    
    public ArrayList<Byte> getDepthArray() {
    	int depth = this.getDepth();
    	ArrayList<Byte> ret = new ArrayList<Byte>();
    	for(int i=0; i < depth; ++i) {
    		ret.add((byte) 0);
    	}
    	return ret;
    }
    
    @Override
    public TypeCode getUpperTypeCode() {
    	if(m_contentTypeCode instanceof AliasTypeCode) {
            AliasTypeCode alias = (AliasTypeCode) m_contentTypeCode;
            return alias.getUpperTypeCode();
        } else if (m_contentTypeCode instanceof ContainerTypeCode) {
            ContainerTypeCode container = (ContainerTypeCode) m_contentTypeCode;
            return container.getUpperTypeCode();
        }
    	return m_contentTypeCode;
    }
    
    public ArrayList<TypeCode> getContainerLevels() {
    	ArrayList<TypeCode> ret = new ArrayList<TypeCode>();
    	
    	this.processContainerLevels(ret);
    	
    	return ret;
    }
    
    protected void processContainerLevels(ArrayList<TypeCode> ret) {
    	if (this instanceof AliasTypeCode) {
    		if (m_contentTypeCode instanceof ContainerTypeCode) {
    			ContainerTypeCode ctc = (ContainerTypeCode) m_contentTypeCode;
    			ctc.processContainerLevels(ret);
    		}
    	} else {
			if (this instanceof ContainerTypeCode) {
				ContainerTypeCode container = (ContainerTypeCode) this;
				ret.add(container);
				if (this instanceof MapTypeCode) {
		    		MapTypeCode mapContainer = (MapTypeCode) this;
					if (mapContainer.getKeyTypeCode() instanceof ContainerTypeCode) {
						ContainerTypeCode keyContainerTypeCode = (ContainerTypeCode) mapContainer.getKeyTypeCode();
						keyContainerTypeCode.processContainerLevels(ret);
					}
		    	} else if (m_contentTypeCode instanceof AliasTypeCode || m_contentTypeCode instanceof ContainerTypeCode) {
		    		ContainerTypeCode contentContainer = (ContainerTypeCode) m_contentTypeCode;
		    		contentContainer.processContainerLevels(ret);
		    	}
				
			}
    	}
    }

    private TypeCode m_contentTypeCode = null;
}
