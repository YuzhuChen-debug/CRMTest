package www.bjpowernode.crm.settings.Services.imp;

import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.settings.Services.DictionaryService;
import www.bjpowernode.crm.settings.dao.DictionaryTypeDao;
import www.bjpowernode.crm.settings.dao.DictionaryValueDao;
import www.bjpowernode.crm.settings.domain.DictionaryType;
import www.bjpowernode.crm.settings.domain.DictionaryValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryServiceImp implements DictionaryService {
    private DictionaryTypeDao dictionaryTypeDao = (DictionaryTypeDao) SqlSessionUtil.getSqlSession().getMapper(DictionaryType.class);
    private DictionaryValueDao dictionaryValueDao = (DictionaryValueDao) SqlSessionUtil.getSqlSession().getMapper(DictionaryValue.class);

    @Override
    public Map<String, List<DictionaryValue>> getDictionaryValueByCode() {
        Map<String,List<DictionaryValue>> map = new HashMap<String,List<DictionaryValue>>();
        List<DictionaryType> dtList = (List<DictionaryType>) dictionaryTypeDao.getTypeList();
        for(DictionaryType dt:dtList){
            String code = dt.getCode();
            List<DictionaryValue> dvList = dictionaryValueDao.getDictionaryValueList(code);
            map.put(code+"List",dvList);
        }
        return map;
    }
}
