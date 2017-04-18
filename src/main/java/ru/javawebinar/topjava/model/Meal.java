package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id"),
        @NamedQuery(name = Meal.BY_DATE, query = "SELECT m FROM Meal m WHERE m.user_id=:userId AND m.date_time BETWEEN :startDate AND :endDate"),
        @NamedQuery(name = Meal.GET_BETWEEN_DATES, query = "SELECT m FROM Meal WHERE m.user_id=:userId ORDER BY m.date_time"),
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"},
        name = "meals_unique_user_datetime_idx")})
public class Meal extends BaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String BY_DATE = "Meal.getByDateUser";
    public static final String GET_BETWEEN_DATES = "Meal.getAllSorted";

    @Column(name = "date_time", nullable = false)
    @NotBlank
    private LocalDateTime dateTime;

    @Column
    @NotBlank
    private String description;

    @Column
    @NotBlank
    @Range(min = 10, max = 5000)
    private int calories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Meal(){

    }

    public Meal(Meal m, int userId){
        this(m.getId(), m.getDateTime(), m.getDescription(), m.getCalories());
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    //!!!JbdcTemplate работает через сеттеры. Вместе с конструктором по умолчанию их нужно добавить в Meal
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
