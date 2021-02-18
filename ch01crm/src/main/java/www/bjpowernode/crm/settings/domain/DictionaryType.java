package www.bjpowernode.crm.settings.domain;

public class DictionaryType {
    private String code;
    private String name;
    private String description;

    public DictionaryType() {
    }

    @Override
    public String toString() {
        return "DictionaryType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DictionaryType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
