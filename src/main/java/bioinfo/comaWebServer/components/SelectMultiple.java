package bioinfo.comaWebServer.components;

import java.util.List;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SelectModelVisitor;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import bioinfo.comaWebServer.entities.DatabaseItem;
import bioinfo.comaWebServer.util.MultiValueEncoder;
import bioinfo.comaWebServer.util.SelectMultipleModelRenderer;

public final class SelectMultiple extends AbstractField
{
    private class Renderer extends SelectMultipleModelRenderer
    {

        public Renderer(MarkupWriter writer)
        {
            super(writer, _encoder);
        }

        protected boolean isOptionSelected(OptionModel optionModel)
        {
                Object value = optionModel.getValue();
                
                if(_values == null)
                {
                	return false;
                }
                
                if(_values.contains(value))
                {
                	return ((DatabaseItem)value).isSelected();
                }
                
                return false;
        }
    }

    @Parameter
    private MultiValueEncoder _encoder;

    @Parameter(required = true)
    private SelectModel _model;

    @Inject
    private Request _request;

    /** The list of value to read. */
    @Parameter(required = true, principal = true)
    private List<Object> _values;
    
    @Parameter(required = true, principal = true)
    private List<Object> _selected;

    @SuppressWarnings("unchecked")
    protected void processSubmission(String elementName)
    {
    	String elementNameLc = elementName.toLowerCase();
    	String paramName = "";
    	
    	List<String> paramNames = _request.getParameterNames();
    	for(String name: paramNames)
    	{
    		if(name.toLowerCase().equals(elementNameLc))
    		{
    			paramName = name;
    		}
    	}
    	
        String[] primaryKeys = _request.getParameters(paramName);
        
        for(Object o: _values)
        {
        	DatabaseItem db = (DatabaseItem) o;
        	db.setSelected(false);
        }
        	
        _selected = _encoder.toValue(primaryKeys);
        
        for(Object o: _selected)
        {
        	DatabaseItem db = (DatabaseItem) o;
        	db.setSelected(true);
        }

    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    void beginRender(MarkupWriter writer)
    {
        writer.element("select", "name", getLabel() , "id", getClientId(), 
        		"multiple", "multiple", "size", "5");
    }

    @BeforeRenderTemplate
    void options(MarkupWriter writer)
    {
        SelectModelVisitor renderer = new Renderer(writer);

        _model.visit(renderer);
    }

    void setModel(SelectModel model)
    {
        _model = model;
    }

    void setValues(List<Object> values)
    {
        _values = values;
    }
    
    void setValueEncoder(MultiValueEncoder encoder)
    {
        _encoder = encoder;
    }
}
