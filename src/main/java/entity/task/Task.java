package entity.task;

import entity.BaseEntity;

import java.sql.Date;


public class Task extends BaseEntity {
    private String name;
    private Date expirationDate;

    //default values
    private PriorityType priorityType = PriorityType.LOW;
    private StateType stateType = StateType.NEW;

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public PriorityType getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, task=%s, expDate=%s, priority=%s, state=%s]",
                id, name, expirationDate.toString(), priorityType, stateType);
    }
}
