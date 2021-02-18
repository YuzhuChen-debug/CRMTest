package www.bjpowernode.crm.settings.Services;

import www.bjpowernode.crm.settings.domain.DictionaryValue;

import java.util.List;
import java.util.Map;

public interface DictionaryService {
    Map<String, List<DictionaryValue>> getDictionaryValueByCode();
}
