package spring.home6.task;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import spring.home6.user.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @Column(name = "Date_Start",nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;
    @Enumerated(EnumType.STRING)
    @Column(name= "Status_at_now")
    private Status status;



    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL)
   private List<User> userList = new ArrayList<>();


    @PrePersist
    private void onCreate(){
        date = new Date(new java.util.Date().getTime());
        status = Status.NOT_STARTED;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        onCreate();
    }

    public Task() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    //endregion


}
