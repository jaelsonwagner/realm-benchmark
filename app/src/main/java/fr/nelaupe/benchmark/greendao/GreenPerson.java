package fr.nelaupe.benchmark.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "GREEN_PERSON".
 */
public class GreenPerson {

    private Long id;
    private String email;

    public GreenPerson() {
    }

    public GreenPerson(Long id) {
        this.id = id;
    }

    public GreenPerson(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
