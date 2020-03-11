package entities;

public class ListsBuilder {

    private Lists list;

    public ListsBuilder() {
        list = new Lists();
    }

    public ListsBuilder withId(String id) {
        list.setId(id);
        return this;
    }

    public ListsBuilder withName(String name) {
        list.setName(name);
        return this;
    }

    public ListsBuilder withDescription(String description) {
        list.setDescription(description);
        return this;
    }

    public ListsBuilder withLanguage(String language) {
        list.setLanguage(language);
        return this;
    }

    public ListsBuilder withMedia_id(String media_id) {
        list.setMedia_id(media_id);
        return this;
    }

    public Lists build() {
        return this.list;
    }

}
