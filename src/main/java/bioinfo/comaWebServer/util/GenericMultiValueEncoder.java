package bioinfo.comaWebServer.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class GenericMultiValueEncoder<T> implements MultiValueEncoder<T> {

		private List<T> list;
	    private final String labelField;

        public GenericMultiValueEncoder(List<T> list, String labelField) 
        {
                this.list = list;
                this.labelField = labelField;
        }

        public List<String> toClient(T obj) {
                try {
                        return Arrays.asList(BeanUtils.getArrayProperty(obj, labelField));
                } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return null;
        }

        public List<T> toValue(String[] strings) 
        {
            try 
            {
                List<String> test = Arrays.asList(strings);
                List<T> valueList = new ArrayList<T>();
                for (T obj : list) 
                {
                    if (test.contains(BeanUtils.getProperty(obj, labelField))) 
                    {
                            valueList.add(obj);
                    }
                }
                return valueList;
            } 
            catch (NullPointerException e) 
            {
                    List<T> exceptionReturn = new ArrayList<T>();
                    return exceptionReturn; 
            } 
            catch (IllegalAccessException e) 
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } 
            catch (InvocationTargetException e) 
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } 
            catch (NoSuchMethodException e) 
            {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return null;
        }
}

