package www.bjpowernode.crm.settings.dao;

import www.bjpowernode.crm.settings.domain.DictionaryValue;

import java.util.List;

public interface DictionaryValueDao {
    List<DictionaryValue> getDictionaryValueList(String code);
}
