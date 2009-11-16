package bioinfo.comaWebServer.util;

import java.util.Map;
import java.util.List;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModelVisitor;

public class SelectMultipleModelRenderer implements SelectModelVisitor
{
    private final MarkupWriter _writer;

    private final MultiValueEncoder _encoder;

    public SelectMultipleModelRenderer(final MarkupWriter writer, MultiValueEncoder encoder)
    {
        _writer = writer;
        _encoder = encoder;
    }

    public void beginOptionGroup(OptionGroupModel groupModel)
    {
        _writer.element("optgroup", "label", groupModel.getLabel());

        writeDisabled(groupModel.isDisabled());
        writeAttributes(groupModel.getAttributes());
    }

    public void endOptionGroup(OptionGroupModel groupModel)
    {
        _writer.end(); // select
    }

    @SuppressWarnings("unchecked")
    public void option(OptionModel optionModel)
    {
        Object optionValue = optionModel.getValue();
        
        List<String> clientValues = _encoder.toClient(optionValue);

        for (String clientValue : clientValues) 
        {
                _writer.element("option", "value", clientValue);
        
                if (isOptionSelected(optionModel)) _writer.attributes("selected", "selected");
        
                writeDisabled(optionModel.isDisabled());
                writeAttributes(optionModel.getAttributes());
        
                _writer.write(optionModel.getLabel());
        }
        
        _writer.end();
    }

    private void writeDisabled(boolean disabled)
    {
        if (disabled) _writer.attributes("disabled", "disabled");
    }

    private void writeAttributes(Map<String, String> attributes)
    {
        if (attributes == null) return;

        for (Map.Entry<String, String> e : attributes.entrySet())
            _writer.attributes(e.getKey(), e.getValue());
    }

    protected boolean isOptionSelected(OptionModel optionModel)
    {
        return false;
    }

}

