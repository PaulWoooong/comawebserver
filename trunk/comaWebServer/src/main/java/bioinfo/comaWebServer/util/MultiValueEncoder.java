package bioinfo.comaWebServer.util;

import java.util.List;

public interface MultiValueEncoder<V>
{
    List<String> toClient(V value);

    List<V> toValue(String[] clientValue);
}

