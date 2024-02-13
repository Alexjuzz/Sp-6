package spring.home6.task;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import spring.home6.user.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @Column(name = "Date_Start", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @Column(name = "Status_at_now")
    private Status status;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Column(name = "users")
    private List<User> userList = new ArrayList<>();


    @PrePersist
    private void onCreate() {
        date = LocalDate.now();
        status = Status.NOT_STARTED;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        onCreate();
    }

    public Task() {
    }

    public void addUser(User user){
        userList.add(user);
        user.setTask(this);
    }
    public void removeUser(User user){
        userList.remove(user);
        user.setTask(null);
    }




    // region getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<User> getUserList() {
        return userList;
    }

    //endregion


}
