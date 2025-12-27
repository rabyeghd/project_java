package com.formation.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a time slot in the weekly schedule
 */
public class TimeSlot {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    
    public TimeSlot() {
    }
    
    public TimeSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * Check if this time slot overlaps with another
     */
    public boolean overlaps(TimeSlot other) {
        if (!this.day.equals(other.day)) {
            return false;
        }
        return this.startTime.isBefore(other.endTime) && 
               other.startTime.isBefore(this.endTime);
    }
    
    /**
     * Check if a time slot is within this time slot
     */
    public boolean contains(TimeSlot other) {
        if (!this.day.equals(other.day)) {
            return false;
        }
        return !this.startTime.isAfter(other.startTime) && 
               !this.endTime.isBefore(other.endTime);
    }
    
    public DayOfWeek getDay() {
        return day;
    }
    
    public void setDay(DayOfWeek day) {
        this.day = day;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return day == timeSlot.day && 
               Objects.equals(startTime, timeSlot.startTime) && 
               Objects.equals(endTime, timeSlot.endTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime);
    }
    
    @Override
    public String toString() {
        return day + " " + startTime + "-" + endTime;
    }
}
