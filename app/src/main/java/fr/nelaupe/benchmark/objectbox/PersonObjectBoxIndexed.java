package fr.nelaupe.benchmark.objectbox;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@Entity
public class PersonObjectBoxIndexed {

    @Id
    private Long id;
    @Index
    private String email;

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
