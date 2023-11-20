package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "floor")
    private int floor;

    @Column(name = "number")
    private int number;

    @ManyToOne
    @JoinColumn(name = "dormitory_id", referencedColumnName = "id")
    private Dormitory dormitory;

    @OneToMany(mappedBy = "room")
    private List<Student> studentList;

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Room() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public Room(int id, int floor, int number, Dormitory dormitory) {
        this.id = id;
        this.floor = floor;
        this.number = number;
        this.dormitory = dormitory;
    }

    public Room(int floor, int number, Dormitory dormitory) {
        this.floor = floor;
        this.number = number;
        this.dormitory = dormitory;
    }
}
